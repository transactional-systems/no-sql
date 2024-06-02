package com.example.demo.modelo;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(value="perro")
public class Perro {
    private String nombre;
    private String raza;

    public Perro(String nombre, String raza) {
        super();
        this.nombre = nombre;
        this.raza = raza;
    }

    public Perro() {;}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    
}
