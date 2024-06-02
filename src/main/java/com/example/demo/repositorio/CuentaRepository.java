package com.example.demo.repositorio;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.data.repository.query.Param;

import com.example.demo.modelo.Cuenta;
import com.example.demo.modelo.PuntoAtencion;

public interface CuentaRepository extends MongoRepository<Cuenta, String>
{

    @Query(value = "{}", fields = "{ 'operaciones_cuenta.punto_atencion' : 0 }")
    Collection<Cuenta> findAllCuentasExceptOperacionesCuenta();


    @Query(value = "{numeroCuenta: ?0}", fields = "{ 'operaciones_cuenta.punto_atencion' : 0 }")
    Cuenta findByNumeroCuenta(String numeroCuenta);

    @Query("{tipo: ?0, estado: ?1, numeroCuenta: ?2, saldo: ?3, fecha_ultima_transaccion: ?4, fecha_creacion: ?5, operaciones_cuenta: [ null ] }")
    Cuenta insertarCuenta(String tipo, String estado, String numeroCuenta, int saldo, Date fecha_ultima_transaccion, Date fecha_creacion);

    @Query("{numeroCuenta: ?0}")
    @Update("{ '$set': { estado: 'Cerrada' }}")
    void cerrarCuenta(String numeroCuenta);

    @Query("{numeroCuenta: ?0}")
    @Update("{ '$set': { estado: 'Desactivada' }}")
    void desactivarCuenta(String numeroCuenta);

    @Query("{'numeroCuenta': '123' }, { $push: { 'operaciones_cuenta.$.punto_atencion': ?0 } }")
    PuntoAtencion insertarPuntoAtencion(PuntoAtencion puntoAtencion);

    // @Query("{'numeroCuenta': '123' }") 
    // @Update("$push: {'operaciones_cuenta.$[].punto_atencion': {tipo: 'digital', operaciones_validas: ['consignar', 'retirar'],oficina: 1}}") // { upsert: false }
    // void insertarPuntoAtencion(PuntoAtencion puntoAtencion);

    @Query("{ 'numeroCuenta': '123', 'operaciones_cuenta._id': 1 }")
    @Update("{ $pull: { 'operaciones_cuenta.$.punto_atencion': { 'tipo': 'digital', 'oficina': ?0 } } }")
    void borrarPuntoAtencion(int oficina);

    @Query(("{'numeroCuenta': ?0 }"))
    @Update("{$push: { 'operaciones_cuenta': { _id: 4, tipo: 'consignar', cuenta_afectada: ?0, valor: ?1, fecha: new Date(), punto_atencion: []}}, $inc: { 'saldo': ?1 }, $set: { 'fecha_ultima_transaccion': new Date() }})")
    void sumarSaldoDestino(String numeroCuenta, int valor);

    // @Query("{ 'numeroCuenta': ?0 }")
    // @Update("{$push: { 'operaciones_cuenta': {_id: 5, tipo: 'retirar', cuenta_afectada: ?0, valor: -?1, fecha: new Date(), punto_atencion: [] }}, $inc: { 'saldo': -?1 }, $set: { 'fecha_ultima_transaccion': new Date() }}")
    // void restarSaldoDestino(String numeroCuenta, int valor);

    @Query("{ 'numeroCuenta': ?0 }")
    @Update("{$push: { 'operaciones_cuenta': {_id: 5, tipo: 'retirar', cuenta_afectada: ?0, valor: ?1, fecha: new Date(), punto_atencion: [] }}, $inc: { 'saldo': ?1 }, $set: { 'fecha_ultima_transaccion': new Date() }}")
    void restarSaldoDestino(String numeroCuenta, int valor);

    //Transferir
    
    @Query("{ 'numeroCuenta': ?0 }")
    @Update("{$push: { 'operaciones_cuenta': {_id: 5, tipo: 'transferir', cuenta_afectada: ?0, valor: ?2, fecha: new Date(), punto_atencion: [] }}, $inc: { 'saldo': ?2 }, $set: { 'fecha_ultima_transaccion': new Date() }}")
    void restarSaldoTransferir(String numeroCuenta, String numeroCuenta2, int valor);

    @Query(("{'numeroCuenta': ?1 }"))
    @Update("{$push: { 'operaciones_cuenta': { _id: 4, tipo: 'transferir', cuenta_afectada: ?1, valor: ?2, fecha: new Date(), punto_atencion: []}}, $inc: { 'saldo': ?2 }, $set: { 'fecha_ultima_transaccion': new Date() }})")
    void sumarSaldoTransferir(String numeroCuenta, String numeroCuenta2, int valor);

    @Query(value = "{'tipo' : ?0, 'saldo' : { $gte: ?1, $lte: ?2 }}", fields = "{ 'operaciones_cuenta' : 0 }")
    Collection<Cuenta> findByTipoAndSaldoBetween(String tipo, Integer minSaldo, Integer maxSaldo);


    @Query(value = "{'tipo' : ?0}", fields = "{ 'operaciones_cuenta' : 0 }")
    Collection<Cuenta> darCuentasPortipo(@Param("tipo") String tipo);

    @Query(value = "{ 'saldo' : { $gte: ?0, $lte: ?1 } }", fields = "{ 'operaciones_cuenta' : 0 }")
    Collection<Cuenta> findBySaldoBetween(Integer minSaldo, Integer maxSaldo);

    @Query(value = "{'fecha_creacion' : ?0}", fields = "{ 'operaciones_cuenta' : 0 }")
    Collection<Cuenta> darCuentasPorFecha(@Param("fecha_creacion") Date fechaCreacion); 

    @Query(value = "{'cliente' : ?0}", fields = "{ 'operaciones_cuenta' : 0 }")
    Collection<Cuenta> darCuentasPorCliente(@Param("cliente") String cliente);
}