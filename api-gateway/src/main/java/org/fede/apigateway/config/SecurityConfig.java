package org.fede.apigateway.config;

import org.fede.apigateway.filter.JwtAuthenticationFilter;
import org.fede.apigateway.filter.JwtAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationManager authenticationManager;

    public SecurityConfig(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrf -> csrf.disable())) //Deshabilita CSRF ya que fue eliminado a partir de Spring security 6
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/crear", "/enviarNotificacion").hasRole("EMPLEADO")
                        .requestMatchers("/enviarPosicion").hasRole("USUARIO_ASOCIADO")
                        .requestMatchers("/verReportes").hasRole("ADMINISTRADOR")
                        .anyRequest().authenticated()
                )
                .addFilter(new JwtAuthenticationFilter(authenticationManager))
                .addFilter(new JwtAuthorizationFilter(authenticationManager));

        return http.build();
    }
}
