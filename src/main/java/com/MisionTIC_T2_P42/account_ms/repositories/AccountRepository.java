package com.MisionTIC_T2_P42.account_ms.repositories;

import com.MisionTIC_T2_P42.account_ms.models.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository <Account, String> { }
