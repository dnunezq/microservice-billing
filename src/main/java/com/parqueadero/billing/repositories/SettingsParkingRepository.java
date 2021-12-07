package com.parqueadero.billing.repositories;
import com.parqueadero.billing.models.SettingsParking;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SettingsParkingRepository extends MongoRepository <SettingsParking, String> {}