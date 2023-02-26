package com.departmenteom.repo.dictionary;

import com.departmenteom.entity.dictionary.Cipher;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CipherRepo extends CrudRepository<Cipher,Long> {

    List<Cipher> findAll();


    Cipher getById(Long id);
}
