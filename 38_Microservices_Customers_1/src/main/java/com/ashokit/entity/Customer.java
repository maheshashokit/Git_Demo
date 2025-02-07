package com.ashokit.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="shopping_customers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="customer_id")
	private Integer id;

	@Column(name="name")
	private String name;
	
	@Column(name="location")
	private String location;
	
	@Column(name="gender")
	private String gender;
	
	@Column(name="email_id")
	private String emailId;
	
	@Column(name="contact_no")
	private String contactNo;
	
	@Column(name="created_dt",updatable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;
	
	@Column(name="updated_dt",insertable = false)
	@UpdateTimestamp
	private LocalDateTime updatedDate;	
}
