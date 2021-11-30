package com.MisionTIC_T2_P42.billing_ms.controllers;


import com.MisionTIC_T2_P42.billing_ms.exceptions.LicensePlateNotFoundException;
import com.MisionTIC_T2_P42.billing_ms.models.Ingress;
import com.MisionTIC_T2_P42.billing_ms.models.SettingParking;
import com.MisionTIC_T2_P42.billing_ms.repositories.IngressRepository;
import com.MisionTIC_T2_P42.billing_ms.repositories.SettingParkingRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class IngressController {
    private final IngressRepository ingressRepository;
    private final SettingParkingRepository settingParkingRepository;

    public IngressController(IngressRepository ingressRepository, SettingParkingRepository settingParkingRepository) {
        this.ingressRepository = ingressRepository;
        this.settingParkingRepository = settingParkingRepository;

        if (settingParkingRepository.findAll().size()==0) {
            settingParkingRepository.save(new SettingParking("admin",50,100));
        }
    }

    //endpoint for active clients
    @GetMapping("/clients")
    Iterable<Ingress>getActiveIngressCars(){
       return ingressRepository.findByStatus("active");
    }

    //endpoint for inactive clients
    @GetMapping("/historic")
    Iterable<Ingress>getHistoricalCars(){
        return ingressRepository.findByStatus("inactive");
    }

    //endpoint to create a new client
    @PostMapping("/creator")
    Ingress CreateCar(@RequestBody Ingress ingress){
        if(ingressRepository.findByLicensePlateAndStatus(ingress.getLicensePlate(),"active").size()>0){

        }
        ingress.setStatus("active");
        ingress.setDateIngress(new Date());
        return ingressRepository.save(ingress);
    }

    //endpoint to show actual car - billing

    @GetMapping("/clients/{licensePlate}")
    Ingress getBillingClient(@PathVariable String licensePlate){
        List<Ingress> activeClients=ingressRepository.findByLicensePlateAndStatus(licensePlate,"active");
        if(activeClients.size()==0){

        }
        //add the current date
        Ingress ingress=activeClients.get(0);
        ingress.setDateExit(new Date());

        //calculate the cost of the service
        List<SettingParking> settings=settingParkingRepository.findAll();
        int cost=settings.get(0).getMinutePrice();
        ingress.setUseTime((int)((double) (ingress.getDateExit().getTime()-ingress.getDateIngress().getTime())/60000));
        ingress.setCost((double) ingress.getUseTime()*cost);


        return ingress;
    }

    //Endpoint to save client billing
    @PostMapping("/clients/{licensePlate}")
    Ingress SaveBillingClient(@RequestBody Ingress ingress){
        Ingress exitClient=ingressRepository.findByLicensePlateAndStatus(ingress.getLicensePlate(),"active").get(0);
        exitClient.setStatus("inactive");
        exitClient.setCost(ingress.getCost());
        exitClient.setUseTime(ingress.getUseTime());
        exitClient.setDateExit(ingress.getDateExit());
        ingressRepository.save(exitClient);

        return exitClient;
    }



}
