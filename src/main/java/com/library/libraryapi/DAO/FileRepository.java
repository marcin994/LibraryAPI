package com.library.libraryapi.DAO;

import com.library.libraryapi.Model.File;
import org.springframework.data.repository.CrudRepository;

public interface FileRepository extends CrudRepository<File, Long> {
}
