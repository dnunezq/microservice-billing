package com.MisionTIC_T2_P42.billing_ms.models;

import org.springframework.data.annotation.Id;
import java.util.Date;


public class Ingress {
    @Id
    private String id;
    private Date dateIngress;
    private Date dateExit;
    private String licensePlate;
    private double cost;
    private int useTime;
    private String status;

    public Ingress(String id, Date dateIngress, Date dateExit, String licensePlate, double cost, int useTime, String status) {
        this.id = id;
        this.dateIngress = dateIngress;
        this.dateExit = dateExit;
        this.licensePlate = licensePlate;
        this.cost = cost;
        this.useTime = useTime;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDateIngress() {
        return dateIngress;
    }

    public void setDateIngress(Date dateIngress) {
        this.dateIngress = dateIngress;
    }

    public Date getDateExit() {
        return dateExit;
    }

    public void setDateExit(Date dateExit) {
        this.dateExit = dateExit;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getUseTime() {
        return useTime;
    }

    public void setUseTime(int useTime) {
        this.useTime = useTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
