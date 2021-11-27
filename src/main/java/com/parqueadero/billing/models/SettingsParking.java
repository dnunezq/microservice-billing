package com.parqueadero.billing.models;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

public class SettingsParking {
    @Id
    @Getter @Setter private String admin;
    @Getter @Setter private int minutePrice;
    @Getter @Setter private int capacity;



    public SettingsParking() {
        this.admin = "admin";
        this.minutePrice = 37;
        this.capacity = 50;
    }
}