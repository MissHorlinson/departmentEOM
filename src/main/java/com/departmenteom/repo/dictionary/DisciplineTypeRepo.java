package com.departmenteom.repo.dictionary;

import com.departmenteom.entity.dictionary.DisciplineType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisciplineTypeRepo extends CrudRepository<DisciplineType, Long> {

    List<DisciplineType> findAll();

}
