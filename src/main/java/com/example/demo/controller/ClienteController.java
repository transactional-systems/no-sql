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
import com.example.demo.repositorio.ClienteRepository;

@Controller
public class ClienteController 
{

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente obtenerClientePorNumero(String numeroId) 
    {
        return clienteRepository.findByNumeroIdentificacion(numeroId);
    }


    //Crear cliente

    @GetMapping("/cliente")
    public String clienteForm(Model model) 
    {
        model.addAttribute("cliente", new Cliente());
        return "clienteNuevo";
    }

    @PostMapping("/clientes/agregar")
    public String insertarCliente(Cliente cliente) 
    {
        try 
        {
            if (cliente.getCuenta() == null) 
            {
                cliente.setCuenta(new ArrayList<>());
            }
            clienteRepository.save(cliente);
            return "confirmacionClienteCreado";
        }
        catch (Exception e) 
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al insertar el cliente", e);
        }
    }
}