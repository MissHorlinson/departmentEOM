package com.departmenteom.repo.dictionary;

import com.departmenteom.entity.dictionary.PersonalTaskForm;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonalTaskFormRepo extends CrudRepository<PersonalTaskForm, Long> {
    List<PersonalTaskForm> findAll();

    Optional<PersonalTaskForm> getByName(String name);
}
