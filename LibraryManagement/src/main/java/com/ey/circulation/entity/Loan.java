package com.ey.circulation.entity;

import java.time.LocalDateTime;

import com.ey.catalog.entity.ItemCopy;
import com.ey.iam.entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "loans")
public class Loan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@ManyToOne(optional = false)
	User user;
	@ManyToOne(optional = false)
	ItemCopy copy;
	LocalDateTime checkoutAt;
	LocalDateTime dueAt;
	LocalDateTime returnedAt;
	int renewCount;
	String createdBy;
	LocalDateTime createdAt;
	String updatedBy;
	LocalDateTime updatedAt;
}
