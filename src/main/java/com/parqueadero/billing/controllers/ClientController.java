package com.parqueadero.billing.controllers;


import com.parqueadero.billing.exceptions.AlreadyClientExistException;
import com.parqueadero.billing.exceptions.ClientNotFoundException;
import com.parqueadero.billing.exceptions.SetBillNumberExecption;
import com.parqueadero.billing.models.Client;
import com.parqueadero.billing.models.Earnings;
import com.parqueadero.billing.models.SettingsParking;
import com.parqueadero.billing.repositories.ClientRepository;
import com.parqueadero.billing.repositories.EarningsRepository;
import com.parqueadero.billing.repositories.SettingsParkingRepository;
import com.parqueadero.billing.resourcesInputOutput.ClientsAndSpace;
import com.parqueadero.billing.resourcesInputOutput.FilterInput;
import com.parqueadero.billing.resourcesInputOutput.ResultHistorical;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class ClientController {
    private final ClientRepository clientRepository;
    private final SettingsParkingRepository settingsParkingRepository;
    private final EarningsRepository earningsRepository;
    private final SimpleDateFormat formatDay;
    private final SimpleDateFormat formatMonth;
    private final SimpleDateFormat formatYear;
    private final long differenceTime;
    public ClientController(ClientRepository clientRepository ,SettingsParkingRepository settingsParkingRepository,EarningsRepository earningsRepository) {
        this.clientRepository = clientRepository;
        this.settingsParkingRepository=settingsParkingRepository;
        this.earningsRepository=earningsRepository;

        this.differenceTime=18000000;
        this.formatDay=new SimpleDateFormat("yyyy-MM-dd");
        this.formatMonth=new SimpleDateFormat("yyyy-MM");
        this.formatYear=new SimpleDateFormat("yyyy");
        if (settingsParkingRepository.findAll().size()==0) {
            settingsParkingRepository.save(new SettingsParking());
        }
    }

    //endpoint for active clients
    @GetMapping("/clients")
    ClientsAndSpace getActiveClients() {
        ClientsAndSpace result=new ClientsAndSpace();
        List <SettingsParking> settings=settingsParkingRepository.findAll();
        List<Client> clients=clientRepository.findByState("active");
        result.setClients(clients);
        result.setCapacity(settings.get(0).getCapacity()-clients.size());
        return result;
    }
    //endpoint for inactive clients
    @GetMapping("/historical/{typeDate}/{date}")
    ResultHistorical getHistoricalClients(@PathVariable String typeDate,@PathVariable String date) {

        ResultHistorical resultHistorical=new ResultHistorical();
        List<Client> clients=new ArrayList<Client>();
        float totalEarnings=0;
        if(typeDate.equals("all")){
            clients=clientRepository.findByState("inactive");
            List<Earnings>allEarnings=earningsRepository.findAll();
            for(Earnings earnings:allEarnings){
                totalEarnings+=earnings.getEarnings();
            }
        }
        else if(typeDate.equals("day")){

            List<Earnings> allEarnings=earningsRepository.findByDay(date);

            for(Earnings earnings:allEarnings){
                for(String idDayClients: earnings.getIdClients())
                    clients.add(clientRepository.findClientById(idDayClients));
                    totalEarnings+=earnings.getEarnings();
            }
        }
        else if(typeDate.equals("month")){

            List<Earnings> allEarnings=earningsRepository.findByMonth(date.substring(0,7));

            for(Earnings earnings:allEarnings){
                for(String idDayClients: earnings.getIdClients())
                    clients.add(clientRepository.findClientById(idDayClients));
                totalEarnings+=earnings.getEarnings();
            }
        }
        else if(typeDate.equals("year")){

            List<Earnings> allEarnings=earningsRepository.findByYear(date.substring(0,4));
            for(Earnings earnings:allEarnings){
                for(String idDayClients: earnings.getIdClients())
                    clients.add(clientRepository.findClientById(idDayClients));
                totalEarnings+=earnings.getEarnings();
            }
        }
        resultHistorical.setClients(clients);
        resultHistorical.setEarnings(totalEarnings);
        return resultHistorical;
    }
    //endpoint to create a new client
    @PostMapping("/clients")
    Client CreateClient(@RequestBody Client client) {
        List <Client> clients=clientRepository.findByState("active");
        List <SettingsParking> settings=settingsParkingRepository.findAll();

        if (clients.size()>=settings.get(0).getCapacity()) {
            throw new AlreadyClientExistException("Ya no hay espacio para más clientes");
        }else if(clientRepository.findByLicensePlateAndState(client.getLicensePlate(),"active").size()>0){
            throw new AlreadyClientExistException("Ya existe un vehiculo con las misma referencia en el parqueadero");
        }else{ 
        client.setState("active");
        Date actual=new Date();
        long milisecondsTime=actual.getTime();
        Date currentDate=new Date(milisecondsTime-differenceTime);
        client.setEntryDate(currentDate);
        return clientRepository.save(client);
        }
    }
    //endpoint to consult client's billing
    @GetMapping("/clients/{licensePlate}")
    Client getBillingClient(@PathVariable String licensePlate) {
        List<Client> activeClients = clientRepository.findByLicensePlateAndState(licensePlate,"active");
        if(activeClients.size()==0){
            throw new ClientNotFoundException("el vehiculo no se encuentra en el parqueadero");
        }
        //add the current exit date
        Client client=activeClients.get(0);
        Date actual=new Date();
        long milisecondsTime=actual.getTime();
        Date currentDate=new Date(milisecondsTime-differenceTime);
        client.setExitDate(currentDate);
        List<SettingsParking> settings=settingsParkingRepository.findAll();
        int cost=settings.get(0).getMinutePrice();

        //calculate the cost of the service
        client.setUseTime((int)((double) ((client.getExitDate().getTime()-client.getEntryDate().getTime())/60000)));
        client.setCost((double)client.getUseTime()*cost);

        return client;
    }
    //Endpoint to save client billing
    @PostMapping("/clients/{licensePlate}")
    Client SaveBillingClient(@RequestBody Client client) {

        Client exitClient;
        try{
         exitClient= clientRepository.findByLicensePlateAndState(client.getLicensePlate(),"active").get(0);
        }catch(IndexOutOfBoundsException e){
            throw new ClientNotFoundException("el vehiculo no se encuentra en el parqueadero");
        }
        exitClient.setState("inactive");
        exitClient.setExitDate(client.getExitDate());
        exitClient.setUseTime(client.getUseTime());
        exitClient.setCost(client.getCost());

        exitClient.setBillNumber(constructBillNumber());
        clientRepository.save(exitClient);

        List<Earnings> actualEarnings=earningsRepository.findByDay(formatDay.format(exitClient.getExitDate()));

        Earnings newEarnings;
        if(actualEarnings.size()==0){
            newEarnings=new Earnings();
            newEarnings.getIdClients().add(exitClient.getId());
            newEarnings.setDay(formatDay.format(exitClient.getExitDate()));
            newEarnings.setMonth(formatMonth.format(exitClient.getExitDate()));
            newEarnings.setYear(formatYear.format(exitClient.getExitDate()));
            newEarnings.setEarnings(newEarnings.getEarnings()+exitClient.getCost());
            earningsRepository.save(newEarnings);
        }
        else{
            newEarnings=actualEarnings.get(0);
            newEarnings.getIdClients().add(exitClient.getId());
            newEarnings.setDay(formatDay.format(exitClient.getExitDate()));
            newEarnings.setMonth(formatMonth.format(exitClient.getExitDate()));
            newEarnings.setYear(formatYear.format(exitClient.getExitDate()));
            newEarnings.setEarnings(newEarnings.getEarnings()+exitClient.getCost());
            earningsRepository.save(newEarnings);
        }
        return exitClient;
    }





    public String constructBillNumber() {


        List <SettingsParking> settings       = settingsParkingRepository.findAll();
        SettingsParking lastSettings          = settings.get(0);
        String pfx                            = lastSettings.getPrefix();
        Long bNI                              = lastSettings.getBillNumberInit();
        Long bNE                             = lastSettings.getBillNumberEnd();
        String lastBillNumber                  = lastSettings.getLastBillNumber();
        String billNumber=pfx;
        if(lastBillNumber.equals("0")){
            billNumber +=String.valueOf(bNI);
        }
        else{
            int prefixLength                = lastSettings.getPrefix().length();
            Long numericBillNumber          = Long.parseLong(lastBillNumber.substring(prefixLength));

            if(numericBillNumber >=bNI && numericBillNumber <bNE  ) {

                billNumber +=String.valueOf(numericBillNumber + 1);


            } else if (numericBillNumber >=bNE) {

                throw new SetBillNumberExecption("Debe solicitar una nueva resolución a la DIAN");

            } else if (numericBillNumber < bNI) {

                throw new SetBillNumberExecption("Configure correctamente la numeracion de la factura");
            }

            lastSettings.setLastBillNumber(billNumber);
            settingsParkingRepository.save(lastSettings);
            return billNumber;

        }

        lastSettings.setLastBillNumber(billNumber);
        settingsParkingRepository.save(lastSettings);
        return billNumber;

    }
}