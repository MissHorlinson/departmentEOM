package com.departmenteom.repo.dictionary;

import com.departmenteom.entity.dictionary.StudyingType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyingTypeRepo extends CrudRepository<StudyingType, Long> {

    List<StudyingType> findAll();
}
