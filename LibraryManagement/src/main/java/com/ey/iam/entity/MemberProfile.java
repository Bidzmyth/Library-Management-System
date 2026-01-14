package com.ey.iam.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "member_profiles")
public class MemberProfile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;
	private String fullName;
	private String department;
	private Integer year;
	private Integer points;
	@Enumerated(EnumType.STRING)
	private MembershipType membershipType = MembershipType.STUDENT;
	private String createdBy;
	private LocalDateTime createdAt;
	private String updatedBy;
	private LocalDateTime updatedAt;

	public enum MembershipType {
		STUDENT, FACULTY, EXTERNAL
	}
}
