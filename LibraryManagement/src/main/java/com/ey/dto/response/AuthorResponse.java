package com.ey.dto.response;


public class AuthorResponse {
 private Long id;
 private String fullName;
 private String bio;

 public Long getId() { return id; }
 public String getFullName() { return fullName; }
 public String getBio() { return bio; }
 public void setId(Long id) { this.id = id; }
 public void setFullName(String fullName) { this.fullName = fullName; }
 public void setBio(String bio) { this.bio = bio; }
}

