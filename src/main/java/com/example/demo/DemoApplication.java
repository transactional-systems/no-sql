package com.example.demo;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.modelo.Cuenta;
import com.example.demo.modelo.Perro;
import com.example.demo.repositorio.PerroRepository;
import com.example.demo.repositorio.CuentaRepository;


@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	PerroRepository perroRepository;

	@Autowired
	CuentaRepository cuentaRepository;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Perro perro = perroRepository.findByNombre("Toby");

		System.out.println("EMPIEZA A IMPRIMIR -------------------");
		System.out.println(perro.getNombre());
	}


	// @Override
	// public void run(String... args) throws Exception 
	// {

	// 	String tipo = "Ahorros";
	// 	String estado = "Activa";
	// 	String numeroCuenta = "654";
	// 	int saldo = 10000;
	// 	Date fecha_ultima_transaccion = new Date();
	// 	Date fecha_creaccion = new Date();

	// 	Cuenta cuenta = cuentaRepository.insertarCuenta(tipo, estado, numeroCuenta, saldo, fecha_ultima_transaccion, fecha_creaccion);

	// 	System.out.println("EMPIEZA A IMPRIMIR -------------------");
	// 	System.out.println(cuenta.getNumeroCuenta());
	// }

}