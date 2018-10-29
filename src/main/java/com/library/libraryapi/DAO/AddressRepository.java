package com.library.libraryapi.DAO;

import com.library.libraryapi.Model.AddressModel;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<AddressModel, Long> {

}
