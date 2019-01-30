package com.library.libraryapi.DAO;

import com.library.libraryapi.Model.Dictionary;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DictionaryRepository extends MongoRepository<Dictionary, String> {
    List<Dictionary> findAll();
    Dictionary findByCode(String code);
}
