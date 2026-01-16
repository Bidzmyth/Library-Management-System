package com.ey.domain.entity;


import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "authors")
public class Author extends BaseEntity {
 @Column(nullable = false, length = 120)
 private String fullName;

 @Column(length = 500)
 private String bio;

 public String getFullName() { return fullName; }
 public String getBio() { return bio; }
 public void setFullName(String fullName) { this.fullName = fullName; }
 public void setBio(String bio) { this.bio = bio; }

 @Override public boolean equals(Object o) {
     if (this == o) return true;
     if (!(o instanceof Author a)) return false;
     return Objects.equals(getId(), a.getId());
 }
 @Override public int hashCode() { return Objects.hash(getId()); }
}

