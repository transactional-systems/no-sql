package com.example.demo.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.AccumulatorOperators.Max;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

import com.example.demo.modelo.Cuenta;
import com.example.demo.modelo.OperacionesCuenta;
import com.example.demo.modelo.PuntoAtencion;
import com.example.demo.repositorio.CuentaRepository;

@Slf4j
@Controller
public class CuentaController {

    @Autowired
    private CuentaRepository cuentaRepository;

    // Crear Punto Atencion

    public Cuenta obtenerCuentaPorNumero(String numeroCuenta) {
        return cuentaRepository.findByNumeroCuenta(numeroCuenta);
    }

    // Crear Cuenta

    @PostMapping("/cuenta/new/save")
    public String insertarCuenta(@RequestBody Cuenta cuenta) {
        try {
            if (cuenta.getOperaciones_cuenta() == null) {
                cuenta.setOperaciones_cuenta(new ArrayList<>());
            }
            cuentaRepository.save(cuenta);
            return "confirmacionCuentaCreada";
        } catch (Exception e) {
            log.error("Error al insertar la cuenta", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al insertar la cuenta", e);
        }
    }

    @GetMapping("/cuenta/new")
    public String cuentaForm(Model model) {
        model.addAttribute("cuenta", new Cuenta());
        return "cuentaNueva";
    }

    @GetMapping("/cuenta/newfiltro")
    public String cuentaFormFiltro(Model model) {
        return "cuentaFiltroN";
    }

    @GetMapping("/cuenta/newextracto")
    public String cuentaFormExtracto(Model model) {
        return "OperacionesCuenta";
    }

    private Date parseFecha(String fecha) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.parse(fecha);
    }

    @PostMapping("/cuenta/new/savefiltro")
    public String cuentaFormFiltroFin(Model model, String TipoCuenta, Integer minSaldo, Integer Maxsaldo,
        String fecha_creacion, String cliente, String fecha_ultimo) throws ParseException {
        boolean HayFecha = fecha_creacion == null || ("".equals(fecha_creacion));
        boolean Haymin = minSaldo == null;
        boolean Haymax = Maxsaldo == null;
        boolean HayCliente = cliente == null || ("".equals(cliente));
        boolean HayTipo = TipoCuenta == null || ("".equals(TipoCuenta));
        boolean Hayultimo = fecha_ultimo == null || ("".equals(fecha_ultimo));

        Collection<Cuenta> cuentas = null;
        if (!HayFecha && !Haymin && !Haymax && !HayTipo && !Hayultimo) {
            cuentas = cuentaRepository.findByTipoAndSaldoBetween(TipoCuenta, minSaldo, Maxsaldo);
            List<Cuenta> cuentasFiltradas = new ArrayList<>();

            Date fechaCreacion = parseFecha(fecha_creacion);
            Date fechaUltimo = parseFecha(fecha_ultimo);

            for (Cuenta cuenta : cuentas) {
                Date fechaCuenta = new SimpleDateFormat("yy-MM-dd")
                        .parse(new SimpleDateFormat("yy-MM-dd").format(cuenta.getFecha_creacion()));

                Date fechaCuenta1 = new SimpleDateFormat("yy-MM-dd")
                        .parse(new SimpleDateFormat("yy-MM-dd").format(cuenta.getFecha_ultima_transaccion()));

                
                if (fechaCuenta.equals(fechaCreacion) && fechaCuenta1.equals(fechaUltimo)) {
                    cuentasFiltradas.add(cuenta);
                }
            }

            cuentas = cuentasFiltradas;
        } else if (!HayFecha && !Haymin && !Haymax && !HayTipo) {
            cuentas = cuentaRepository.findByTipoAndSaldoBetween(TipoCuenta, minSaldo, Maxsaldo);
            List<Cuenta> cuentasFiltradas = new ArrayList<>();

            Date fechaCreacion = parseFecha(fecha_creacion);

            for (Cuenta cuenta : cuentas) {
                Date fechaCuenta = new SimpleDateFormat("yy-MM-dd")
                        .parse(new SimpleDateFormat("yy-MM-dd").format(cuenta.getFecha_creacion()));
                System.out.println(fechaCuenta);
                
                if (fechaCuenta.equals(fechaCreacion)) {
                    cuentasFiltradas.add(cuenta);
                }
            }

            cuentas = cuentasFiltradas;
        } else if (HayCliente && !HayFecha && Haymin && Haymax && HayTipo) {
            cuentas = cuentaRepository.findAllCuentasExceptOperacionesCuenta();
            List<Cuenta> cuentasFiltradas = new ArrayList<>();

            Date fechaCreacion = parseFecha(fecha_creacion);

            for (Cuenta cuenta : cuentas) {
                Date fechaCuenta = new SimpleDateFormat("yy-MM-dd")
                        .parse(new SimpleDateFormat("yy-MM-dd").format(cuenta.getFecha_creacion()));
                System.out.println(fechaCuenta);

                if (fechaCuenta.equals(fechaCreacion)) {
                    cuentasFiltradas.add(cuenta);
                }
            }

            cuentas = cuentasFiltradas;

        } else if (HayCliente && HayFecha && (!Haymin || !Haymax) && HayTipo) {
            if (Haymin) {
                minSaldo = 0;
                cuentas = cuentaRepository.findBySaldoBetween(minSaldo, Maxsaldo);
            }
            else if (Haymax) {
                Maxsaldo = Integer.MAX_VALUE;
                cuentas = cuentaRepository.findBySaldoBetween(minSaldo, Maxsaldo);
            }
            else {
                cuentas = cuentaRepository.findBySaldoBetween(minSaldo, Maxsaldo);
            }
        } else if (HayCliente && HayFecha && Haymin && Haymax && !HayTipo) {
            cuentas = cuentaRepository.darCuentasPortipo(TipoCuenta);
        } else {
            cuentas = cuentaRepository.findAllCuentasExceptOperacionesCuenta();
        }

        System.out.println(
                "+----------+--------------+--------------+------------+---------+------------------------------+------------------------------+");
        System.out.println(
                "|    ID    |     Tipo     |    Estado    |  N°Cuenta  |  Saldo  |   Fecha Ultima Transaccion   |      Fecha de Creacion       |");
        System.out.println(
                "+----------+--------------+--------------+------------+---------+------------------------------+------------------------------+");

        // Iterar sobre cada cuenta e imprimir sus atributos
        for (Cuenta cuenta : cuentas) {
            String id = cuenta.getId().substring(0, Math.min(cuenta.getId().length(), 8)); 
            System.out.printf("| %-8s | %-12s | %-12s | %-10s | %7d | %-24s | %-28s |\n",
                    id,
                    cuenta.getTipo(),
                    cuenta.getEstado(),
                    cuenta.getNumeroCuenta(),
                    cuenta.getSaldo(),
                    cuenta.getFecha_ultima_transaccion(),
                    cuenta.getFecha_creacion());
        }

        // Imprimir línea inferior de la tabla
        System.out.println(
                "+----------+--------------+--------------+------------+---------+------------------------------+------------------------------+");

        return "index";
    }

