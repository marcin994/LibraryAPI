package com.library.libraryapi.DAO;

import com.library.libraryapi.Model.BookItem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookItemRepository extends MongoRepository<BookItem, String> {

    BookItem findByIdAndAuthorAndTitle(String id,String author, String title);
}
