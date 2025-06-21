package com.assignmentdemo.ps.api.filter;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.assignmentdemo.ps.api.confriguration.UserInfoUserDetailsService;
import com.assignmentdemo.ps.api.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * Filter for processing JWT tokens in incoming HTTP requests.
 * This filter checks if the request contains a valid JWT token and sets the authentication context
 * if the token is valid.
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter{
	@Autowired
	private JwtService jwtService; // Service responsible for handling JWT token operations

	@Autowired
	private UserInfoUserDetailsService userDetailsService; // Service to load user details by username
	    
	    
	    /**
	     * This method processes the incoming request, extracts the JWT token from the Authorization header,
	     * and performs authentication if the token is valid.
	     *
	     * @param request the HTTP request
	     * @param response the HTTP response
	     * @param filterChain the filter chain to continue processing the request
	     * @throws ServletException if the filter cannot process the request
	     * @throws IOException if an input/output exception occurs during filter processing
	     */
	    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
				throws ServletException, IOException {
			String authorization = request.getHeader("Authorization");
			// Check if the Authorization header contains a valid Bearer token
			if(authorization != null && authorization.startsWith("Bearer ")) {
				String token = authorization.substring(7); // Extract the token from the Authorization header
				 // Extract the username from the token
				String username = jwtService.extractUsername(token);
				// If the username exists and no authentication is set, proceed with validation
				if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
					 // Load user details from the UserDetailsService
					UserDetails userDetails = userDetailsService.loadUserByUsername(username);
					// If the token is valid, authenticate the user
					if(jwtService.validateToken(token, userDetails)) {
						// Set the details of the request (e.g., remote address, session info)
						UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new 
								UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
						usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						// Set the authentication in the security context
						SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
					}
				}
			}
			 // Proceed with the filter chain
			filterChain.doFilter(request, response);
		}
}
