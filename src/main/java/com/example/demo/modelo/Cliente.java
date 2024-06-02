package com.example.demo.modelo;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cliente")
public class Cliente 
{
    private String tipo_cliente;
    private String nombre;
    private String apellido;
    private String numero_identificacion;
    private String tipo_identificacion;
    private String login;
    private String clave;
    private String nacionalidad;
    private String direccion;
    private String correo;
    private String celular;
    private String ciudad;
    private String departamento;
    private String codigo_postal;
    private List<String> cuenta;

    public Cliente() {}

    public Cliente(String tipo_cliente, String nombre, String apellido, String numero_identificacion, String tipo_identificacion, String login, String clave, String nacionalidad, String direccion, String correo, String celular, String ciudad, String departamento, String codigo_postal, List<String> cuenta) 
    {
        this.tipo_cliente = tipo_cliente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.numero_identificacion = numero_identificacion;
        this.tipo_identificacion = tipo_identificacion;
        this.login = login;
        this.clave = clave;
        this.nacionalidad = nacionalidad;
        this.direccion = direccion;
        this.correo = correo;
        this.celular = celular;
        this.ciudad = ciudad;
        this.departamento = departamento;
        this.codigo_postal = codigo_postal;
        this.cuenta = cuenta;
    }

    public String getTipo_cliente() {
        return tipo_cliente;
    }

    public void setTipo_cliente(String tipo_cliente) {
        this.tipo_cliente = tipo_cliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNumero_identificacion() {
        return numero_identificacion;
    }

    public void setNumero_identificacion(String numero_identificacion) {
        this.numero_identificacion = numero_identificacion;
    }

    public String getTipo_identificacion() {
        return tipo_identificacion;
    }

    public void setTipo_identificacion(String tipo_identificacion) {
        this.tipo_identificacion = tipo_identificacion;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getCodigo_postal() {
        return codigo_postal;
    }

    public void setCodigo_postal(String codigo_postal) {
        this.codigo_postal = codigo_postal;
    }

    public List<String> getCuenta() {
        return cuenta;
    }

    public void setCuenta(List<String> cuenta) {
        this.cuenta = cuenta;
    }
}