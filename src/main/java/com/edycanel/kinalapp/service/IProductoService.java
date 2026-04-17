package com.edycanel.kinalapp.service;

import com.edycanel.kinalapp.entity.Producto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface IProductoService {

    @Transactional(readOnly = true)
    List<Producto> listarTodos();

    Producto guardar(Producto producto);

    Optional<Producto> buscarPorCodigoProducto(Integer codigoProducto);

    @Transactional(readOnly = true)
    List<Producto> buscarPorEstado(Integer estado);

    Producto actualizar(Integer codigoProducto, Producto producto);

    void eliminar(Integer codigoProducto);

    boolean existePorCodigoProducto(Integer codigoProducto);
}