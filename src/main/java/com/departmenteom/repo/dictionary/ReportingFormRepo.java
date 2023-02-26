package com.departmenteom.repo.dictionary;

import com.departmenteom.entity.dictionary.ReportingForm;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportingFormRepo extends CrudRepository<ReportingForm, Long> {

    List<ReportingForm> findAll();

}
