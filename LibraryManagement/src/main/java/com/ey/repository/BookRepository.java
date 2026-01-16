package com.ey.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ey.domain.entity.Author;
import com.ey.domain.entity.Book;
import com.ey.domain.entity.Category;

public interface BookRepository extends JpaRepository<Book, Long> {
 List<Book> findByTitleContainingIgnoreCase(String q);
 List<Book> findByAuthor(Author author);
 List<Book> findByCategory(Category category);
 List<Book> findByAvailableCopiesGreaterThan(int zero);
 Optional<Book> findByIsbn(String isbn);
}

