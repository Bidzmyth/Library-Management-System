package com.ey.dto.request;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public class BookUpdateRequest {
 @Size(max = 180)
 private String title;
 @Size(max = 20)
 private String isbn;
 private Long authorId;
 private Long categoryId;
 @Min(1)
 private Integer totalCopies;
 private Integer availableCopies;

 public String getTitle() { return title; }
 public String getIsbn() { return isbn; }
 public Long getAuthorId() { return authorId; }
 public Long getCategoryId() { return categoryId; }
 public Integer getTotalCopies() { return totalCopies; }
 public Integer getAvailableCopies() { return availableCopies; }
 public void setTitle(String title) { this.title = title; }
 public void setIsbn(String isbn) { this.isbn = isbn; }
 public void setAuthorId(Long authorId) { this.authorId = authorId; }
 public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
 public void setTotalCopies(Integer totalCopies) { this.totalCopies = totalCopies; }
 public void setAvailableCopies(Integer availableCopies) { this.availableCopies = availableCopies; }
}

