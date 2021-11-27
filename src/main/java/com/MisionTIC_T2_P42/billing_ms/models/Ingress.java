package com.MisionTIC_T2_P42.billing_ms.models;

import org.springframework.data.annotation.Id;
import java.util.Date;


public class Ingress {
    @Id
    private String id;
    private Date dateIngress;
    private Date dateExit;
    private String licensePlate;

    public Ingress(String id, Date date_ingress, Date date_exit, String license_plate) {
        this.id = id;
        this.dateIngress = date_ingress;
        this.dateExit = date_exit;
        this.licensePlate = license_plate;
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
}
