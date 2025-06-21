package com.assignmentdemo.ps.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assignmentdemo.ps.api.Entity.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
	 Optional<UserInfo> findByusername(String username);
}
