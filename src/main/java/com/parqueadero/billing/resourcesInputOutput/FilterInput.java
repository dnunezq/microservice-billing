package com.parqueadero.billing.resourcesInputOutput;

import com.parqueadero.billing.models.Client;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class FilterInput {
    private String typeDate;
    private String date;
}
