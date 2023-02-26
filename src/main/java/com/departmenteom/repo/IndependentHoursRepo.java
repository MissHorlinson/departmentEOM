package com.departmenteom.repo;

import com.departmenteom.entity.IndependentHours;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IndependentHoursRepo extends CrudRepository<IndependentHours, Long> {

    Optional<IndependentHours> getIndependentHoursByDisciplineIndependentHoursId(Long id);

    Optional<IndependentHours> getById(Long id);
}
