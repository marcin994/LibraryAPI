package com.library.libraryapi.DAO;

import com.library.libraryapi.Model.Address;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface AddressRepository extends MongoRepository<Address, String> {

}
