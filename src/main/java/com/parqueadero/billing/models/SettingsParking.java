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


    public SettingsParking() {
        this.admin = "admin";
        this.minutePrice = 37;
        this.capacity = 50;
    }
}