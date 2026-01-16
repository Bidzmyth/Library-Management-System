package com.ey.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AuthorCreateRequest {
 @NotBlank @Size(max = 120)
 private String fullName;
 @Size(max = 500)
 private String bio;

 public String getFullName() { return fullName; }
 public String getBio() { return bio; }
 public void setFullName(String fullName) { this.fullName = fullName; }
 public void setBio(String bio) { this.bio = bio; }
}

