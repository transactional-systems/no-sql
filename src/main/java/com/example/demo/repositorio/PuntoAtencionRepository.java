package com.example.demo.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import com.example.demo.modelo.PuntoAtencion;



public interface PuntoAtencionRepository extends MongoRepository<PuntoAtencion, String>
{
    @Query("{'numeroCuenta': '123' },{$push: {'operaciones_cuenta.$[].punto_atencion': {tipo: ?0, operaciones_validas: ['consignar', 'retirar'],oficina: ?1}},},{ upsert: false }")
    PuntoAtencion insertarPuntoAtencion(String tipoOperacion, int idOficina);
}
