package com.edycanel.kinalapp.service;

import com.edycanel.kinalapp.entity.DetalleVenta;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface IDetalleVentaService {

    @Transactional(readOnly = true)
    List<DetalleVenta> listarTodos();

    DetalleVenta guardar(DetalleVenta detalleVenta);

    Optional<DetalleVenta> buscarPorCodigoDetalleVenta(int codigoDetalleVenta);

    DetalleVenta actualizar(int codigoDetalleVenta, DetalleVenta detalleVenta);

    void eliminar(int codigoDetalleVenta);
}