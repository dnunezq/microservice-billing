package com.parqueadero.billing.models;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SettingsParking {
    @Id
    private String admin;
    private String nameParking;
    private String adressParking;
    private int minutePrice;
    private int capacity;
    private String prefix;
    private Long billNumberInit;
    private Long billNumberEnd;
    private String lastBillNumber;
    //private String DIANresolution;


    public SettingsParking() {
        this.admin = "admin";
        this.nameParking="parking";
        this.adressParking ="adressParking";
        this.minutePrice = 37;
        this.capacity = 3;
        this.billNumberInit = (long) 100000;
        this.billNumberEnd = (long) 999999;
        this.lastBillNumber="0";
        this.prefix = "9999";
    }

}