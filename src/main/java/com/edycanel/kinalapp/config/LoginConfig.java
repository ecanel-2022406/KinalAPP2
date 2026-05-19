package com.edycanel.kinalapp.config;

import com.edycanel.kinalapp.entity.Usuario;
import com.edycanel.kinalapp.service.IUsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
public class LoginConfig {

    private final IUsuarioService usuarioService;

    public LoginConfig(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Bean
    public UserDetailsService userDetailsService() {

        return username -> {

            Usuario usuario = usuarioService.buscarPorUsername(username)
                    .orElseThrow(() ->
                            new UsernameNotFoundException("Usuario no encontrado"));

            return new User(
                    usuario.getUsername(),
                    usuario.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRol()))
            );
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**/*.css", "/**/*.js").permitAll()
                        .requestMatchers("/", "/login", "/css/**", "/js/**").permitAll()
                        .requestMatchers("/menu").authenticated()
                        .requestMatchers("/clientes-vista").hasRole("ADMIN")
                        .requestMatchers("/productos-vista").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/usuarios-vista").hasRole("ADMIN")
                        .requestMatchers("/ventas-vista").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/detalleventas-vista").authenticated()
                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/menu", true)
                        .failureUrl("/?error=true")
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutSuccessUrl("/?logout=true")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}