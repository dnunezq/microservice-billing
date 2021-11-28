package com.parqueadero.billing.models;

import org.springframework.data.annotation.Id;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Client {
    @Id
    @Getter private  String id;
    @Getter @Setter private String licensePlate;
    @Getter @Setter private Date entryDate;
    @Getter @Setter private Date exitDate;
    @Getter @Setter private int useTime;
    @Getter @Setter private double cost;
    @Getter @Setter private String state;



}