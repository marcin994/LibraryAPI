package com.library.libraryapi.Controller;

import com.google.gson.Gson;
import com.library.libraryapi.DAO.CustomerRepository;
import com.library.libraryapi.Model.CustomerModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class CustomerController {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<String> login(@RequestParam("login") String login, @RequestParam("password") String password) {

        CustomerModel customer = customerRepository.findByLoginAndPassword(login, password);

        //TODO: fix localrepository method, customer always nil / null;

        if (customer == null) {

            customer = new CustomerModel();
            customer.setLastName("lastName");
            customer.setFirstName("FirstName");

            if (customerRepository.findByLogin(login) != null) {
                return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.valueOf("Invalid password"));
            }

            return new ResponseEntity<>( null, new HttpHeaders(), HttpStatus.NOT_FOUND);
        }

        Gson gson = new Gson();
        String result = gson.toJson(customer);

        return new ResponseEntity<>(result, new HttpHeaders(), HttpStatus.OK);
    }
}
