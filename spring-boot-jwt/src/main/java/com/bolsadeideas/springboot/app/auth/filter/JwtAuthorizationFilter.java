package com.bolsadeideas.springboot.app.auth.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.bolsadeideas.springboot.app.auth.SimpleGrantedAuthorityMixin;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	public static final String AUTHENTICATION_SCHEME_BASIC = "Bearer ";
	public static final SecretKey secretKey = new SecretKeySpec("AlgunaLlaveSecreta.78/#sfhj34576{~2345234".getBytes(),
			SignatureAlgorithm.HS256.getJcaName());

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String header = request.getHeader("Authorization");

		if (!requiresAuthentication(header)) {
			chain.doFilter(request, response);
			return;
		}

		boolean tokenValid;

		Claims token = null;

		try {
			token = Jwts.parserBuilder()
					.setSigningKey(secretKey)
					.build()
					.parseClaimsJws(header.replace("Bearer ", ""))
					.getBody();

			tokenValid = true;
		} catch (JwtException | IllegalArgumentException e) {
			tokenValid = false;
		}

		UsernamePasswordAuthenticationToken authentication = null;

		if (tokenValid) {
			String username = token.getSubject();
			Object roles = token.get("authorities");

			Collection<? extends GrantedAuthority> authorities = Arrays
					.asList(new ObjectMapper()
							.addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class)
							.readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class));

			authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
		}

		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
	}

	protected boolean requiresAuthentication(String header) {
		if (header == null || !header.startsWith(AUTHENTICATION_SCHEME_BASIC)) {
			return false;
		}

		return true;
	}

}
