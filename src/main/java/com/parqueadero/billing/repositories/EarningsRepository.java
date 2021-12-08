package com.parqueadero.billing.repositories;


import com.parqueadero.billing.models.Earnings;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EarningsRepository extends MongoRepository<Earnings, String> {
    List<Earnings> findByDay(String day);
    List<Earnings> findByMonth(String month);
    List<Earnings> findByYear(String year);

}
