package com.example.demo.modelo;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "oficina")
public class Oficina 
{
    @Id
    private int _id;
    private String nombre;
    private String direccion;
    private int numero_puntos_atencion;
    private List<String> empleados;
    
    public Oficina() {;}

    public Oficina(int _id, String nombre, String direccion, int numero_puntos_atencion, List<String> empleados) 
    {
        this._id = _id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.numero_puntos_atencion = numero_puntos_atencion;
        this.empleados = empleados;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getNumero_puntos_atencion() {
        return numero_puntos_atencion;
    }

    public void setNumero_puntos_atencion(int numero_puntos_atencion) {
        this.numero_puntos_atencion = numero_puntos_atencion;
    }

    public List<String> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<String> empleados) {
        this.empleados = empleados;
    }    
}    