package com.library.libraryapi.Controller;

import com.google.gson.Gson;
import com.library.libraryapi.DAO.ResourceRepository;
import com.library.libraryapi.Model.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ResourcesController {

    private final ResourceRepository resourceRepository;
    private HttpHeaders headers;
    private String defaultLanguage;
    private Gson gson;

    public ResourcesController(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
        this.headers = new HttpHeaders();
        this.headers.setContentType(MediaType.APPLICATION_JSON);
        this.defaultLanguage = "EN";
        this.gson = new Gson();
    }

    @RequestMapping(value = "/resources", method = RequestMethod.GET)
    public ResponseEntity<String> getResources(@RequestParam(name = "languageCode", required = false) String languageCode,
                                               @RequestParam(name = "name", required = false) String resourceName) {

        List<Resources> result;

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

        Resources singleElement = resourceRepository.findByNameAndLanguageCode(resourceName, languageCode);

        return  new ResponseEntity<>(gson.toJson(singleElement), headers, HttpStatus.OK);
    }
}
