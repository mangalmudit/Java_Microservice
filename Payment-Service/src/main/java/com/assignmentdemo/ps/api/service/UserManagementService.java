package com.assignmentdemo.ps.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.assignmentdemo.ps.api.Entity.UserInfo;
import com.assignmentdemo.ps.api.repository.UserInfoRepository;

@Service
public class UserManagementService {
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
    private UserInfoRepository repository;
	
	private static final Logger logger = LoggerFactory.getLogger(UserManagementService.class);
	
	/**
	 * Adds a new user to the system after encoding the user's password.
	 * Saves the user information to the repository and returns a confirmation message.
	 * 
	 * @param userInfo The user details to be added to the system.
	 * @return A confirmation message indicating that the user has been added.
	 */
	public String addUser(UserInfo userInfo) {
		logger.info("Attempting to add user with username: {}", userInfo.getUsername());
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
     
        repository.save(userInfo);
        return "user added to system ";

}
}
