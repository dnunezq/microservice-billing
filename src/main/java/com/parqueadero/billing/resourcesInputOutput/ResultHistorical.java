package com.parqueadero.billing.resourcesInputOutput;

import com.parqueadero.billing.models.Client;
import lombok.*;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class ResultHistorical {

    private List<Client> clients;
    private double earnings;


}
