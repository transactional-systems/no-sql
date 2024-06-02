package com.example.demo.repositorio;

import com.example.demo.modelo.Cliente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ClienteRepository extends MongoRepository<Cliente, String>
{
    @Query("{numero_identificacion: ?0}")
    Cliente findByNumeroIdentificacion(String numeroId);


}