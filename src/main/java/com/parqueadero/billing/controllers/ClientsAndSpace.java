package com.parqueadero.billing.controllers;

import com.parqueadero.billing.models.Client;
import lombok.*;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class ClientsAndSpace {
    private List<Client> clients;
    private int capacity;
}
