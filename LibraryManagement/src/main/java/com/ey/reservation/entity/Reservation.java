package com.ey.reservation.entity;



import java.time.LocalDateTime;

import com.ey.catalog.entity.Item;
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
@Table(name = "reservations")
public class Reservation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@ManyToOne
	User user;
	@ManyToOne
	Item item;
	@Enumerated(EnumType.STRING)
	Status status = Status.PENDING;
	int position;
	LocalDateTime notifiedAt;
	LocalDateTime expiresAt;
	String createdBy;
	LocalDateTime createdAt;
	String updatedBy;
	LocalDateTime updatedAt;

	public enum Status {
		PENDING, READY, EXPIRED, CANCELLED
	}
}
