package com.library.libraryapi.DAO;

import com.library.libraryapi.Model.Resources;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ResourceRepository extends CrudRepository<Resources, Long> {
    List<Resources> findAllByLanguageCode(String languageCode);
    List<Resources> findAllByName(String name);
    Resources findByNameAndLanguageCode(String name, String language);
}
