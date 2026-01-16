package com.ey.dto.request;


import jakarta.validation.constraints.Size;

public class CategoryUpdateRequest {
 @Size(max = 80)
 private String name;
 @Size(max = 300)
 private String description;

 public String getName() { return name; }
 public String getDescription() { return description; }
 public void setName(String name) { this.name = name; }
 public void setDescription(String description) { this.description = description; }
}


