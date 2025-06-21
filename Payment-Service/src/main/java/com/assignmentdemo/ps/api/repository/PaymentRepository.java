package com.assignmentdemo.ps.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assignmentdemo.ps.api.Entity.PaymentResponse;


public interface PaymentRepository extends JpaRepository<PaymentResponse, Integer> {

}
