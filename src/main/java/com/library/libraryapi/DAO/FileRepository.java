package com.library.libraryapi.DAO;

import com.library.libraryapi.Model.File;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FileRepository extends MongoRepository<File, String> {
}
