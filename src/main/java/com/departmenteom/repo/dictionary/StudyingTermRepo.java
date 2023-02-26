package com.departmenteom.repo.dictionary;

import com.departmenteom.entity.dictionary.StudyingTerm;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudyingTermRepo extends CrudRepository<StudyingTerm, Long> {

    List<StudyingTerm> findAll();

    StudyingTerm getById(Long id);

    Optional<StudyingTerm> getByName(String name);
}
