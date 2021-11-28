package com.parqueadero.billing.models;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SettingsParking {
    @Id
    private String admin;
    private int minutePrice;
    private int capacity;
    private int prefix;
    private int serialBillInit;
    private int serialBillEnd;
    //private String DIANresolution;


    public SettingsParking() {
        this.admin = "admin";
        this.minutePrice = 37;
        this.capacity = 50;
        this.serialBillInit = 10000000;
        this.serialBillEnd = 10999999;
        this.prefix = 9999;
    }
}