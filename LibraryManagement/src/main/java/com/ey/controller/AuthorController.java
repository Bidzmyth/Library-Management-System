package com.ey.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ey.dto.request.AuthorCreateRequest;
import com.ey.dto.request.AuthorUpdateRequest;
import com.ey.dto.response.AuthorResponse;
import com.ey.mapper.AuthorMapper;
import com.ey.service.AuthorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {
 private static final Logger log = LoggerFactory.getLogger(AuthorController.class);
 private final AuthorService service;

 public AuthorController(AuthorService service) { this.service = service; }

 @PostMapping
 public AuthorResponse create(@Valid @RequestBody AuthorCreateRequest r) {
     log.debug("POST /authors");
     return AuthorMapper.toResponse(service.create(r));
 }

 @GetMapping
 public List<AuthorResponse> list() {
     return service.list().stream().map(AuthorMapper::toResponse).toList();
 }

 @GetMapping("/{id}")
 public AuthorResponse get(@PathVariable Long id) {
     return AuthorMapper.toResponse(service.get(id));
 }

 @PutMapping("/{id}")
 public AuthorResponse update(@PathVariable Long id, @Valid @RequestBody AuthorUpdateRequest r) {
     return AuthorMapper.toResponse(service.update(id, r));
 }

 @DeleteMapping("/{id}")
 public void delete(@PathVariable Long id) { service.delete(id); }

 @GetMapping("/search")
 public List<AuthorResponse> search(@RequestParam String q) {
     return service.searchByName(q).stream().map(AuthorMapper::toResponse).toList();
 }

 @GetMapping("/{id}/books")
 public List<Object> authorBooks(@PathVariable Long id) {
     // Keep thin — real data comes from BookController endpoint /books/by-author/{authorId}
     return List.of(Map.of("hint", "Use /api/v1/books/by-author/" + id));
 }
}

