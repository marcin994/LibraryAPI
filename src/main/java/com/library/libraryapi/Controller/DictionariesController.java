package com.library.libraryapi.Controller;

import com.google.gson.Gson;
import com.library.libraryapi.DAO.DictionaryItemRepository;
import com.library.libraryapi.DAO.DictionaryRepository;
import com.library.libraryapi.Model.Dictionary;
import com.library.libraryapi.Model.DictionaryItem;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DictionariesController {

    private final DictionaryRepository dictionaryRepository;
    private final DictionaryItemRepository dictionaryItemRepository;
    private HttpHeaders headers;
    private Gson gson;

    public DictionariesController(DictionaryRepository dictionaryRepository,
                                  DictionaryItemRepository dictionaryItemRepository) {
        this.dictionaryRepository = dictionaryRepository;
        this.dictionaryItemRepository = dictionaryItemRepository;
        this.headers = new HttpHeaders();
        this.headers.setContentType(MediaType.APPLICATION_JSON);
        this.gson = new Gson();
    }

    @RequestMapping(name = "/dictionaries", method = RequestMethod.GET)
    public ResponseEntity<String> getDictionaries(@RequestParam(value = "dictionary", required = false) String dictCode) {

        if (dictCode == null || dictCode.isEmpty()) {
            List<Dictionary> dictionaries = dictionaryRepository.findAll();

            return new ResponseEntity<>(gson.toJson(dictionaries), headers, HttpStatus.OK);
        }

        if (dictionaryRepository.findByCode(dictCode) == null) {
            return new ResponseEntity<>(null, headers, HttpStatus.valueOf(" Dictionary with the given code does not exist"));
        }

        List<DictionaryItem> dictionaryItems = dictionaryItemRepository.findAllByDomain(dictCode);

        return new ResponseEntity<>(gson.toJson(dictionaryItems), headers, HttpStatus.OK);
    }
}
