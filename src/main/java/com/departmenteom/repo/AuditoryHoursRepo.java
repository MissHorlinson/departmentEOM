package com.departmenteom.repo;

import com.departmenteom.entity.AuditoryHours;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditoryHoursRepo extends CrudRepository<AuditoryHours, Long> {
}
