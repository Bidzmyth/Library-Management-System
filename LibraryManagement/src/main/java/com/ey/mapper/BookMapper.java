package com.ey.mapper;

import com.ey.domain.entity.Author;
import com.ey.domain.entity.Book;
import com.ey.domain.entity.Category;
import com.ey.dto.request.BookCreateRequest;
import com.ey.dto.request.BookUpdateRequest;
import com.ey.dto.response.BookResponse;

public class BookMapper {
 public static Book toEntity(BookCreateRequest r, Author a, Category c) {
     Book b = new Book();
     b.setTitle(r.getTitle());
     b.setIsbn(r.getIsbn());
     b.setAuthor(a);
     b.setCategory(c);
     b.setTotalCopies(r.getTotalCopies());
     b.setAvailableCopies(r.getTotalCopies());
     return b;
 }
 public static void update(Book b, BookUpdateRequest r, Author a, Category c) {
     if (r.getTitle() != null) b.setTitle(r.getTitle());
     if (r.getIsbn() != null) b.setIsbn(r.getIsbn());
     if (a != null) b.setAuthor(a);
     if (c != null) b.setCategory(c);
     if (r.getTotalCopies() != null) b.setTotalCopies(r.getTotalCopies());
     if (r.getAvailableCopies() != null) b.setAvailableCopies(r.getAvailableCopies());
 }
 public static BookResponse toResponse(Book b) {
     BookResponse br = new BookResponse();
     br.setId(b.getId());
     br.setTitle(b.getTitle());
     br.setIsbn(b.getIsbn());
     br.setAuthorId(b.getAuthor().getId());
     br.setAuthorName(b.getAuthor().getFullName());
     br.setCategoryId(b.getCategory().getId());
     br.setCategoryName(b.getCategory().getName());
     br.setTotalCopies(b.getTotalCopies());
     br.setAvailableCopies(b.getAvailableCopies());
     return br;
 }
}

