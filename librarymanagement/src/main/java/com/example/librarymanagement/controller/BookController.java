package com.example.librarymanagement.controller;

import com.example.librarymanagement.entity.Book;
import com.example.librarymanagement.repository.BookRepository;
import com.example.librarymanagement.repository.CategoryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    public BookController(BookRepository bookRepository,
                          CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public String listBooks(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            Model model
    ) {

        if (keyword != null && !keyword.isEmpty()) {
            model.addAttribute("books",
                    bookRepository
                            .findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(
                                    keyword,
                                    keyword
                            ));
        }
        else if (category != null && !category.isEmpty()) {
            model.addAttribute("books",
                    bookRepository.findByCategory_Name(category));
        }
        else {
            model.addAttribute("books", bookRepository.findAll());
        }

        model.addAttribute("categories", categoryRepository.findAll());

        return "books";
    }

    @GetMapping("/add")
    public String addBookForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("categories", categoryRepository.findAll());

        return "book-form";
    }

    @PostMapping("/save")
    public String saveBook(@ModelAttribute Book book) {
        bookRepository.save(book);

        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String editBook(
            @PathVariable Long id,
            Model model
    ) {
        Book book = bookRepository.findById(id).orElseThrow();

        model.addAttribute("book", book);
        model.addAttribute("categories", categoryRepository.findAll());

        return "book-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {

        bookRepository.deleteById(id);

        return "redirect:/books";
    }
}