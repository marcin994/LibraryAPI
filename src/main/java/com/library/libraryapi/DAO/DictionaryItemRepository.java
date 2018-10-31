package com.library.libraryapi.DAO;

import com.library.libraryapi.Model.DictionaryItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DictionaryItemRepository extends CrudRepository<DictionaryItem, Long> {
    List<DictionaryItem> findAllByDomain(String domain);
}
