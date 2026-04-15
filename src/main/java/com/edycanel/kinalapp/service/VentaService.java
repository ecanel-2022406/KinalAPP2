package com.edycanel.kinalapp.service;

import com.edycanel.kinalapp.entity.Cliente;
import com.edycanel.kinalapp.entity.Usuario;
import com.edycanel.kinalapp.entity.Venta;
import com.edycanel.kinalapp.repository.ClienteRepository;
import com.edycanel.kinalapp.repository.UsuarioRepository;
import com.edycanel.kinalapp.repository.VentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VentaService implements IVentaService {

    private final VentaRepository ventaRepository;
    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;

    public VentaService(VentaRepository ventaRepository,
                        ClienteRepository clienteRepository,
                        UsuarioRepository usuarioRepository) {
        this.ventaRepository = ventaRepository;
        this.clienteRepository = clienteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venta> listarTodos() {
        return ventaRepository.findAll();
    }

    @Override
    public Venta guardar(Venta venta) {
        if (venta.getTotal() == null) {
            venta.setTotal(BigDecimal.ZERO);
        }

        Cliente cliente = clienteRepository.findById(
                venta.getCliente().getDpiCliente()
        ).orElseThrow(() -> new IllegalArgumentException("Cliente no existe"));

        Usuario usuario = usuarioRepository.findById(
                venta.getUsuario().getCodigoUsuario()
        ).orElseThrow(() -> new IllegalArgumentException("Usuario no existe"));

        venta.setCliente(cliente);
        venta.setUsuario(usuario);

        if (venta.getEstado() == 0) {
            venta.setEstado(1);
        }

        validarVenta(venta);

        return ventaRepository.save(venta);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Venta> buscarPorCodigoVenta(int codigoVenta) {
        return ventaRepository.findById(codigoVenta);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venta> buscarPorEstado(int estado) {
        return ventaRepository.findAll()
                .stream()
                .filter(v -> v.getEstado() == estado)
                .toList();
    }

    @Override
    public Venta actualizar(int codigoVenta, Venta venta) {
        Venta existente = ventaRepository.findById(codigoVenta)
                .orElseThrow(() -> new RuntimeException("Venta no existe"));

        Cliente cliente = clienteRepository.findById(
                venta.getCliente().getDpiCliente()
        ).orElseThrow(() -> new IllegalArgumentException("Cliente no existe"));

        Usuario usuario = usuarioRepository.findById(
                venta.getUsuario().getCodigoUsuario()
        ).orElseThrow(() -> new IllegalArgumentException("Usuario no existe"));

        existente.setFechaVenta(venta.getFechaVenta());
        existente.setTotal(
                venta.getTotal() == null ? BigDecimal.ZERO : venta.getTotal()
        );
        existente.setEstado(venta.getEstado());
        existente.setCliente(cliente);
        existente.setUsuario(usuario);

        validarVenta(existente);

        return ventaRepository.save(existente);
    }

    @Override
    public void eliminar(int codigoVenta) {
        if (!ventaRepository.existsById(codigoVenta)) {
            throw new RuntimeException("Venta no encontrada");
        }
        ventaRepository.deleteById(codigoVenta);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorCodigoVenta(int codigoVenta) {
        return ventaRepository.existsById(codigoVenta);
    }

    private void validarVenta(Venta venta) {
        if (venta == null) {
            throw new IllegalArgumentException("Venta null");
        }
        if (venta.getFechaVenta() == null) {
            throw new IllegalArgumentException("Fecha obligatoria");
        }
        if (venta.getTotal().doubleValue() < 0) {
            throw new IllegalArgumentException("Total inválido");
        }
        if (venta.getCliente() == null || venta.getUsuario() == null) {
            throw new IllegalArgumentException("Cliente y usuario obligatorios");
        }
    }
}