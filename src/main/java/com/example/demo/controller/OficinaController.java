package com.example.demo.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.modelo.Cliente;
import com.example.demo.modelo.Oficina;
import com.example.demo.repositorio.OficinaRepository;

@Controller
public class OficinaController 
{
    @Autowired
    private OficinaRepository oficinaRepository;

    //Crear Oficina

    @GetMapping("/oficina")
    public String oficinaForm(Model model) 
    {
        model.addAttribute("oficina", new Oficina());
        return "oficinaNueva";
    }

    @PostMapping("/oficina/agregar")
    public String insertarOficina(Oficina oficina) 
    {
        try 
        {
            if (oficina.getEmpleados() == null) 
            {
                oficina.setEmpleados(new ArrayList<>());
            }
            oficinaRepository.save(oficina);
            return "confirmacionOficinaCreada";
        }
        catch (Exception e) 
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al insertar la oficina", e);
        }
    }
    
}