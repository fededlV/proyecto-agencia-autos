package org.fede.apigateway.config;

import org.fede.apigateway.filter.JwtAuthenticationFilter;
import org.fede.apigateway.filter.JwtAuthorizationFilter;
import org.fede.apigateway.util.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtUtil jwtUtil;

    public SecurityConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        http
                .csrf((csrf -> csrf.disable())) //Deshabilita CSRF ya que fue eliminado a partir de Spring security 6
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/obtener").permitAll() //Permitir acceso sin autenticacion  para ver empleados
                        .requestMatchers("/crear", "/enviarNotificacion").hasRole("EMPLEADO")
                        .requestMatchers("/enviarPosicion").hasRole("USUARIO_ASOCIADO")
                        .requestMatchers("/verReportes").hasRole("ADMINISTRADOR")
                        .anyRequest().authenticated()
                )
                //.addFilter(new JwtAuthenticationFilter(authenticationManager, jwtUtil))
                //.addFilter(new JwtAuthorizationFilter(authenticationManager, jwtUtil))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // Aplicacion sin estado

        return http.build();
    }
}
