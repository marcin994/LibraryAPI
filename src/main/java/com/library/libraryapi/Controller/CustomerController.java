package com.library.libraryapi.Controller;

import com.library.libraryapi.DAO.CustomerRepository;
import com.library.libraryapi.Model.CustomerModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<CustomerModel> login(@RequestParam("login") String login, @RequestParam("password") String password) {
        CustomerModel customer = customerRepository.findByLoginAndPassword(login, password);

        //TODO: fix localrepository method, customer always nil / null

        if (customer == null) {
            return new ResponseEntity<>( null, new HttpHeaders(), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(customer, new HttpHeaders(), HttpStatus.OK);
        }
    }

}
