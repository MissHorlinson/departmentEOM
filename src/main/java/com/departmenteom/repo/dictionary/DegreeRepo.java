package com.departmenteom.repo.dictionary;

import com.departmenteom.entity.dictionary.Degree;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DegreeRepo extends CrudRepository<Degree, Long> {

    List<Degree> findAll();
}
