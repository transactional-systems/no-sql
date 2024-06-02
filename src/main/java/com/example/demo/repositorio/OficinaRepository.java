package com.example.demo.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.modelo.Oficina;

public interface OficinaRepository extends MongoRepository<Oficina, String>
{
    
}