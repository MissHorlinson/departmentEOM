package com.departmenteom.repo;

import com.departmenteom.entity.Teacher;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TeacherRepo extends CrudRepository<Teacher, Long> {

    List<Teacher> findAll();
}
