package com.stackroute.muzixmanager.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

import com.stackroute.muzixmanager.constant.AuthConstant;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JwtFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		final HttpServletRequest httpRequest = (HttpServletRequest) request;
		final HttpServletResponse httpResponse = (HttpServletResponse) response;
		final String authHeader = httpRequest.getHeader(AuthConstant.AUTHORIZATION);
		
		if(AuthConstant.METHOD_OPTIONS.equals(httpRequest.getMethod())){
			httpResponse.setStatus(HttpServletResponse.SC_OK);
			chain.doFilter(request, response);
		} else {
			if(authHeader == null || !authHeader.startsWith(AuthConstant.BEARER)) {
				throw new ServletException("Missing or invalid authorization header!");
			}
			final String token = authHeader.substring(7);
			final Claims claims = Jwts.parser().setSigningKey(AuthConstant.BASE64_ENCODED_KEY).parseClaimsJws(token).getBody();
			request.setAttribute(AuthConstant.CLAIMS, claims);			
			chain.doFilter(request, response);
		}		
	}
}
