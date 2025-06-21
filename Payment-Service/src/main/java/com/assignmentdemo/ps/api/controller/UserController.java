package com.assignmentdemo.ps.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignmentdemo.ps.api.Entity.AuthRequest;
import com.assignmentdemo.ps.api.Entity.UserInfo;
import com.assignmentdemo.ps.api.exceptionhandler.AuthenticationException;
import com.assignmentdemo.ps.api.service.JwtService;
import com.assignmentdemo.ps.api.service.UserManagementService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private UserManagementService userManagementService;

	private JwtService jwtService;

	private AuthenticationManager authenticationManager;

	@Autowired
	public UserController(UserManagementService userManagementService, JwtService jwtService,
			AuthenticationManager authenticationManager) {
		this.userManagementService = userManagementService;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
	}
	
	/**
	 * Adds a new user to the system.
	 * Accepts a UserInfo object in the request body and returns the result of the addUser service method.
	 */
	@PostMapping
	public String user(@RequestBody UserInfo userInfo) {
		return userManagementService.addUser(userInfo);
	}
	/**
	 * Authenticates the user and generates a JWT token if authentication is successful.
	 * If authentication fails, an appropriate exception is thrown.
	 */
	@PostMapping("/authenticate")
	public String generateToken(@RequestBody AuthRequest authRequest) {
		try {
			// Authenticate the user using the provided credential
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

			if (authentication.isAuthenticated()) {
				return jwtService.generateToken(authRequest.getUsername());
			} else {
				throw new AuthenticationException("Authentication failed for user: " + authRequest.getUsername());
			}
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException(e.getMessage());
		}
		catch(AuthenticationException e) {
			throw e;
		}
		catch (Exception e) {
			throw new RuntimeException("Authentication failed due to an unexpected error: " + e.getMessage(), e);
		}
	}

}
