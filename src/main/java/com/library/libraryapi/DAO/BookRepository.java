package com.library.libraryapi.DAO;

import com.library.libraryapi.Model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String> {
    Book findByAuthorAndTitle(String author, String title);
    List<Book> findAllByTitleContainingOrAuthorContainingOrDescriptionContaining(String title, String author, String description);
}
