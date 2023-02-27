package com.departmenteom.repo.dictionary;

import com.departmenteom.entity.dictionary.Step;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StepRepo extends CrudRepository<Step, Long> {

    List<Step> findAll();

    Step getById(Long id);

    Optional<Step> getByName(String name);
}
