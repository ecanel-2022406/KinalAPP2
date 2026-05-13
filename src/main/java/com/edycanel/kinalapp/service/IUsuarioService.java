package com.edycanel.kinalapp.service;

import com.edycanel.kinalapp.entity.Usuario;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {

    @Transactional(readOnly = true)
    List<Usuario> listarTodos();

    Usuario guardar(Usuario usuario);

    Optional<Usuario> buscarPorCodigoUsuario(Integer codigoUsuario);

    @Transactional(readOnly = true)
    List<Usuario> buscarPorEstado(Integer estado);

    Usuario actualizar(Integer codigoUsuario, Usuario usuario);

    void eliminar(Integer codigoUsuario);

    boolean existePorCodigoUsuario(Integer codigoUsuario);

    Optional<Usuario> login(String username, String password);

    Optional<Usuario> buscarPorUsername(String username);
}