package com.departmenteom.repo;

import com.departmenteom.entity.WeekPlan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeekPlanRepo extends CrudRepository<WeekPlan, Long> {

    List<WeekPlan> getWeekPlansByWeekPlanInfoId(Long id);

    List<WeekPlan> getWeekPlanByWeekPlanInfoIdAndSemesterGreaterThanEqualAndSemesterLessThanEqual(Long id, int firstSemester, int secondSemester);

}
