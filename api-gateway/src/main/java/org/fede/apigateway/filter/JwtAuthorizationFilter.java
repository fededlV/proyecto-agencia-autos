package org.fede.apigateway.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.fede.apigateway.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");

        //Verificar que el encabezado contenga el token y comience con "Bearer "
        if(header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        //Extraer y validar token
        String token = header.replace("Bearer ", "");
        UsernamePasswordAuthenticationToken authentication = getAuthentication(token);

        //Configurar el contexto de autenticacion de Spring
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        //Validar el token y extraer los datos del usuario y roles
        if(jwtUtil.isTokenValid(token)) {
            String username = jwtUtil.getUsernameFromToken(token);
            var autorities = jwtUtil.getAuthoritiesFromToken(token);

            //Devolver el objeto autenticacion con el usuario y sus roles
            return new UsernamePasswordAuthenticationToken(username, null, autorities);
        }
        return null;
    }
}
