package com.ey.finance.entity;


import java.time.LocalDateTime;

import com.ey.circulation.entity.Loan;
import com.ey.iam.entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "fines")
public class Fine {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@ManyToOne
	User user;
	@ManyToOne
	Loan loan;
	java.math.BigDecimal amount;
	@Enumerated(EnumType.STRING)
	Reason reason;
	@Enumerated(EnumType.STRING)
	Status status = Status.PENDING;
	LocalDateTime paidAt;
	String createdBy;
	LocalDateTime createdAt;
	String updatedBy;
	LocalDateTime updatedAt;

	public enum Reason {
		LATE, LOST, DAMAGE
	}

	public enum Status {
		PENDING, PAID, WAIVED
	}
}
