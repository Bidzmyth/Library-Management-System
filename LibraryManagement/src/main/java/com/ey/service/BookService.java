package com.ey.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ey.domain.entity.Author;
import com.ey.domain.entity.Book;
import com.ey.domain.entity.Category;
import com.ey.dto.request.BookCreateRequest;
import com.ey.dto.request.BookUpdateRequest;
import com.ey.exception.BadRequestException;
import com.ey.exception.NotFoundException;
import com.ey.mapper.BookMapper;
import com.ey.repository.AuthorRepository;
import com.ey.repository.BookRepository;
import com.ey.repository.CategoryRepository;

@Service
public class BookService {
 private static final Logger log = LoggerFactory.getLogger(BookService.class);
 private final BookRepository repo;
 private final AuthorRepository authorRepo;
 private final CategoryRepository categoryRepo;

 public BookService(BookRepository repo, AuthorRepository authorRepo, CategoryRepository categoryRepo) {
     this.repo = repo; this.authorRepo = authorRepo; this.categoryRepo = categoryRepo;
 }

 public Book create(BookCreateRequest r) {
     if (repo.findByIsbn(r.getIsbn()).isPresent()) {
         throw new BadRequestException("ISBN already registered: " + r.getIsbn());
     }
     Author a = authorRepo.findById(r.getAuthorId())
             .orElseThrow(() -> new NotFoundException("Author not found: " + r.getAuthorId()));
     Category c = categoryRepo.findById(r.getCategoryId())
             .orElseThrow(() -> new NotFoundException("Category not found: " + r.getCategoryId()));
     Book b = BookMapper.toEntity(r, a, c);
     log.info("Creating book: {} (ISBN {})", r.getTitle(), r.getIsbn());
     return repo.save(b);
 }

 public Book get(Long id) {
     return repo.findById(id).orElseThrow(() -> new NotFoundException("Book not found: " + id));
 }

 public List<Book> list() { return repo.findAll(); }

 public List<Book> search(String q) { return repo.findByTitleContainingIgnoreCase(q); }

 public List<Book> byAuthor(Long authorId) {
     Author a = authorRepo.findById(authorId).orElseThrow(() -> new NotFoundException("Author not found: " + authorId));
     return repo.findByAuthor(a);
 }

 public List<Book> byCategory(Long categoryId) {
     Category c = categoryRepo.findById(categoryId).orElseThrow(() -> new NotFoundException("Category not found: " + categoryId));
     return repo.findByCategory(c);
 }

 public List<Book> available() { return repo.findByAvailableCopiesGreaterThan(0); }

 public Book byIsbn(String isbn) { return repo.findByIsbn(isbn).orElseThrow(() -> new NotFoundException("Book not found by ISBN: " + isbn)); }

 public Book update(Long id, BookUpdateRequest r) {
     Book b = get(id);
     Author a = null;
     Category c = null;
     if (r.getAuthorId() != null) {
         a = authorRepo.findById(r.getAuthorId()).orElseThrow(() -> new NotFoundException("Author not found: " + r.getAuthorId()));
     }
     if (r.getCategoryId() != null) {
         c = categoryRepo.findById(r.getCategoryId()).orElseThrow(() -> new NotFoundException("Category not found: " + r.getCategoryId()));
     }
     if (r.getIsbn() != null && !r.getIsbn().equals(b.getIsbn()) && repo.findByIsbn(r.getIsbn()).isPresent()) {
         throw new BadRequestException("ISBN already registered: " + r.getIsbn());
     }
     BookMapper.update(b, r, a, c);
     log.info("Updating book id={}", id);
     return repo.save(b);
 }

 public Book updateQuantity(Long id, int available) {
     Book b = get(id);
     if (available < 0 || available > b.getTotalCopies())
         throw new BadRequestException("Available copies must be between 0 and totalCopies");
     b.setAvailableCopies(available);
     log.info("Updating available copies for book id={} -> {}", id, available);
     return repo.save(b);
 }

 public void delete(Long id) {
     log.warn("Deleting book id={}", id);
     repo.delete(get(id));
 }
}

