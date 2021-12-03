package com.parqueadero.billing.controllers;


import com.parqueadero.billing.exceptions.AlreadyClientExistException;
import com.parqueadero.billing.exceptions.ClientNotFoundException;
import com.parqueadero.billing.exceptions.SetBillNumberExecption;
import com.parqueadero.billing.models.Client;
import com.parqueadero.billing.models.SettingsParking;
import com.parqueadero.billing.repositories.ClientRepository;
import com.parqueadero.billing.repositories.SettingsParkingRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class ClientController {
    private final ClientRepository clientRepository;
    private final SettingsParkingRepository settingsParkingRepository;

    public ClientController(ClientRepository clientRepository ,SettingsParkingRepository settingsParkingRepository) {
        this.clientRepository = clientRepository;
        this.settingsParkingRepository=settingsParkingRepository;

        if (settingsParkingRepository.findAll().size()==0) {
            settingsParkingRepository.save(new SettingsParking());
        }
    }

    //endpoint for active clients
    @GetMapping("/Clients")
    Iterable<Client> getActiveClients() {
        return clientRepository.findByState("active");
    }
    //endpoint for inactive clients
    @GetMapping("/historical")
    Iterable<Client> getHistoricalClients() {
        return clientRepository.findByState("inactive");
    }
    //endpoint to create a new client
    @PostMapping("/create")
    Client CreateClient(@RequestBody Client client) {
        List <Client> clients=clientRepository.findByState("active");
        List <SettingsParking> settings=settingsParkingRepository.findAll();

        if (clients.size()>=settings.get(0).getCapacity()) {
            throw new AlreadyClientExistException("Ya no hay espacio para más clientes");
        }else if(clientRepository.findByLicensePlateAndState(client.getLicensePlate(),"active").size()>0){
            throw new AlreadyClientExistException("Ya existe un vehiculo con las misma referencia en el parqueadero");
        }else{ 
        client.setState("active");
        client.setEntryDate(new Date());
        return clientRepository.save(client);
        }
    }
    //endpoint to consult client's billing
    @GetMapping("/Clients/{licensePlate}")
    Client getBillingClient(@PathVariable String licensePlate) {
        List<Client> activeClients = clientRepository.findByLicensePlateAndState(licensePlate,"active");
        if(activeClients.size()==0){
            throw new ClientNotFoundException("el vehiculo no se encuentra en el parqueadero");
        }
        //add the current exit date
        Client client=activeClients.get(0);
        client.setExitDate(new Date());
        List<SettingsParking> settings=settingsParkingRepository.findAll();
        int cost=settings.get(0).getMinutePrice();

        //calculate the cost of the service
        client.setUseTime((int)((double) ((client.getExitDate().getTime()-client.getEntryDate().getTime())/60000)));
        client.setCost((double)client.getUseTime()*cost);

        return client;
    }
    //Endpoint to save client billing
    @PostMapping("/Clients/{licensePlate}")
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
        return exitClient;
    }





    public Long constructBillNumber() {

        Long billNumber;
        List <SettingsParking> settings       = settingsParkingRepository.findAll();
        SettingsParking lastSettings          = settings.get(0);
        Long pfx                              = lastSettings.getPrefix();
        Long bNI                              = lastSettings.getBillNumberInit();
        Long lastBillNumber                   = lastSettings.getLastBillNumber();

        if(lastBillNumber!=0){     

            Long prefixLength                = (long) lastSettings.getPrefix().toString().length();
            Long bNE                         = lastSettings.getBillNumberEnd();          
            lastBillNumber                   = toolBox.eliminatePrefix(lastBillNumber, prefixLength);

            if(lastBillNumber > bNE - 1000) {

                billNumber = toolBox.concatenateDigits(pfx, bNI) + 1;
                lastSettings.setLastBillNumber(billNumber);
                settingsParkingRepository.save(lastSettings);
                return billNumber;

            } else if (lastBillNumber == bNE) {

                throw new SetBillNumberExecption("Debe solicitar una nueva resolución a la DIAN");

            } else if (lastBillNumber < bNI) {

                throw new SetBillNumberExecption("Configure correctamente la numeracion de la factura");
            }

            billNumber = toolBox.concatenateDigits(pfx, lastBillNumber);
            billNumber ++;
            lastSettings.setLastBillNumber(billNumber);
            settingsParkingRepository.save(lastSettings);
            return billNumber;

        }

        billNumber = toolBox.concatenateDigits(pfx, bNI) + 1;
        lastSettings.setLastBillNumber(billNumber);
        settingsParkingRepository.save(lastSettings);
        return billNumber;

    }
}