package com.ey.catalog.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "items")
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@Column(nullable = false)
	String title;
	@Column(unique = true)
	String isbn;
	String publisher;
	Integer pubYear;
	String language;
	@Lob
	String summary;
	@Enumerated(EnumType.STRING)
	Format format = Format.PHYSICAL;

	public enum Format {
		PHYSICAL, EBOOK
	}

	@ManyToMany
	@JoinTable(name = "item_authors", joinColumns = @JoinColumn(name = "item_id"), inverseJoinColumns = @JoinColumn(name = "author_id"))
	Set<Author> authors = new HashSet<>();
	@ManyToMany
	@JoinTable(name = "item_genres", joinColumns = @JoinColumn(name = "item_id"), inverseJoinColumns = @JoinColumn(name = "genre_id"))
	Set<Genre> genres = new HashSet<>();
	String createdBy;
	LocalDateTime createdAt;
	String updatedBy;
	LocalDateTime updatedAt;
}
