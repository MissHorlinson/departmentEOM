package com.departmenteom.repo;

import com.departmenteom.entity.Discipline;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisciplineRepo extends CrudRepository<Discipline, Long> {

    List<Discipline> getDisciplinesByDisciplinePlanInfoId(Long id);

    List<Discipline> getDisciplinesByDisciplinePlanInfoIdAndSemester(Long id, int semester);

    List<Discipline> getDisciplinesByDisciplinePlanInfoIdAndSemesterAfterAndSemesterBefore(Long id, int semesterAfter, int semesterBefore);

    List<Discipline> getDisciplinesByDisciplinePlanInfoIdAndSemesterGreaterThanEqualAndSemesterLessThanEqual(Long id, int firstSemester, int secondSemester);
}
