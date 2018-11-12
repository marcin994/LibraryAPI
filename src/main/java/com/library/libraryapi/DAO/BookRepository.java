package com.library.libraryapi.DAO;

import com.library.libraryapi.Model.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository <Book, Long>{
    Book findByIsbn(String isbn);
    Book findByAuthorAndTitle(String author, String title);
}
