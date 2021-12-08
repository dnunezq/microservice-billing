package com.parqueadero.billing.models;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor

@ToString
@Getter
public class Earnings {
    @Id
    private  String id;
    @Setter
    private String day;
    @Setter private String month;
    @Setter private String year;
    @Setter private List<String> idClients;
    @Setter private double earnings;

    public Earnings(){
        this.idClients=new ArrayList<String>() ;
        this.earnings=0;
    }


}
