package com.edycanel.kinalapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VistaController {

    @GetMapping("/clientes-vista")
    public String clientes(){
        return "clientes";
    }

    @GetMapping("/productos-vista")
    public String productos(){
        return "productos";
    }

    @GetMapping("/usuarios-vista")
    public String usuarios(){
        return "usuarios";
    }

    @GetMapping("/ventas-vista")
    public String ventas(){
        return "ventas";
    }

    @GetMapping("/detalleventas-vista")
    public String detalle(){
        return "detalleventas";
    }
}