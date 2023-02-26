package com.departmenteom.repo.dictionary;

import com.departmenteom.entity.dictionary.SubjectName;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectNameRepo extends CrudRepository<SubjectName, Long> {

    List<SubjectName> findAll();

    Optional<SubjectName> getByName(String name);
}
