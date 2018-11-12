package com.library.libraryapi.Controller;

import com.google.gson.Gson;
import com.library.libraryapi.DAO.BookRepository;
import com.library.libraryapi.DAO.CustomerRepository;
import com.library.libraryapi.Model.Book;
import com.library.libraryapi.Model.Customer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class BookController {

    //TODO: add, remove, edit, search,

    private final BookRepository bookRepository;
    private final CustomerRepository customerRepository;
    private HttpHeaders headers;
    private Gson gson;

    public BookController(BookRepository bookRepository,
                          CustomerRepository customerRepository) {
        this.bookRepository = bookRepository;
        this.customerRepository = customerRepository;
        this.headers = new HttpHeaders();
        this.headers.setContentType(MediaType.APPLICATION_JSON);
        this.gson = new Gson();
    }

    @RequestMapping(value = "/{me}/addBook", method = RequestMethod.POST)
    public ResponseEntity<String> addBook(@PathVariable(value = "me") String me, @RequestBody Book book) {

        if (!userCouldPerformAction(me)) {
            return new ResponseEntity<>(null, headers, HttpStatus.valueOf(" Your account doesnt exist or you arent librarian"));
        }

        if (book.getIsbn() == null || book.getIsbn().isEmpty()) {
            return new ResponseEntity<>(null, headers, HttpStatus.valueOf(" Isbn is required"));
        }

        if (book.getTitle() == null || book.getTitle().isEmpty()) {
            return new ResponseEntity<>(null, headers, HttpStatus.valueOf(" Title is required"));
        }

        if (book.getAuthor() == null || book.getAuthor().isEmpty()) {
            return new ResponseEntity<>(null, headers, HttpStatus.valueOf(" Author is required"));
        }

        if (book.getPublishing() == null || book.getPublishing().isEmpty()) {
            return new ResponseEntity<>(null, headers, HttpStatus.valueOf(" Publishing is required"));
        }

        if (book.getCategory() == null || book.getCategory().isEmpty()) {
            return new ResponseEntity<>(null, headers, HttpStatus.valueOf(" Category is required"));
        }

        Book existedBook = bookRepository.findByAuthorAndTitle(book.getAuthor(), book.getTitle());

        if (existedBook != null) {
            existedBook.incrementItemNumber();
            book = existedBook;
        }

        if (book.getItemNumber() == 0) {
            book.incrementItemNumber();
        }

        bookRepository.save(book);
        String savedBook = gson.toJson(bookRepository.findByIsbn(book.getIsbn()));

        return new ResponseEntity<>(savedBook, headers, HttpStatus.OK);
    }

    public boolean userCouldPerformAction(String id) {
        if (id == null || id.isEmpty()) {
            return false;
        }

        Optional<Customer> userOptional = customerRepository.findById(Long.parseLong(id));
        Customer user = new Customer();
        user.setAccountType(userOptional.map(Customer::getAccountType).orElse(null));
        user.setDeleted(userOptional.map(Customer::isDeleted).orElse(false));

        if (user.getAccountType() == null || !user.getAccountType().equals("LIBRARIAN")) {
            return false;
        }

        if (user.isDeleted() == true) {
            return false;
        }

        return true;
    }
}
