package com.library.libraryapi.Controller;

import com.google.gson.Gson;
import com.library.libraryapi.DAO.BookItemRepository;
import com.library.libraryapi.DAO.BookRepository;
import com.library.libraryapi.DAO.CustomerRepository;
import com.library.libraryapi.Model.Book;
import com.library.libraryapi.Model.BookItem;
import com.library.libraryapi.Model.Customer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    //TODO: remove, edit,

    private final BookRepository bookRepository;
    private final CustomerRepository customerRepository;
    private final BookItemRepository bookItemRepository;
    private HttpHeaders headers;
    private Gson gson;

    public BookController(BookRepository bookRepository,
                          CustomerRepository customerRepository,
                          BookItemRepository bookItemRepository) {
        this.bookRepository = bookRepository;
        this.customerRepository = customerRepository;
        this.bookItemRepository = bookItemRepository;
        this.headers = new HttpHeaders();
        this.headers.setContentType(MediaType.APPLICATION_JSON);
        this.gson = new Gson();
    }

    @RequestMapping(value = "/{me}/addBook", method = RequestMethod.POST)
    public ResponseEntity<String> addBook(@PathVariable(value = "me") String me,
                                          @RequestBody(required = false) Book book) {

        if (!userCouldPerformAction(me)) {
            return new ResponseEntity<>(null, headers, HttpStatus.valueOf(" Your account doesnt exist or you arent librarian"));
        }

        if (book == null) {
            return new ResponseEntity<>(null, headers, HttpStatus.valueOf(" Incorect data"));
        }

        if (book.getTitle() == null || book.getTitle().isEmpty()) {
            return new ResponseEntity<>(null, headers, HttpStatus.valueOf(" Title is required"));
        }

        if (book.getAuthor() == null || book.getAuthor().isEmpty()) {
               return new ResponseEntity<>(null, headers, HttpStatus.valueOf(" Author is required"));
        }

        if (book.getCategory() == null || book.getCategory().isEmpty()) {
            return new ResponseEntity<>(null, headers, HttpStatus.valueOf(" Category is required"));
        }

        Book existedBook = bookRepository.findByAuthorAndTitle(book.getAuthor(), book.getTitle());

        if (existedBook != null) {
            book = existedBook;
        }

        if (book.isDeleted()) {
            book.setDeleted(false);
        }

        if (book.getItems() == null) {
            book.setItems(new ArrayList<>());
        }

        bookRepository.save(book);

        return new ResponseEntity<>(gson.toJson(book), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/{me}/addBookItem", method = RequestMethod.POST)
    public ResponseEntity<String> addBookIItem(@PathVariable(value = "me") String me,
                                               @RequestBody BookItem bookItem) {

        if (!userCouldPerformAction(me)) {
            return new ResponseEntity<>(null, headers, HttpStatus.valueOf(" Your account doesnt exist or you arent librarian"));
        }

        if (bookItem == null ) {
            return new ResponseEntity<>(null, headers, HttpStatus.valueOf(" Incorect data"));
        }

        if (bookItem.getTitle() == null || bookItem.getTitle().isEmpty()) {
            return new ResponseEntity<>(null, headers, HttpStatus.valueOf(" Title for item is required"));
        }

         if (bookItem.getAuthor() == null || bookItem.getAuthor().isEmpty()) {
             return new ResponseEntity<>(null, headers, HttpStatus.valueOf(" Author for item is required"));
         }

        if (bookItem.getIsbn() == null || bookItem.getIsbn().isEmpty()) {
            return new ResponseEntity<>(null, headers, HttpStatus.valueOf(" Isbn is required"));
        }

        if (bookItem.getPublishing() == null || bookItem.getPublishing().isEmpty()) {
            return new ResponseEntity<>(null, headers, HttpStatus.valueOf(" Publishing is required"));
        }

        bookItem.setAvailable(true);

        Book book = bookRepository.findByAuthorAndTitle(bookItem.getAuthor(), bookItem.getTitle());

        if (book == null) {
            return new ResponseEntity<>(null, headers, HttpStatus.valueOf(" Book doesny exist yet"));
        }

        book.addBookItem(bookItem);
        bookItem.setBook(book);

        bookItemRepository.save(bookItem);
        bookRepository.save(book);

        book = bookRepository.findByAuthorAndTitle(book.getAuthor(), book.getTitle());

        return new ResponseEntity<>(gson.toJson(book), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity<String> searchBook(@RequestParam(name = "text", required = false) String text) {

        if (text == null || text.isEmpty()) {
            return new ResponseEntity<>(null, headers, HttpStatus.valueOf(" Searching text cant be empty"));
        }

        List<Book> books = bookRepository.findAllByTitleContainingOrAuthorContainingOrDescriptionContaining(text, text, text);

        return new ResponseEntity<>(gson.toJson(books), headers, HttpStatus.OK);
    }

    public boolean userCouldPerformAction(String id) {
        if (id == null || id.isEmpty()) {
            return false;
        }

        Optional<Customer> userOptional = customerRepository.findById(id);
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
