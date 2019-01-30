package com.library.libraryapi.DAO;

import com.library.libraryapi.Model.Resource;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ResourceRepository extends MongoRepository<Resource, String> {
    List<Resource> findAllByLanguageCode(String languageCode);
    List<Resource> findAllByName(String name);
    Resource findByNameAndLanguageCode(String name, String language);
    Long countByName(String name);
}
