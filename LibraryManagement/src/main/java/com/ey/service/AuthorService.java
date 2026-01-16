package com.ey.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ey.domain.entity.Author;
import com.ey.dto.request.AuthorCreateRequest;
import com.ey.dto.request.AuthorUpdateRequest;
import com.ey.exception.NotFoundException;
import com.ey.mapper.AuthorMapper;
import com.ey.repository.AuthorRepository;

@Service
public class AuthorService {
 private static final Logger log = LoggerFactory.getLogger(AuthorService.class);
 private final AuthorRepository repo;

 public AuthorService(AuthorRepository repo) { this.repo = repo; }

 public Author create(AuthorCreateRequest r) {
     log.info("Creating author: {}", r.getFullName());
     return repo.save(AuthorMapper.toEntity(r));
 }

 public Author get(Long id) {
     return repo.findById(id).orElseThrow(() -> new NotFoundException("Author not found: " + id));
 }

 public List<Author> list() { return repo.findAll(); }

 public Author update(Long id, AuthorUpdateRequest r) {
     Author a = get(id);
     AuthorMapper.update(a, r);
     log.info("Updating author id={}", id);
     return repo.save(a);
 }

 public void delete(Long id) {
     log.warn("Deleting author id={}", id);
     repo.delete(get(id));
 }

 public List<Author> searchByName(String q) { return repo.findByFullNameContainingIgnoreCase(q); }
}

