package com.ey.domain.entity;


import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "books")
public class Book extends BaseEntity {
 @Column(nullable = false, length = 180)
 private String title;

 @Column(nullable = false, length = 20, unique = true)
 private String isbn;

 @ManyToOne(optional = false, fetch = FetchType.LAZY)
 private Author author;

 @ManyToOne(optional = false, fetch = FetchType.LAZY)
 private Category category;

 @Column(nullable = false)
 private Integer totalCopies;

 @Column(nullable = false)
 private Integer availableCopies;

 public String getTitle() { return title; }
 public String getIsbn() { return isbn; }
 public Author getAuthor() { return author; }
 public Category getCategory() { return category; }
 public Integer getTotalCopies() { return totalCopies; }
 public Integer getAvailableCopies() { return availableCopies; }
 public void setTitle(String title) { this.title = title; }
 public void setIsbn(String isbn) { this.isbn = isbn; }
 public void setAuthor(Author author) { this.author = author; }
 public void setCategory(Category category) { this.category = category; }
 public void setTotalCopies(Integer totalCopies) { this.totalCopies = totalCopies; }
 public void setAvailableCopies(Integer availableCopies) { this.availableCopies = availableCopies; }

 @Override public boolean equals(Object o) {
     if (this == o) return true;
     if (!(o instanceof Book b)) return false;
     return Objects.equals(getId(), b.getId());
 }
 @Override public int hashCode() { return Objects.hash(getId()); }
}

