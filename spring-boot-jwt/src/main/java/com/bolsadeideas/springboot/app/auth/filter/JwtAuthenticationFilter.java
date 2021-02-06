/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bolsadeideas.springboot.app.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 *
 * @author pj
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
//        TODO: Por defecto la ruta de login es "/login", se modifica de la siguiente manera
        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) 
            throws AuthenticationException {

//        TODO: Por defecto los parametros que se manejan son username y password
//        String username = obtainUsername(request); 
//        String password = obtainPassword(request);
//        TODO: Para parametros personalizados se usa el request y se solicita el parametro a traves del metodo getParameter()
        String username = request.getParameter("usuario");
        String password = request.getParameter("pass");

        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }

        if (username != null && password != null) {
            logger.info("Username desde request parameter (form-data): " + username);
            logger.info("Password desde request parameter (form-data): " + password);
        }

        username = username.trim();

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);

//        return super.attemptAuthentication(request, response); //To change body of generated methods, choose Tools | Templates.
        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, 
            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        
//        String username = authResult.getName();
        String username = ((User) authResult.getPrincipal()).getUsername();
        
        Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();
        
        Claims claims = Jwts.claims();
        claims.put("authorities", new ObjectMapper().writeValueAsString(roles));
        
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .signWith(Keys.secretKeyFor(SignatureAlgorithm.HS512), SignatureAlgorithm.HS512)
//                .signWith(SignatureAlgorithm.HS512, "Alguna.Clave.Secreta.12345".getBytes("UTF-8"))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (3600000L*4L)))
                .compact();
        
        response.addHeader("Authorization", "Bearer " + token);
        
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("token", token);
        body.put("user", (User) authResult.getPrincipal());
        body.put("mensaje", String.format("Hola %s, Ha iniciado sesion con exito!", username));
        
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(200);
        response.setContentType("application/json");
        
//        super.successfulAuthentication(request, response, chain, authResult); //To change body of generated methods, choose Tools | Templates.
    }
    
    

}
