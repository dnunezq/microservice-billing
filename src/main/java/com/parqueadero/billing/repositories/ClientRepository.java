package com.parqueadero.billing.repositories;
import com.parqueadero.billing.models.Client;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface ClientRepository extends MongoRepository <Client, String> {
    Client findClientById(String id);
    List<Client> findByState(String state);
    List<Client> findByLicensePlateAndState(String licensePlate,String state);

}
