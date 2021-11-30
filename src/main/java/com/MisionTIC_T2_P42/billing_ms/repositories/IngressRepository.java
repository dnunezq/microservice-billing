package com.MisionTIC_T2_P42.billing_ms.repositories;


import com.MisionTIC_T2_P42.billing_ms.models.Ingress;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IngressRepository extends MongoRepository <Ingress, String> {
    List<Ingress> findByStatus (String status);
    List<Ingress> findByLicensePlateAndStatus (String licensePlate,String status);
}



