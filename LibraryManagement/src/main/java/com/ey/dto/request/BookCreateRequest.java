package com.ey.dto.request;


import jakarta.validation.constraints.*;

public class BookCreateRequest {
 @NotBlank @Size(max = 180)
 private String title;
 @NotBlank @Size(max = 20)
 private String isbn;
 @NotNull
 private Long authorId;
 @NotNull
 private Long categoryId;
 @NotNull @Min(1)
 private Integer totalCopies;

 public String getTitle() { return title; }
 public String getIsbn() { return isbn; }
 public Long getAuthorId() { return authorId; }
 public Long getCategoryId() { return categoryId; }
 public Integer getTotalCopies() { return totalCopies; }
 public void setTitle(String title) { this.title = title; }
 public void setIsbn(String isbn) { this.isbn = isbn; }
 public void setAuthorId(Long authorId) { this.authorId = authorId; }
 public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
 public void setTotalCopies(Integer totalCopies) { this.totalCopies = totalCopies; }
}

