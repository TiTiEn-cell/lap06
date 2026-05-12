package com.example.librarymanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "books")
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String author;

    private Double price;

    @Column(name = "published_year")
    private Integer publishedYear;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}