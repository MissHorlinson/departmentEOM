package com.departmenteom.repo.dictionary;

import com.departmenteom.entity.dictionary.StudyingType;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudyingTypeRepo extends CrudRepository<StudyingType, Long> {

    List<StudyingType> findAll();

    @Query(value = "SELECT * FROM dictionary_studying_type dst WHERE BINARY dst.letter_mean = :letter")
    Optional<StudyingType> getByLetter(@Param("letter") String letter);
}
