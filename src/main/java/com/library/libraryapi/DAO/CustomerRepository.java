package com.library.libraryapi.DAO;

import com.library.libraryapi.Model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer, String> {
    Customer findByLoginAndPassword(String login, String password);
    Customer findByLogin(String login);
}
