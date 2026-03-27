package com.edycanel.kinalapp.service;

import com.edycanel.kinalapp.entity.DetalleVenta;
import com.edycanel.kinalapp.repository.DetalleVentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DetalleVentaService implements IDetalleVentaService {

    private final DetalleVentaRepository detalleVentaRepository;

    public DetalleVentaService(DetalleVentaRepository detalleVentaRepository){
        this.detalleVentaRepository = detalleVentaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetalleVenta> listarTodos(){
        return detalleVentaRepository.findAll();
    }

    @Override
    public DetalleVenta guardar(DetalleVenta detalleVenta) {
        validarDetalleVenta(detalleVenta);
        detalleVenta.setSubtotal(
                detalleVenta.getPrecioUnitario()
                        .multiply(BigDecimal.valueOf(detalleVenta.getCantidad()))
        );
        return detalleVentaRepository.save(detalleVenta);
    }

    @Override
    public Optional<DetalleVenta> buscarPorCodigoDetalleVenta(int codigoDetalleVenta) {
        return detalleVentaRepository.findById(codigoDetalleVenta);
    }

    @Override
    public DetalleVenta actualizar(int codigoDetalleVenta, DetalleVenta detalleVenta) {
        if (!detalleVentaRepository.existsById(codigoDetalleVenta)) {
            throw new RuntimeException("DetalleVenta no encontrado");
        }

        detalleVenta.setCodigoDetalleVenta(codigoDetalleVenta);
        validarDetalleVenta(detalleVenta);

        detalleVenta.setSubtotal(
                detalleVenta.getPrecioUnitario()
                        .multiply(BigDecimal.valueOf(detalleVenta.getCantidad()))
        );

        return detalleVentaRepository.save(detalleVenta);
    }

    @Override
    public void eliminar(int codigoDetalleVenta) {
        if (!detalleVentaRepository.existsById(codigoDetalleVenta)) {
            throw new RuntimeException("DetalleVenta no encontrado");
        }
        detalleVentaRepository.deleteById(codigoDetalleVenta);
    }

    private void validarDetalleVenta(DetalleVenta detalleVenta){
        if (detalleVenta.getCantidad() <= 0){
            throw new IllegalArgumentException("Cantidad inválida");
        }

        if (detalleVenta.getPrecioUnitario() == null){
            throw new IllegalArgumentException("Precio requerido");
        }
    }
}