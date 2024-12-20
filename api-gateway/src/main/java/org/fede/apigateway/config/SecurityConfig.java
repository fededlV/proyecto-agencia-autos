package org.fede.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrf -> csrf.disable())) //Deshabilita CSRF ya que fue eliminado a partir de Spring security 6
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/pruebas/crear", "/api/notificaciones/enviarNotificacion").hasRole("EMPLEADO")
                        .requestMatchers("/api/posiciones/enviarPosicion").hasRole("VEHICULO")
                        .requestMatchers("/api/reportes/verReportes").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // Aplicacion sin estado

        return http.build();
    }

    //Metodo para convertir los roles de JWT a GrantedAuthority
    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        var jwtAuthenticationConverter = new JwtAuthenticationConverter();
        var grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

        //Especificar el nombre del claim a analizar
        grantedAuthoritiesConverter.setAuthoritiesClaimName("realm_access.roles");
        // Agregar prefijo por convencion de Spring
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        // Se asocia el conversor de Authorities al Bean que convierte el token JWT a un objeto Authorization
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return JwtDecoders.fromIssuerLocation("https://labsys.frc.utn.edu.ar/aim/realms/backend-tps");
    }
}

/*JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            Map<String, Object> realmAcces = (Map<String, Object>) jwt.getClaims().get("realm_access");
            if (realmAcces != null) {
                Object rolesObj = realmAcces.get("roles");
                if(rolesObj instanceof List) {
                    List<String> roles = (List<String>) rolesObj;
                    authorities.addAll(roles.stream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                            .collect(Collectors.toList()));
                }
            }
            return authorities;
         });
        return jwtAuthenticationConverter;*/