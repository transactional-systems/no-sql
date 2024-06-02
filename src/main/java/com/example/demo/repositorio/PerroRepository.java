package com.example.demo.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.modelo.Perro;


@Repository
public interface PerroRepository extends MongoRepository<Perro, String>{
    
    @Query("{nombre: ?0}")
    Perro findByNombre(String nombre);

}