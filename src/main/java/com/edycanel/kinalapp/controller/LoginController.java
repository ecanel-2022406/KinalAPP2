package com.edycanel.kinalapp.controller;

import com.edycanel.kinalapp.entity.Usuario;
import com.edycanel.kinalapp.service.IUsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class LoginController {

    private final IUsuarioService usuarioService;

    public LoginController(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Mostrar login
    @GetMapping("/")
    public String mostrarLogin() {
        return "login";
    }

    // Procesar login
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        Model model) {

        Optional<Usuario> usuario = usuarioService.login(username, password);

        if (usuario.isPresent()) {
            return "menu"; // Redirige al menú
        } else {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            return "login";
        }
    }

    // Mostrar menú
    @GetMapping("/menu")
    public String mostrarMenu() {
        return "menu";
    }
}