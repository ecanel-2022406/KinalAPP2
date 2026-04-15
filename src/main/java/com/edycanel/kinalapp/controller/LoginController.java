package com.edycanel.kinalapp.controller;

import com.edycanel.kinalapp.service.IUsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    private final IUsuarioService usuarioService;

    public LoginController(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String username,
                                @RequestParam String password,
                                Model model) {

        return usuarioService.login(username, password)
                .map(user -> "redirect:/home")
                .orElseGet(() -> {
                    model.addAttribute("error", true);
                    return "login";
                });
    }
}