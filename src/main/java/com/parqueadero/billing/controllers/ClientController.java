package com.parqueadero.billing.controllers;


import com.parqueadero.billing.exceptions.AlreadyClientExistException;
import com.parqueadero.billing.exceptions.ClientNotFoundException;
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
        if(clientRepository.findByLicensePlateAndState(client.getLicensePlate(),"active").size()>0){
            throw new AlreadyClientExistException("Ya existe un vehiculo con las misma referencia en el parqueadero");
        }
        client.setState("active");
        client.setEntryDate(new Date());
        return clientRepository.save(client);
    }
    //endpoint to show client billing
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
        Client exitClient = clientRepository.findByLicensePlateAndState(client.getLicensePlate(),"active").get(0);
        exitClient.setState("inactive");
        exitClient.setCost(client.getCost());
        exitClient.setUseTime(client.getUseTime());
        exitClient.setExitDate(client.getExitDate());
        exitClient.setSerialBill(constructSerialBill(exitClient));
        clientRepository.save(exitClient);
        return exitClient;
    }

    public Integer constructSerialBill(Client exitClient){
        List <Client> listBill = clientRepository.findAll();
        SettingsParking sp = new SettingsParking();
        int pfx = sp.getPrefix();
        int sBI = sp.getSerialBillInit();
        int sBE = sp.getSerialBillEnd();
        if(listBill.isEmpty()){         
            return concatenateDigits(pfx,sBI,sBE)+1;
        }else if(listBill.get(listBill.size()-1) > sBI && < sBE){
            return concatenateDigits(pfx,sBI,sBE)+1;

        };
        return sBE;
    }
    //function that concatenate prefix serialBillInit serialBillEnd and return a integer
    public static Integer concatenateDigits(int... digits) {
        StringBuilder sb = new StringBuilder(digits.length);
        for (int digit : digits) {
          sb.append(digit);
        }
        return new Integer(sb.toString());
     }

     public  Integer eliminatePrefix(int serialBill){
         
        }
     }

}
