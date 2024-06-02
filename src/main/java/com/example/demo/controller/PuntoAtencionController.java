package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.modelo.PuntoAtencion;

import com.example.demo.repositorio.PuntoAtencionRepository;

@Controller
public class PuntoAtencionController 
{
    @Autowired
    private PuntoAtencionRepository puntoAtencionRepository;

    // @GetMapping("/punto/new")
    // public String puntoForm(Model model) 
    // {
    //     model.addAttribute("puntoAtencion", new PuntoAtencion());
    //     return "crearPuntoAtencion";
    // }

    // @PostMapping("/cuenta/{idOficina}/operacion/{tipoOperacion}/puntoatencion/agregar")
    // public String insertarPuntoAtencion(@RequestBody PuntoAtencion puntoAtencion) 
    // {
    //     puntoAtencionRepository.save(puntoAtencion);
    //     return "redirect:/confirmacionPuntoCreado";
    // }

    // @Controller
    // public class RedireccionController 
    // {
    //     @GetMapping("/confirmacionPuntoCreado")
    //     public String confirmacionPuntoCreado() 
    //     {
    //         return "confirmacionPuntoCreado";
    //     }
    // }

}