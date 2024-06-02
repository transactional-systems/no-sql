package com.example.demo.modelo;

import java.util.List;

public class PuntoAtencion 
{
    private String tipo;
    private List<String> operaciones_validas;
    private int oficina;

    public String getTipo() 
    {
        return tipo;
    }
    public void setTipo(String tipo) 
    {
        this.tipo = tipo;
    }
    public List<String> getOperaciones_validas() 
    {
        return operaciones_validas;
    }
    public void setOperaciones_validas(List<String> operaciones_validas) 
    {
        this.operaciones_validas = operaciones_validas;
    }
    public int getOficina() 
    {
        return oficina;
    }
    public void setOficina(int oficina) 
    {
        this.oficina = oficina;
    }
}
