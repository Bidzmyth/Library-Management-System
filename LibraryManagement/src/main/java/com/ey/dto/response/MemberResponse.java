package com.ey.dto.response;

public class MemberResponse {
 private Long id;
 private String fullName;
 private String email;
 private String status;

 public Long getId() { return id; }
 public String getFullName() { return fullName; }
 public String getEmail() { return email; }
 public String getStatus() { return status; }
 public void setId(Long id) { this.id = id; }
 public void setFullName(String fullName) { this.fullName = fullName; }
 public void setEmail(String email) { this.email = email; }
 public void setStatus(String status) { this.status = status; }
}

