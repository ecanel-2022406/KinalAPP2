package com.edycanel.kinalapp.service;

import com.edycanel.kinalapp.entity.DetalleVenta;

import java.util.List;
import java.util.Optional;

public interface IDetalleVentaService {

    List<DetalleVenta> listarTodos();

    Optional<DetalleVenta> buscarPorCodigoDetalleVenta(int codigoDetalleVenta);

    DetalleVenta guardar(DetalleVenta detalleVenta);

    DetalleVenta actualizar(int codigoDetalleVenta, DetalleVenta detalleVenta);

    void eliminar(int codigoDetalleVenta);
}
