package com.ey.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ey.domain.entity.Author;
import com.ey.domain.entity.Book;
import com.ey.domain.entity.Category;
import com.ey.dto.request.BookCreateRequest;
import com.ey.dto.request.BookUpdateRequest;
import com.ey.exception.BadRequestException;
import com.ey.exception.NotFoundException;
import com.ey.repository.AuthorRepository;
import com.ey.repository.BookRepository;
import com.ey.repository.CategoryRepository;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private BookService bookService;

    Author author;
    Category category;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        author = new Author();
        author.setId(1L);
        author.setFullName("JK Rowling");

        category = new Category();
        category.setId(1L);
        category.setName("Fantasy");
    }

    @Test
    void createBook_success() {
        BookCreateRequest req = new BookCreateRequest();
        req.setTitle("HP1");
        req.setIsbn("123");
        req.setAuthorId(1L);
        req.setCategoryId(1L);
        req.setTotalCopies(5);

        when(bookRepository.findByIsbn("123")).thenReturn(Optional.empty());
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(bookRepository.save(any())).thenAnswer(i -> {
            Book b = i.getArgument(0);
            b.setId(10L);
            return b;
        });

        Book result = bookService.create(req);

        assertEquals(10L, result.getId());
        assertEquals(5, result.getAvailableCopies());
        verify(bookRepository, times(1)).save(any());
    }

    @Test
    void createBook_duplicateIsbn_throwsException() {
        BookCreateRequest req = new BookCreateRequest();
        req.setIsbn("123");

        when(bookRepository.findByIsbn("123")).thenReturn(Optional.of(new Book()));

        assertThrows(BadRequestException.class, () -> bookService.create(req));
    }

    @Test
    void updateBook_notFound_throwsException() {
        when(bookRepository.findById(100L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> bookService.update(100L, new BookUpdateRequest()));
    }
}

