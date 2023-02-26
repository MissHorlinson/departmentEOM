package com.departmenteom.repo;

import com.departmenteom.entity.Reporting;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportingRepo extends CrudRepository<Reporting, Long> {
}
