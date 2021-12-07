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
@Getter
public class Client {
    @Id
     private  String id;
     @Setter private String billNumber;
     @Setter private String licensePlate;
     @Setter private Date entryDate;
     @Setter private Date exitDate;
     @Setter private int useTime;
     @Setter private double cost;
     @Setter private String state;    
}