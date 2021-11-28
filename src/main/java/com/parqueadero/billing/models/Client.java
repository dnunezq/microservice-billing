package com.parqueadero.billing.models;

import org.springframework.data.annotation.Id;
import java.util.Date;
import java.util.List;

import com.parqueadero.billing.repositories.ClientRepository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Client {
    @Id
     private  String id;
     @Setter int serialBill;
     @Setter private String licensePlate;
     @Setter private Date entryDate;
     @Setter private Date exitDate;
     @Setter private int useTime;
     @Setter private double cost;
     @Setter private String state;
}