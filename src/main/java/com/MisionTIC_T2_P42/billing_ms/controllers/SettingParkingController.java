package com.MisionTIC_T2_P42.billing_ms.controllers;



import com.MisionTIC_T2_P42.billing_ms.models.SettingParking;
import com.MisionTIC_T2_P42.billing_ms.repositories.SettingParkingRepository;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.Optional;

@RestController
public class SettingParkingController {
    private final SettingParkingRepository settingParkingRepository;

    public SettingParkingController(SettingParkingRepository settingParkingRepository) {
        this.settingParkingRepository = settingParkingRepository;
    }

    @GetMapping("/settings")
    SettingParking getActualSettingParking() {
        List<SettingParking> settings=settingParkingRepository.findAll();
        return settings.get(0);
    }

    @PostMapping("/settings")
    SettingParking updateSettings(@RequestBody SettingParking settingParking){ /*captura los datos del ususario*/
        List<SettingParking> settings=settingParkingRepository.findAll();
        SettingParking newSettingParking;   /* Se crea la nueva clase*/
        newSettingParking=settings.get(0); /* Se instancia la nueva clase con los datos en BD*/

        newSettingParking.setMinutePrice(settingParking.getMinutePrice());
        newSettingParking.setCapacity(settingParking.getCapacity());
        settingParkingRepository.save(newSettingParking); //Guarda en BD
        return newSettingParking;  //Imprime en BD, por @RequestBody
    }


}
