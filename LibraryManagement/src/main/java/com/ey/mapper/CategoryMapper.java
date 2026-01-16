package com.ey.mapper;

import com.ey.domain.entity.Category;
import com.ey.dto.request.CategoryCreateRequest;
import com.ey.dto.request.CategoryUpdateRequest;
import com.ey.dto.response.CategoryResponse;

public class CategoryMapper {
 public static Category toEntity(CategoryCreateRequest r) {
     Category c = new Category();
     c.setName(r.getName());
     c.setDescription(r.getDescription());
     return c;
 }
 public static void update(Category c, CategoryUpdateRequest r) {
     if (r.getName() != null) c.setName(r.getName());
     if (r.getDescription() != null) c.setDescription(r.getDescription());
 }
 public static CategoryResponse toResponse(Category c) {
     CategoryResponse cr = new CategoryResponse();
     cr.setId(c.getId());
     cr.setName(c.getName());
     cr.setDescription(c.getDescription());
     return cr;
 }
}
