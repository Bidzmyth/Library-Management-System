package com.ey.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.ey.domain.entity.Author;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
 List<Author> findByFullNameContainingIgnoreCase(String q);
}