    @PostMapping("/cuenta/new/saveextracto")
    public String cuentaFormFiltroFin(Model model, String numeroOrigen, String Mes) {
        Boolean HayCuenta = (numeroOrigen == null || "".equals(numeroOrigen));
        Boolean HayMes = (Mes == null || "".equals(Mes));
        if (!HayCuenta && !HayMes) {
            LocalDateTime ahora = LocalDateTime.now();
            String fechaActual = ahora.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String[] partesFechaActual = fechaActual.split("-");
            String yearActual = partesFechaActual[0];
            Cuenta cuenta = null;
            Collection<Cuenta> cuentas = cuentaRepository.findAllCuentasExceptOperacionesCuenta();
            for (Cuenta cuentab : cuentas) {
                if (cuentab.getId().equals(numeroOrigen)) {
                    cuenta = cuentab;
                    break;
                }
            }
            List<OperacionesCuenta> operacionesCuentas = cuenta.getOperaciones_cuenta();
            Double Dinero = Double.valueOf(cuenta.getSaldo());

            SimpleDateFormat sdf = new SimpleDateFormat("MM");
            String mesSiguiente = "";
            try {
                Date date = sdf.parse(Mes);
                mesSiguiente = sdf.format(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
            int mesInicial = Integer.parseInt(mesSiguiente)+1;
            for (int i = mesInicial; i <= 12; i++) {
                String mes = String.format("%02d", i);
                for (OperacionesCuenta operacion : operacionesCuentas) {

                    String tipoOperacion = operacion.getTipo();
                    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
                    SimpleDateFormat monthFormat = new SimpleDateFormat("MM");

                    String yearOperacion = yearFormat.format(operacion.getFecha());
                    String mesOperacion = monthFormat.format(operacion.getFecha());

                    if (mesOperacion.equals(mes) && yearActual.equals(yearOperacion)) {
                        if (tipoOperacion.equals("consignar")) {
                            Dinero -= operacion.getValor();
                        } else if (tipoOperacion.equals("retirar")) {
                            Dinero += operacion.getValor();
                        } else if (tipoOperacion.equals("transferir")) {
                            Dinero += operacion.getValor();
                        }
                    }
                }
            }
            double saldo = Dinero;
            double valorInicial = saldo;
            if (!Mes.equals("12")) {
                for (OperacionesCuenta operacion : operacionesCuentas) {

                    String tipoOperacion = operacion.getTipo();
                    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
                    SimpleDateFormat monthFormat = new SimpleDateFormat("MM");

                    String yearOperacion = yearFormat.format(operacion.getFecha());
                    String mesOperacion = monthFormat.format(operacion.getFecha());

                    if (mesOperacion.equals(Mes) && yearActual.equals(yearOperacion)) {
                        if (tipoOperacion.equals("consignar")) {
                            valorInicial -= operacion.getValor();
                        } else if (tipoOperacion.equals("retirar")) {
                            valorInicial += operacion.getValor();
                        } else if (tipoOperacion.equals("transferir")) {
                            valorInicial += operacion.getValor();
                        }
                    }

                }
            } else {
                for (OperacionesCuenta operacion : operacionesCuentas) {

                    String tipoOperacion = operacion.getTipo();
                    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
                    SimpleDateFormat monthFormat = new SimpleDateFormat("MM");

                    String yearOperacion = yearFormat.format(operacion.getFecha());
                    String mesOperacion = monthFormat.format(operacion.getFecha());

                    if (mesOperacion.equals(Mes) && yearActual.equals(yearOperacion)) {
                        if (tipoOperacion.equals("consignar")) {
                            saldo += operacion.getValor();
                        } else if (tipoOperacion.equals("retirar")) {
                            saldo -= operacion.getValor();
                        } else if (tipoOperacion.equals("transferir")) {
                            saldo -= operacion.getValor();
                        }
                    }
                }
            }

            List<OperacionesCuenta> operacionesFiltradas = new ArrayList<>();

            for (OperacionesCuenta operacion : operacionesCuentas) {
                SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
                String mesOperacion = monthFormat.format(operacion.getFecha());

                if (mesOperacion.equals(Mes)) {
                    operacionesFiltradas.add(operacion);
                }
            }

            System.out.println("Este es el saldo inicial: " + valorInicial);
            System.out.println("Este es el saldo final: " + saldo);

            System.out.println(
                "+----------+--------------+------------------+---------+------------+");
            System.out.println(
                "|  Cuenta  |     Tipo     | Cuenta Afectada  |  Valor  |    Fecha   |");
            System.out.println(
                "+----------+--------------+------------------+---------+------------+");
            
            for (OperacionesCuenta operacion : operacionesFiltradas) {
                String id = cuenta.getId().substring(0, Math.min(cuenta.getId().length(), 8));
                String fechaOperacion = new SimpleDateFormat("yyyy-MM-dd").format(operacion.getFecha());
            
                System.out.printf("| %-8s | %-12s | %-16s | %7.2f | %-10s |\n",
                    id,
                    operacion.getTipo(),
                    operacion.getCuenta_afectada(),
                    operacion.getValor(),
                    fechaOperacion
                );
            }
            
            System.out.println(
                "+----------+--------------+------------------+---------+------------+");
            
        }
        return "index";
    }

    // Cerrar cuenta

    @GetMapping("/cuenta/closed")
    public String cerrarCuenta(Model model) {
        model.addAttribute("numeroCuenta", new Cuenta());
        return "cuentaCerrada";
    }

    @PostMapping("/operacioncuenta/{numeroCuenta}/close")
    public String operacionCerrarCuenta(@PathVariable("numeroCuenta") String numeroCuenta) {
        cuentaRepository.cerrarCuenta(numeroCuenta);
        return "confirmacionCuentaCerrada";
    }

    // Desactivar cuenta

    @GetMapping("/cuenta/deactivated")
    public String desactivarCuenta(Model model) {
        model.addAttribute("numeroCuenta", new Cuenta());
        return "cuentaDesactivada";
    }

    @PostMapping("/operacioncuenta/{numeroCuenta}/deactivate")
    public String operacionDesactivarCuenta(@PathVariable("numeroCuenta") String numeroCuenta) {
        cuentaRepository.desactivarCuenta(numeroCuenta);
        return "confirmacionCuentaDesactivada";
    }

    // Insertar Punto Atencion

    @GetMapping("/punto/new")
    public String puntoForm(Model model) {
        model.addAttribute("puntoAtencion", new PuntoAtencion());
        return "crearPuntoAtencion";
    }

    // @PostMapping("/cuenta/{idCuenta}/operacion/{tipoOperacion}/puntoatencion/agregar")
    // public String insertarPuntoAtencion(@ModelAttribute PuntoAtencion
    // puntoAtencion, @PathVariable String idCuenta, @PathVariable String
    // tipoOperacion)
    // {
    // cuentaRepository.insertarPuntoAtencion(tipoOperacion, 1);
    // return "redirect:/confirmacionPuntoCreado";
    // }

    @PostMapping("/cuenta/{idCuenta}/operacion/{tipoOperacion}/puntoatencion/agregar")
    public String insertarPuntoAtencion(@RequestParam("tipo") String tipo,
            @RequestParam("operaciones_validas") List<String> operacionesValidas,
            @RequestParam("oficina") int oficina) {
        PuntoAtencion puntoAtencion = new PuntoAtencion();
        puntoAtencion.setTipo(tipo);
        puntoAtencion.setOperaciones_validas(operacionesValidas);
        puntoAtencion.setOficina(oficina);

        cuentaRepository.insertarPuntoAtencion(puntoAtencion);
        return "redirect:/confirmacionPuntoCreado";
    }

    @GetMapping("/confirmacionPuntoCreado")
    public String mostrarConfirmacionPuntoCreado() {
        return "confirmacionPuntoCreado";
    }

    // Borrar Punto Atencion

    @PostMapping("/cuenta/{idCuenta}/operacion/{tipoOperacion}/puntoatencion/eliminar")
    public String borrarPuntoAtencion(@PathVariable("idCuenta") String idCuenta,
            @PathVariable("tipoOperacion") String tipoOperacion, @RequestParam("oficina") int oficina) {
        cuentaRepository.borrarPuntoAtencion(oficina);
        return "redirect:/confirmacionPuntoBorrado";
    }

    @GetMapping("/puntoatencion/eliminar")
    public String confirmacionPuntoBorrado() {
        return "borrarPuntoAtencion";
    }

    @GetMapping("/confirmacionPuntoBorrado")
    public String mostrarConfirmacionPuntoBorrado() {
        return "borrarPuntoAtencion";
    }

    // Consignar

    @GetMapping("/operacioncuenta/consignar")
    public String cuentaConsignar(Model model) {
        return "consignar";
    }

    @PostMapping("/consignar")
    public String consignarDinero(@RequestParam("cuentaDestino") String numeroCuenta, @RequestParam("monto") int valor,
            RedirectAttributes redirectAttributes) {
        try {
            cuentaRepository.sumarSaldoDestino(numeroCuenta, valor);
            redirectAttributes.addFlashAttribute("mensaje", "Consignación realizada exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al realizar la consignación: " + e.getMessage());
            return "redirect:/confirmacionConsignacion";
        }
        return "redirect:/confirmacionConsignacion";
    }

    @GetMapping("/confirmacionConsignacion")
    public String mostrarConfirmacionConsignacion() {
        return "confirmacionConsignacion";
    }

    // Retirar

    @GetMapping("/operacioncuenta/retirar")
    public String cuentaRetirar(Model model) {
        model.addAttribute("numerocuenta", new Cuenta());
        return "retirar";
    }

    @PostMapping("/retirar")
    @Transactional
    public String retirarDinero(@RequestParam("cuentaDestino") String numeroCuenta, @RequestParam("monto") int valor,
            RedirectAttributes redirectAttributes) {
        try {
            cuentaRepository.restarSaldoDestino(numeroCuenta, valor);
            redirectAttributes.addFlashAttribute("mensaje", "Retiro realizado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al realizar el retiro: " + e.getMessage());
            return "redirect:/confirmacionRetiro";
        }

        return "redirect:/confirmacionRetiro";
    }

    @GetMapping("/confirmacionRetiro")
    public String mostrarConfirmacionRetiro() {
        return "confirmacionRetiro";
    }

    // Transferir

    @GetMapping("/operacioncuenta/transferir")
    public String cuentaTransferir(Model model) {
        model.addAttribute("numerocuenta", new Cuenta());
        return "transferir";
    }

    @PostMapping("/transferir")
    @Transactional
    public String transferirDinero(@RequestParam("cuentaOrigen") String numeroCuentaOrigen,
            @RequestParam("cuentaDestino") String numeroCuentaDestino, @RequestParam("monto") int valor,
            RedirectAttributes redirectAttributes) {
        try {
            cuentaRepository.restarSaldoTransferir(numeroCuentaOrigen, numeroCuentaDestino, (valor) * -1);
            cuentaRepository.sumarSaldoTransferir(numeroCuentaDestino, numeroCuentaOrigen, valor);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al realizar el retiro: " + e.getMessage());
            return "redirect:/confirmacionTransferencia";
        }

        return "redirect:/confirmacionTransferencia";
    }

    @GetMapping("/confirmacionTransferencia")
    public String mostrarConfirmacionTransferencia() {
        return "confirmacionTransferencia";
    }
}