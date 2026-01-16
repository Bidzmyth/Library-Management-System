package com.ey.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ey.dto.request.CategoryCreateRequest;
import com.ey.dto.request.CategoryUpdateRequest;
import com.ey.dto.response.CategoryResponse;
import com.ey.mapper.CategoryMapper;
import com.ey.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
 private final CategoryService service;

 public CategoryController(CategoryService service) { this.service = service; }

 @PostMapping
 public CategoryResponse create(@Valid @RequestBody CategoryCreateRequest r) {
     return CategoryMapper.toResponse(service.create(r));
 }

 @GetMapping
 public List<CategoryResponse> list() { return service.list().stream().map(CategoryMapper::toResponse).toList(); }

 @GetMapping("/{id}")
 public CategoryResponse get(@PathVariable Long id) { return CategoryMapper.toResponse(service.get(id)); }

 @PutMapping("/{id}")
 public CategoryResponse update(@PathVariable Long id, @Valid @RequestBody CategoryUpdateRequest r) {
     return CategoryMapper.toResponse(service.update(id, r));
 }

 @DeleteMapping("/{id}")
 public void delete(@PathVariable Long id) { service.delete(id); }

 @GetMapping("/{id}/books")
 public List<Object> categoryBooks(@PathVariable Long id) {
     return List.of(Map.of("hint", "Use /api/v1/books/by-category/" + id));
 }
}

