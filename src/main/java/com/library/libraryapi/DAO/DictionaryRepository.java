package com.library.libraryapi.DAO;

import com.library.libraryapi.Model.Dictionary;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DictionaryRepository extends CrudRepository<Dictionary, Long> {
    List<Dictionary> findAll();
    Dictionary findByCode(String code);
}
