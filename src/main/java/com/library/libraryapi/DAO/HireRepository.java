package com.library.libraryapi.DAO;

import com.library.libraryapi.Model.Hire;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HireRepository extends MongoRepository<Hire, String> {

}
