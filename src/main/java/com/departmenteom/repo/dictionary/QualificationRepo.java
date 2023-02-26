package com.departmenteom.repo.dictionary;

import com.departmenteom.entity.dictionary.Qualification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QualificationRepo extends CrudRepository<Qualification, Long> {

    List<Qualification> findAll();

    Qualification getById(Long id);

    Optional<Qualification> getByName(String name);
}
