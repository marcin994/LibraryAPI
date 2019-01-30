package com.library.libraryapi.DAO;

import com.library.libraryapi.Model.DictionaryItem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DictionaryItemRepository extends MongoRepository<DictionaryItem, String> {
    List<DictionaryItem> findAllByDomain(String domain);
    DictionaryItem findByDomainAndCode(String domain, String code);
    DictionaryItem findByCode(String code);
}
