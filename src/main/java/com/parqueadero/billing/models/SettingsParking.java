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
    private Long prefix;
    private Long BillNumberInit;
    private Long BillNumberEnd;
    private Long lastBillNumber;
    //private String DIANresolution;


    public SettingsParking() {
        this.admin = "admin";
        this.minutePrice = 37;
        this.capacity = 3;
        this.BillNumberInit = (long) 10000000;
        this.BillNumberEnd = (long) 10999999;
        this.prefix = (long) 9999;
    }

}