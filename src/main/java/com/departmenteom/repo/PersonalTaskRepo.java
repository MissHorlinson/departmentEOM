package com.departmenteom.repo;

import com.departmenteom.entity.PersonalTasks;
import org.springframework.data.repository.CrudRepository;

public interface PersonalTaskRepo extends CrudRepository<PersonalTasks, Long> {
}
