package com.ey.domain.entity;


import jakarta.persistence.*;
import java.time.OffsetDateTime;

@MappedSuperclass
public abstract class BaseEntity {
 @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 private OffsetDateTime createdAt;
 private OffsetDateTime updatedAt;

 @PrePersist
 public void prePersist() {
     this.createdAt = OffsetDateTime.now();
     this.updatedAt = this.createdAt;
 }

 @PreUpdate
 public void preUpdate() {
     this.updatedAt = OffsetDateTime.now();
 }

 public Long getId() { return id; }
 public OffsetDateTime getCreatedAt() { return createdAt; }
 public OffsetDateTime getUpdatedAt() { return updatedAt; }
 public void setId(Long id) { this.id = id; }
 public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
 public void setUpdatedAt(OffsetDateTime updatedAt) { this.updatedAt = updatedAt; }
}

