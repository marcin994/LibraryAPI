package com.library.libraryapi.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.library.libraryapi.DAO.AddressRepository;
import com.library.libraryapi.DAO.CustomerRepository;
import com.library.libraryapi.DAO.DictionaryItemRepository;
import com.library.libraryapi.Model.Customer;
import com.library.libraryapi.Util.Adapter.ListAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CustomerController {

    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;
    private final DictionaryItemRepository dictionaryItemRepository;
    private HttpHeaders headers;
    private Gson gson;

    @Autowired
    public CustomerController(CustomerRepository customerRepository,
                              AddressRepository addressRepository,
                              DictionaryItemRepository dictionaryItemRepository) {
        this.headers = new HttpHeaders();
        this.headers.setContentType(MediaType.APPLICATION_JSON);
        this.gson = new GsonBuilder().
                registerTypeHierarchyAdapter(List.class, new ListAdapter()).
                create();
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
        this.dictionaryItemRepository = dictionaryItemRepository;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<String> login(@RequestBody Customer user) {

        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            return new ResponseEntity<>(null, headers, HttpStatus.valueOf(" Login cant be empty"));
        }

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            return new ResponseEntity<>(null, headers, HttpStatus.valueOf(" Password cant be empty"));
        }

        Customer customer = customerRepository.findByLoginAndPassword(user.getLogin(), user.getPassword());

        if (customer == null) {

            if (customerRepository.findByLogin(user.getLogin()) != null) {
                return new ResponseEntity<>(null, headers, HttpStatus.valueOf(" Invalid password"));
            }

            return new ResponseEntity<>( null, headers, HttpStatus.NOT_FOUND);

        } else if (customer.isDeleted() == true) {
            return new ResponseEntity<>(null, headers, HttpStatus.valueOf(" This account was deleted"));
        }

        customer.setHireBooks(null);
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

        if (customerModel.getAccountType() == null || customerModel.getAccountType().isEmpty()) {
            customerModel.setAccountType("USER");
        } else if (dictionaryItemRepository.findByDomainAndCode("ROLE", customerModel.getAccountType()) == null){
            return new ResponseEntity<>(null, headers, HttpStatus.valueOf(" Incorrect role type"));
        }

        if (customerModel.getAddress() != null) {
            addressRepository.save(customerModel.getAddress());
        }

        customerRepository.save(customerModel);
        Customer cM = customerRepository.findByLogin(customerModel.getLogin());

        if (cM == null) {
            return new ResponseEntity<>(null, headers, HttpStatus.valueOf(" Server error"));
        }

        customerModel.setPassword(null);

        return new ResponseEntity<>(gson.toJson(customerModel), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/{meId}/delete", method = RequestMethod.POST)
    public ResponseEntity<String> deleteAccount(@RequestParam(value = "userid") String userid, @PathVariable(value = "meId") String meId) {

        if (meId == null || meId.isEmpty()) {
            return new ResponseEntity<>(null, headers, HttpStatus.valueOf(" Your id is required"));
        }

        if (userid == null || userid.isEmpty()) {
            return new ResponseEntity<>(null, headers, HttpStatus.valueOf(" Id account to remove is required"));
        }

        Optional<Customer> userOptional = customerRepository.findById(Long.parseLong(meId));
        Customer user = new Customer();
        user.setAccountType(userOptional.map(Customer::getAccountType).orElse(null));
        user.setDeleted(userOptional.map(Customer::isDeleted).orElse(false));

        if (user.getAccountType() == null || !user.getAccountType().equals("ADMIN")) {
            return new ResponseEntity<>(null, headers, HttpStatus.valueOf(" Only admin could delete users account"));
        }

        if (user.isDeleted() == true) {
            return new ResponseEntity<>(null, headers, HttpStatus.valueOf(" This account was deleted"));
        }

        userOptional = customerRepository.findById(Long.parseLong(userid));
        Customer userToDelete = new Customer();
        userToDelete.setLogin(userOptional.map(Customer::getLogin).orElse(null));

        if (userToDelete.getLogin() == null ) {
            return new ResponseEntity<>(null, headers, HttpStatus.valueOf(" User with selected id doesnt exist"));
        }

        userToDelete = customerRepository.findByLogin(userToDelete.getLogin());
        userToDelete.setDeleted(true);
        customerRepository.save(userToDelete);

        userToDelete = customerRepository.findByLogin(userToDelete.getLogin());

        return new ResponseEntity<>(gson.toJson(userToDelete), headers, HttpStatus.OK);
    }
}
