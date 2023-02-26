package com.departmenteom.repo.dictionary;

import com.departmenteom.entity.dictionary.DisciplineForm;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisciplineFormRepo extends CrudRepository<DisciplineForm, Long> {

    List<DisciplineForm> findAll();
}
