package com.example.demo.Repositories;

import com.example.demo.Models.Clientes;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;


/**
 * @author Sonia Sanabria
 */
public interface RepositorioClientes extends MongoRepository <Clientes,String> {
    Clientes findByfactura(Integer factura);  
    
      }

   