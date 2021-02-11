/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bolsadeideas.springboot.app.auth.filter;

import com.bolsadeideas.springboot.app.models.entity.Usuario;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
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

	public static final SecretKey secretKey = new SecretKeySpec("AlgunaLlaveSecreta.78/#sfhj34576{~2345234".getBytes(),
			SignatureAlgorithm.HS256.getJcaName());

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

		if (username != null && password != null) {
			logger.info("Username desde request parameter (form-data): " + username);
			logger.info("Password desde request parameter (form-data): " + password);
		} else {
//        	TODO: Obteniendo los datos en bruto (raw)
			Usuario user = null;
			try {
//        		TODO: Usando new ObjectMapper().readValue() para transformar JSON a Objeto (Usuario)
				user = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);

//				TODO: Importante que los parametros JSON en bruto que se indiquen, sean enviados con el mismo 
//				nombre de los atributos de la clase

				username = user.getUsername();
				password = user.getPassword();

				username = username.trim();

				logger.info("Username desde request InputStream (raw): " + username);
				logger.info("Password desde request InputStream (raw): " + password);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);

//        return super.attemptAuthentication(request, response); //To change body of generated methods, choose Tools | Templates.
		return authenticationManager.authenticate(authToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

//        String username = authResult.getName();
		String username = ((User) authResult.getPrincipal()).getUsername();

		Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();

		Claims claims = Jwts.claims();
//        TODO: Usando new ObjectMapper().writeValueAsString() para transformar Objeto a JSON
		claims.put("authorities", new ObjectMapper().writeValueAsString(roles));

		String token = Jwts.builder().setClaims(claims).setSubject(username).signWith(secretKey)
				.setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + (3600000L * 4L)))
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

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("mensaje", "Error de autenticación: usuario o contraseña invalido!");
		body.put("error", failed.getMessage());

		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
		response.setStatus(401);
		response.setContentType("application/js");
	}

}
