package com.ey.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ey.dto.request.BookCreateRequest;
import com.ey.dto.request.BookUpdateRequest;
import com.ey.dto.response.BookResponse;
import com.ey.mapper.BookMapper;
import com.ey.service.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/books")
public class BookController {
 private static final Logger log = LoggerFactory.getLogger(BookController.class);
 private final BookService service;

 public BookController(BookService service) { this.service = service; }

 @PostMapping
 public BookResponse create(@Valid @RequestBody BookCreateRequest r) {
     return BookMapper.toResponse(service.create(r));
 }

 @GetMapping
 public List<BookResponse> list() { return service.list().stream().map(BookMapper::toResponse).toList(); }

 @GetMapping("/{id}")
 public BookResponse get(@PathVariable Long id) { return BookMapper.toResponse(service.get(id)); }

 @PutMapping("/{id}")
 public BookResponse update(@PathVariable Long id, @Valid @RequestBody BookUpdateRequest r) {
     return BookMapper.toResponse(service.update(id, r));
 }

 @PatchMapping("/{id}/quantity")
 public BookResponse updateQuantity(@PathVariable Long id, @RequestParam int available) {
     return BookMapper.toResponse(service.updateQuantity(id, available));
 }

 @DeleteMapping("/{id}")
 public void delete(@PathVariable Long id) { service.delete(id); }

 @GetMapping("/search")
 public List<BookResponse> search(@RequestParam String q) {
     return service.search(q).stream().map(BookMapper::toResponse).toList();
 }

 @GetMapping("/by-author/{authorId}")
 public List<BookResponse> byAuthor(@PathVariable Long authorId) {
     return service.byAuthor(authorId).stream().map(BookMapper::toResponse).toList();
 }

 @GetMapping("/by-category/{categoryId}")
 public List<BookResponse> byCategory(@PathVariable Long categoryId) {
     return service.byCategory(categoryId).stream().map(BookMapper::toResponse).toList();
 }

 @GetMapping("/available")
 public List<BookResponse> available() {
     return service.available().stream().map(BookMapper::toResponse).toList();
 }

 @GetMapping("/isbn/{isbn}")
 public BookResponse byIsbn(@PathVariable String isbn) {
     return BookMapper.toResponse(service.byIsbn(isbn));
 }
}

