package com.MisionTIC_T2_P42.billing_ms.models;

import org.springframework.data.annotation.Id;

public class SettingParking {
    @Id
    private String admin;
    private int minutePrice;
    private int capacity;

    public SettingParking (String admin, int minutePrice, int capacity) {
        this.admin = "admin";
        this.minutePrice = 50;
        this.capacity = 100;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public int getMinutePrice() {
        return minutePrice;
    }

    public void setMinutePrice(int minutePrice) {
        this.minutePrice = minutePrice;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
