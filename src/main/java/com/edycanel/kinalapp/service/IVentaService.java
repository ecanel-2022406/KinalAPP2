package com.edycanel.kinalapp.service;

import com.edycanel.kinalapp.entity.Venta;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface IVentaService {

    @Transactional(readOnly = true)
    List<Venta> listarTodos();

    Venta guardar(Venta venta);

    Optional<Venta> buscarPorCodigoVenta(Integer codigoVenta);

    @Transactional(readOnly = true)
    List<Venta> buscarPorEstado(Integer estado);

    Venta actualizar(Integer codigoVenta, Venta venta);

    void eliminar(Integer codigoVenta);

    boolean existePorCodigoVenta(Integer codigoVenta);
}