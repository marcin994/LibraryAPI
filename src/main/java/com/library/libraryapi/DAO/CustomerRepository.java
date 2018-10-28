package com.library.libraryapi.DAO;

import com.library.libraryapi.Model.CustomerModel;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<CustomerModel, Long> {
    CustomerModel findByLoginAndPassword(String login, String password);
    CustomerModel findByLogin(String login);
}
