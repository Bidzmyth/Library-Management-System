package com.ey.dto.response;


public class BookResponse {
 private Long id;
 private String title;
 private String isbn;
 private Long authorId;
 private String authorName;
 private Long categoryId;
 private String categoryName;
 private Integer totalCopies;
 private Integer availableCopies;

 public Long getId() { return id; }
 public String getTitle() { return title; }
 public String getIsbn() { return isbn; }
 public Long getAuthorId() { return authorId; }
 public String getAuthorName() { return authorName; }
 public Long getCategoryId() { return categoryId; }
 public String getCategoryName() { return categoryName; }
 public Integer getTotalCopies() { return totalCopies; }
 public Integer getAvailableCopies() { return availableCopies; }
 public void setId(Long id) { this.id = id; }
 public void setTitle(String title) { this.title = title; }
 public void setIsbn(String isbn) { this.isbn = isbn; }
 public void setAuthorId(Long authorId) { this.authorId = authorId; }
 public void setAuthorName(String authorName) { this.authorName = authorName; }
 public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
 public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
 public void setTotalCopies(Integer totalCopies) { this.totalCopies = totalCopies; }
 public void setAvailableCopies(Integer availableCopies) { this.availableCopies = availableCopies; }
}

