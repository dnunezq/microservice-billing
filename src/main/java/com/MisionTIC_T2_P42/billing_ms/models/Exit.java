package com.MisionTIC_T2_P42.billing_ms.models;

import org.springframework.data.annotation.Id;
import java.util.Date;

public class Exit {
    @Id
    private String licensePlateExit;
    private Date date_exit;
    private long useTime;
    private Double cost;


    public Exit(String licensePlateExit, Date date_exit, long useTime, Double cost) {
        this.licensePlateExit = licensePlateExit;
        this.date_exit = date_exit;
        this.useTime = useTime;
        this.cost = cost;
    }

    public String getLicensePlateExit() {
        return licensePlateExit;
    }

    public void setLicensePlateExit(String licensePlateExit) {
        this.licensePlateExit = licensePlateExit;
    }

    public Date getDate_exit() {
        return date_exit;
    }

    public void setDate_exit(Date date_exit) {
        this.date_exit = date_exit;
    }

    public long getUseTime() {
        return useTime;
    }

    public void setUseTime(long useTime) {
        this.useTime = useTime;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
}
