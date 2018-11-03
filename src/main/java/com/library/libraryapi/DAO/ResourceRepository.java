package com.library.libraryapi.DAO;

import com.library.libraryapi.Model.Resource;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ResourceRepository extends CrudRepository<Resource, Long> {
    List<Resource> findAllByLanguageCode(String languageCode);
    List<Resource> findAllByName(String name);
    Resource findByNameAndLanguageCode(String name, String language);
    Long countByName(String name);
}
