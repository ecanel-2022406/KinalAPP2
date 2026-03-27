package com.edycanel.kinalapp.controller;

import com.edycanel.kinalapp.entity.DetalleVenta;
import com.edycanel.kinalapp.service.IDetalleVentaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/detalleventas")
public class DetalleVentaController {

    private final IDetalleVentaService detalleVentaService;

    public DetalleVentaController(IDetalleVentaService detalleVentaService){
        this.detalleVentaService = detalleVentaService;
    }

    @GetMapping
    public ResponseEntity<List<DetalleVenta>> listar(){
        List<DetalleVenta> detalleVentas = detalleVentaService.listarTodos();
        return ResponseEntity.ok(detalleVentas);
    }

    @GetMapping("/{codigoDetalleVenta}")
    public ResponseEntity<DetalleVenta> buscarPorDetalleVenta(@PathVariable int codigoDetalleVenta){
        return detalleVentaService.buscarPorCodigoDetalleVenta(codigoDetalleVenta)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody DetalleVenta detalleVenta){
        try{
            DetalleVenta nuevo = detalleVentaService.guardar(detalleVenta);
            return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{codigoDetalleVenta}")
    public ResponseEntity<?> actualizar(@PathVariable int codigoDetalleVenta,
                                        @RequestBody DetalleVenta detalleVenta){
        try{
            DetalleVenta actualizado = detalleVentaService.actualizar(codigoDetalleVenta, detalleVenta);
            return ResponseEntity.ok(actualizado);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{codigoDetalleVenta}")
    public ResponseEntity<Void> eliminar(@PathVariable int codigoDetalleVenta){
        try{
            detalleVentaService.eliminar(codigoDetalleVenta);
            return ResponseEntity.noContent().build();
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }
}