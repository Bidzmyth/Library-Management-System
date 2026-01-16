package com.ey.dto.request;



import jakarta.validation.constraints.*;

public class MemberCreateRequest {
 @NotBlank @Size(max = 120)
 private String fullName;
 @NotBlank @Email @Size(max = 160)
 private String email;

 public String getFullName() { return fullName; }
 public String getEmail() { return email; }
 public void setFullName(String fullName) { this.fullName = fullName; }
 public void setEmail(String email) { this.email = email; }
}

