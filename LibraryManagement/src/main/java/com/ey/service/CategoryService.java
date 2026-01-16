package com.ey.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ey.domain.entity.Category;
import com.ey.dto.request.CategoryCreateRequest;
import com.ey.dto.request.CategoryUpdateRequest;
import com.ey.exception.BadRequestException;
import com.ey.exception.NotFoundException;
import com.ey.mapper.CategoryMapper;
import com.ey.repository.CategoryRepository;

@Service
public class CategoryService {
 private static final Logger log = LoggerFactory.getLogger(CategoryService.class);
 private final CategoryRepository repo;

 public CategoryService(CategoryRepository repo) { this.repo = repo; }

 public Category create(CategoryCreateRequest r) {
     repo.findByNameIgnoreCase(r.getName()).ifPresent(c -> {
         throw new BadRequestException("Category already exists with name: " + r.getName());
     });
     Category c = CategoryMapper.toEntity(r);
     log.info("Creating category: {}", r.getName());
     return repo.save(c);
 }

 public Category get(Long id) {
     return repo.findById(id).orElseThrow(() -> new NotFoundException("Category not found: " + id));
 }

 public List<Category> list() { return repo.findAll(); }

 public Category update(Long id, CategoryUpdateRequest r) {
     Category c = get(id);
     CategoryMapper.update(c, r);
     log.info("Updating category id={}", id);
     return repo.save(c);
 }

 public void delete(Long id) {
     log.warn("Deleting category id={}", id);
     repo.delete(get(id));
 }
}


