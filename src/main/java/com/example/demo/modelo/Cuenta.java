package com.example.demo.modelo;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Document(collection = "cuenta")
public class Cuenta 
{
    
    @Id
    private String id;
    private String tipo;
    private String estado;
    private String numeroCuenta;
    private int saldo;
    private Date fecha_ultima_transaccion;
    private Date fecha_creacion;
    private List<OperacionesCuenta> operaciones_cuenta;

    


    public Cuenta(String id, String tipo, String estado, String numeroCuenta, int saldo, Date fecha_ultima_transaccion, Date fecha_creacion, List<OperacionesCuenta> operaciones_cuenta) 
    {
        this.id = id;
        this.tipo = tipo;
        this.estado = estado;
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldo;
        this.fecha_ultima_transaccion = fecha_ultima_transaccion;
        this.fecha_creacion = fecha_creacion;
        this.operaciones_cuenta = operaciones_cuenta;
    }

    public Cuenta() {;}

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public String getNumeroCuenta() {
        return numeroCuenta;
    }
    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }
    public int getSaldo() {
        return saldo;
    }
    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }
    public Date getFecha_ultima_transaccion() {
        return fecha_ultima_transaccion;
    }
    public void setFecha_ultima_transaccion(Date fecha_ultima_transaccion) {
        this.fecha_ultima_transaccion = fecha_ultima_transaccion;
    }
    public Date getFecha_creacion() {
        return fecha_creacion;
    }
    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }
    public List<OperacionesCuenta> getOperaciones_cuenta() {
        return operaciones_cuenta;
    }
    public void setOperaciones_cuenta(List<OperacionesCuenta> operaciones_cuenta) {
        this.operaciones_cuenta = operaciones_cuenta;
    }
}