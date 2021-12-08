package com.parqueadero.billing.resourcesInputOutput;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class FilterInput {
    private String typeDate;
    private String date;
}
