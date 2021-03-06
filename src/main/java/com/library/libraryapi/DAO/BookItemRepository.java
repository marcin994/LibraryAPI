package com.library.libraryapi.DAO;

import com.library.libraryapi.Model.BookItem;
import org.springframework.data.repository.CrudRepository;

public interface BookItemRepository extends CrudRepository<BookItem, Long> {

    BookItem findByIdAndAuthorAndTitle(Long id,String author, String title);
}
