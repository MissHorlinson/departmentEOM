package com.departmenteom.repo.dictionary;

import com.departmenteom.entity.dictionary.StudyingForm;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudyingFromRepo extends CrudRepository<StudyingForm, Long> {

    List<StudyingForm> findAll();

    StudyingForm getById(Long id);

    Optional<StudyingForm> getByName(String name);
}
