package com.ey.catalog.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "item_copies")
public class ItemCopy {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@ManyToOne(optional = false)
	Item item;
	@Column(unique = true, nullable = false)
	String barcode;
	String shelfLocation;
	@Enumerated(EnumType.STRING)
	Status status = Status.AVAILABLE;

	public enum Status {
		AVAILABLE, ON_LOAN, LOST, DAMAGED, REPAIR
	}

	String createdBy;
	LocalDateTime createdAt;
	String updatedBy;
	LocalDateTime updatedAt;
}
