package com.library.libraryapi.DAO;

import com.library.libraryapi.Model.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository <Book, Long>{
    Book findByAuthorAndTitle(String author, String title);
    List<Book> findAllByTitleContainingOrAuthorContainingOrDescriptionContaining(String title, String author, String description);
}
