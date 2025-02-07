package com.ashokit.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashokit.entity.Customer;

public interface CustomerDao extends JpaRepository<Customer, Integer> {

}
