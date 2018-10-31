package com.library.libraryapi.DAO;

import com.library.libraryapi.Model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Customer findByLoginAndPassword(String login, String password);
    Customer findByLogin(String login);
}
