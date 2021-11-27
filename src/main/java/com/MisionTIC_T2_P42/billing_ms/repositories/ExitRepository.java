package com.MisionTIC_T2_P42.billing_ms.repositories;

import com.MisionTIC_T2_P42.billing_ms.models.Exit;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;


public interface ExitRepository extends MongoRepository<Exit, String> {
    List<Exit> findByLicensePlateExit (String licensePlateExit);
}





