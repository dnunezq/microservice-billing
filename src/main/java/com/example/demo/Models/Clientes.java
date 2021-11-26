package com.example.demo.Models;

/**
 * @author Sonia Sanabria
 */
import org.springframework.data.annotation.Id;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class Clientes {

    @Id
    private Integer factura;
    private Date fechaHoraIngreso;
    private String placa;
    private Date fechaHoraSalida;
    private Integer tiempoUso;
    private Double valorFactura;
            
/* Constructor*/
    
    public Clientes (Integer factura, Date fechaHoraIngreso,String placa, Date fechaHoraSalida, Integer tiempoUso, Double valorFactura) {
        this.factura = factura;
        this.fechaHoraIngreso = fechaHoraIngreso;
        this.placa = placa;
        this.fechaHoraSalida = fechaHoraSalida;
        this.tiempoUso = tiempoUso;
        this.valorFactura = valorFactura;
    }
    
    public Integer getfactura() {
        return factura;
    }
    
    public void setfactura (Integer factura) {
        this.factura = factura;
    }
    
    
    public Date getfechaHoraIngreso() {
        return fechaHoraIngreso;
    }
    
    public void setfechaHoraIngreso (Date fechaingreso) {
        this.fechaHoraIngreso = fechaHoraIngreso;
    }
         
    public String getplaca() {
        return placa;
    }
    
    public void setplaca (String placa) {
        this.placa = placa;
    }
    
    public Date getfechaHoraSalida() {
        return fechaHoraSalida;
    }
    
    public void setfechaHoraSalida (Date fechaHoraSalida) {
        this.fechaHoraSalida = fechaHoraSalida;
    }
    
    public Integer gettiempoUso() {
        return tiempoUso;
    }
    
    public void settiempoUso (Integer tiempoUso) {
        this.tiempoUso = tiempoUso;
    }
    
     public Double getvalorFactura() {
        return valorFactura;
    }
    
    public void setvalorFactura (Double valorFactura) {
        this.valorFactura = valorFactura;
    }
    
    
    
    }

