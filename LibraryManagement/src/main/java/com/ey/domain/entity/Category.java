package com.ey.domain.entity;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {
 @Column(nullable = false, unique = true, length = 80)
 private String name;

 @Column(length = 300)
 private String description;

 public String getName() { return name; }
 public String getDescription() { return description; }
 public void setName(String name) { this.name = name; }
 public void setDescription(String description) { this.description = description; }

 @Override public boolean equals(Object o) {
     if (this == o) return true;
     if (!(o instanceof Category c)) return false;
     return Objects.equals(getId(), c.getId());
 }
 @Override public int hashCode() { return Objects.hash(getId()); }
}

