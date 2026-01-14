package com.ey.notify.entity;

import java.time.LocalDateTime;

import com.ey.iam.entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "notifications")
public class Notification {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@ManyToOne
	User user;
	@Enumerated(EnumType.STRING)
	Channel channel = Channel.EMAIL;
	String template;
	@Lob
	String payload;
	@Enumerated(EnumType.STRING)
	Status status = Status.PENDING;
	LocalDateTime sentAt;
	String createdBy;
	LocalDateTime createdAt;
	String updatedBy;
	LocalDateTime updatedAt;

	public enum Channel {
		EMAIL, SMS
	}

	public enum Status {
		PENDING, SENT, FAILED
	}
}
