package com.library.libraryapi.Controller;

import com.google.gson.Gson;
import com.library.libraryapi.DAO.DictionaryItemRepository;
import com.library.libraryapi.DAO.ResourceRepository;
import com.library.libraryapi.Model.DictionaryItem;
import com.library.libraryapi.Model.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ResourcesController {

    private final ResourceRepository resourceRepository;
    private final DictionaryItemRepository dictionaryItemRepository;
    private HttpHeaders headers;
    private String defaultLanguage;
    private Gson gson;

    public ResourcesController(ResourceRepository resourceRepository,
                               DictionaryItemRepository dictionaryItemRepository) {
        this.resourceRepository = resourceRepository;
        this.dictionaryItemRepository = dictionaryItemRepository;
        this.headers = new HttpHeaders();
        this.headers.setContentType(MediaType.APPLICATION_JSON);
        this.defaultLanguage = "EN";
        this.gson = new Gson();
    }

    @RequestMapping(value = "/resources", method = RequestMethod.GET)
    public ResponseEntity<String> getResources(@RequestParam(name = "languageCode", required = false) String languageCode,
                                               @RequestParam(name = "name", required = false) String resourceName) {

        List<Resource> result;
        DictionaryItem languageItem = dictionaryItemRepository.findByDomainAndCode("LANGUAGE", languageCode);

        if (languageCode != null && !languageCode.isEmpty() && languageItem == null) {
            return new ResponseEntity<>(null, headers, HttpStatus.valueOf(" Language code not found"));
        }

        if (resourceName != null && !resourceName.isEmpty() && resourceRepository.countByName(resourceName) == 0) {
            return new ResponseEntity<>(null, headers, HttpStatus.valueOf(" Resource not found"));
        }

        if (languageCode == null || languageCode.isEmpty()) {

            if (resourceName == null || resourceName.isEmpty()) {
                result = resourceRepository.findAllByLanguageCode(defaultLanguage);
            }else {
                result = resourceRepository.findAllByName(resourceName);
            }

            return new ResponseEntity<>(gson.toJson(result), headers, HttpStatus.OK);
        }

        if (resourceName == null || resourceName.isEmpty()) {

            result = resourceRepository.findAllByLanguageCode(languageCode);

            return new ResponseEntity<>(gson.toJson(result), headers, HttpStatus.OK);
        }

        Resource singleElement = resourceRepository.findByNameAndLanguageCode(resourceName, languageCode);

        return  new ResponseEntity<>(gson.toJson(singleElement), headers, HttpStatus.OK);
    }
}
