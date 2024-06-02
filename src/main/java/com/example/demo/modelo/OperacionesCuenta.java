package com.example.demo.modelo;

import java.util.Date;
import java.util.List;

public class OperacionesCuenta 
{
    private int _id;
    private String tipo;
    private String cuenta_afectada;
    private double valor;
    private Date fecha;
    private List<PuntoAtencion> punto_atencion;


    public int get_id() {
        return _id;
    }
    public void set_id(int _id) {
        this._id = _id;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public String getCuenta_afectada() {
        return cuenta_afectada;
    }
    public void setCuenta_afectada(String cuenta_afectada) {
        this.cuenta_afectada = cuenta_afectada;
    }
    public double getValor() {
        return valor;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }
    public Date getFecha() {
        return fecha;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public List<PuntoAtencion> getPunto_atencion() {
        return punto_atencion;
    }
    public void setPunto_atencion(List<PuntoAtencion> punto_atencion) {
        this.punto_atencion = punto_atencion;
    }
}