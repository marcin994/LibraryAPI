package com.library.libraryapi.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.library.libraryapi.DAO.BookItemRepository;
import com.library.libraryapi.DAO.CustomerRepository;
import com.library.libraryapi.DAO.DictionaryItemRepository;
import com.library.libraryapi.DAO.HireRepository;
import com.library.libraryapi.Model.*;
import com.library.libraryapi.Util.Adapter.ListAdapter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/action")
public class HireController {

    private final CustomerRepository customerRepository;
    private final BookItemRepository bookItemRepository;
    private final HireRepository hireRepository;
    private final DictionaryItemRepository dictionaryItemRepository;

    private HttpHeaders headers;
    private Gson gson;

    public HireController(CustomerRepository customerRepository,
                          BookItemRepository bookItemRepository,
                          HireRepository hireRepository,
                          DictionaryItemRepository dictionaryItemRepository) {

        this.bookItemRepository = bookItemRepository;
        this.customerRepository = customerRepository;
        this.hireRepository = hireRepository;
        this.dictionaryItemRepository = dictionaryItemRepository;
        this.headers = new HttpHeaders();
        this.headers.setContentType(MediaType.APPLICATION_JSON);
        this.gson = new Gson();
    }

    @RequestMapping(value = "/{me}/hire", method = RequestMethod.POST)
    public ResponseEntity<String> hireAction(@PathVariable(value = "me") String me,
                                             @RequestParam(name = "book") String book) {

        //Customer validation
        if (me == null || me.isEmpty()) {
            return new ResponseEntity<>(null, headers, HttpStatus.valueOf(" user id cant be empty"));
        }

        Optional<Customer> userOptional = customerRepository.findById(Long.parseLong(me));

        Customer user = new Customer();
        user.setLogin(userOptional.map(Customer::getLogin).orElse(null));

        user = customerRepository.findByLogin(user.getLogin());

        if (user == null) {
            return new ResponseEntity<>(null, headers, HttpStatus.valueOf(" account doesnt exist"));
        }

        if (user.isDeleted()) {
            return new ResponseEntity<>(null, headers, HttpStatus.valueOf(" account was deleted"));
        }

        //Book validation
        if (book == null || book.isEmpty()) {
            return new ResponseEntity<>(null, headers, HttpStatus.valueOf(" BookId is required"));
        }

        Optional<BookItem> bookItemOptional = bookItemRepository.findById(Long.parseLong(book));

        BookItem bookItem = new BookItem();
        bookItem.setId(bookItemOptional.map(BookItem::getId).orElse(null));
        bookItem.setAuthor(bookItemOptional.map(BookItem::getAuthor).orElse(null));
        bookItem.setTitle(bookItemOptional.map(BookItem::getTitle).orElse(null));

        bookItem = bookItemRepository.findByIdAndAuthorAndTitle(bookItem.getId(), bookItem.getAuthor(), bookItem.getTitle());

        if (bookItem == null) {
            return new ResponseEntity<>(null, headers, HttpStatus.valueOf(" book doesnt exist"));
        }

        if (!bookItem.isAvailable()) {
            return new ResponseEntity<>(null, headers, HttpStatus.valueOf(" book isnt available"));
        }

        Hire hire = new Hire();
        DictionaryItem extension = dictionaryItemRepository.findByCode("EXDF");
        DictionaryItem returnTime = dictionaryItemRepository.findByCode("BRT");

        int maxExtension = extension != null ? Integer.parseInt(extension.getValue()) : 0;
        int bookReturnAfterDays = returnTime != null ? Integer.parseInt(returnTime.getValue()) : 30;

        hire.setAvailableExtension(maxExtension);
        hire.setBook(bookItem);
        hire.setCustomer(user);
        hire.setHireDate(new Date());
        hire.setReturnDate(addDays(bookReturnAfterDays, hire.getHireDate()));
        user.addHire(hire);

        saveHire(hire, user, bookItem);

        return new ResponseEntity<>(null, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/{me}/return", method = RequestMethod.POST)
    public ResponseEntity<String> returnAction(@PathVariable(value = "me") String me,
                                               @RequestParam(name = "book") String bookId) {

        return null;
    }

    @RequestMapping(value = "/{me}/extend", method = RequestMethod.POST)
    public ResponseEntity<String> extendAction(@PathVariable(value = "me") String me,
                                               @RequestParam(name = "book") String bookId) {

        return null;
    }

    private Date addDays(int numberOfDays, Date date){

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, numberOfDays);
        return c.getTime();
    }

    @Transactional
    protected void saveHire(Hire hire, Customer customer, BookItem bookItem) {

        bookItem.setAvailable(false);

        hireRepository.save(hire);
        customerRepository.save(customer);
        bookItemRepository.save(bookItem);
    }
}
