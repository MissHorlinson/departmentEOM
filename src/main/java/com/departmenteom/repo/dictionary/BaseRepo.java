package com.departmenteom.repo.dictionary;

import com.departmenteom.entity.dictionary.Base;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BaseRepo extends CrudRepository<Base, Long> {

    List<Base> findAll();

    Base getById(Long id);

    Optional<Base> getByName(String name);
}
