package com.edycanel.kinalapp.service;

import com.edycanel.kinalapp.entity.DetalleVenta;
import com.edycanel.kinalapp.entity.Producto;
import com.edycanel.kinalapp.entity.Venta;
import com.edycanel.kinalapp.repository.DetalleVentaRepository;
import com.edycanel.kinalapp.repository.ProductoRepository;
import com.edycanel.kinalapp.repository.VentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DetalleVentaService implements IDetalleVentaService {

    private final DetalleVentaRepository detalleRepo;
    private final VentaRepository ventaRepo;
    private final ProductoRepository productoRepo;

    public DetalleVentaService(DetalleVentaRepository detalleRepo,
                               VentaRepository ventaRepo,
                               ProductoRepository productoRepo) {
        this.detalleRepo = detalleRepo;
        this.ventaRepo = ventaRepo;
        this.productoRepo = productoRepo;
    }

    @Override
    public List<DetalleVenta> listarTodos() {
        return detalleRepo.findAll();
    }

    @Override
    public Optional<DetalleVenta> buscarPorCodigoDetalleVenta(int codigoDetalleVenta) {
        return detalleRepo.findById(codigoDetalleVenta);
    }

    @Override
    public DetalleVenta guardar(DetalleVenta detalle) {

        Venta venta = ventaRepo.findById(
                detalle.getVenta().getCodigoVenta()
        ).orElseThrow(() -> new IllegalArgumentException("Venta no existe"));

        Producto producto = productoRepo.findById(
                detalle.getProducto().getCodigoProducto()
        ).orElseThrow(() -> new IllegalArgumentException("Producto no existe"));

        detalle.setVenta(venta);
        detalle.setProducto(producto);

        BigDecimal subtotal = detalle.getPrecioUnitario()
                .multiply(BigDecimal.valueOf(detalle.getCantidad()));

        detalle.setSubtotal(subtotal);

        return detalleRepo.save(detalle);
    }

    @Override
    public DetalleVenta actualizar(int codigoDetalleVenta, DetalleVenta detalle) {

        DetalleVenta existente = detalleRepo.findById(codigoDetalleVenta)
                .orElseThrow(() -> new RuntimeException("Detalle no encontrado"));

        Venta venta = ventaRepo.findById(
                detalle.getVenta().getCodigoVenta()
        ).orElseThrow(() -> new IllegalArgumentException("Venta no existe"));

        Producto producto = productoRepo.findById(
                detalle.getProducto().getCodigoProducto()
        ).orElseThrow(() -> new IllegalArgumentException("Producto no existe"));

        existente.setCantidad(detalle.getCantidad());
        existente.setPrecioUnitario(detalle.getPrecioUnitario());
        existente.setVenta(venta);
        existente.setProducto(producto);

        BigDecimal subtotal = detalle.getPrecioUnitario()
                .multiply(BigDecimal.valueOf(detalle.getCantidad()));

        existente.setSubtotal(subtotal);

        return detalleRepo.save(existente);
    }

    @Override
    public void eliminar(int codigoDetalleVenta) {
        if (!detalleRepo.existsById(codigoDetalleVenta)) {
            throw new RuntimeException("Detalle no encontrado");
        }
        detalleRepo.deleteById(codigoDetalleVenta);
    }
}