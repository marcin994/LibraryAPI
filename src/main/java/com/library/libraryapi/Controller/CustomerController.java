package com.library.libraryapi.Controller;

import com.google.gson.Gson;
import com.library.libraryapi.DAO.AddressRepository;
import com.library.libraryapi.DAO.CustomerRepository;
import com.library.libraryapi.Model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class CustomerController {

    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;
    private HttpHeaders headers;

    @Autowired
    public CustomerController(CustomerRepository customerRepository, AddressRepository addressRepository) {
        this.headers = new HttpHeaders();
        this.headers.setContentType(MediaType.APPLICATION_JSON);
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<String> login(@RequestParam("login") String login, @RequestParam("password") String password) {

        Customer customer = customerRepository.findByLoginAndPassword(login, password);

        if (customer == null) {

            if (customerRepository.findByLogin(login) != null) {
                return new ResponseEntity<>(null, headers, HttpStatus.valueOf(" Invalid password"));
            }

            return new ResponseEntity<>( null, headers, HttpStatus.NOT_FOUND);
        }

        Gson gson = new Gson();
        String result = gson.toJson(customer);

        return new ResponseEntity<>(result, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<String> register(@RequestBody Customer customerModel) {

        if (customerModel == null) {
            return new ResponseEntity<>(null, headers, HttpStatus.valueOf(" Incorrect data"));
        }

        if (customerModel.getLogin() == null || customerModel.getLogin().isEmpty()) {
            return new ResponseEntity<>(null, headers, HttpStatus.valueOf(" Login cant be empty"));
        }

        if (customerModel.getPassword() == null || customerModel.getPassword().isEmpty()) {
            return new ResponseEntity<>(null, headers, HttpStatus.valueOf(" Password cant be empty"));
        }

        if (customerRepository.findByLogin(customerModel.getLogin()) != null) {
            return new ResponseEntity<>(null, headers, HttpStatus.valueOf(" Login is already used"));
        }

        addressRepository.save(customerModel.getAddress());
        customerRepository.save(customerModel);
        Customer cM = customerRepository.findByLogin(customerModel.getLogin());

        if (cM == null) {
            return new ResponseEntity<>(null, headers, HttpStatus.valueOf(" Server error"));
        }

        customerModel.setPassword(null);
        Gson gson = new Gson();

        return new ResponseEntity<>(gson.toJson(customerModel), headers, HttpStatus.OK);
    }
}
