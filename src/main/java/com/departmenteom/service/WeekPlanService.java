package com.departmenteom.service;

import com.departmenteom.entity.WeekPlan;
import com.departmenteom.repo.WeekPlanRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeekPlanService {

    private final WeekPlanRepo weekPlanRepo;

    public void saveWeekPlanData(List<WeekPlan> weekPlan) {
        weekPlan.forEach(week -> {
            log.info("Save week {} ", weekPlan.toString());
            if (week.getStudyingType().getId() != null || week.getStartWeek() != 0 || week.getTerm() != 0) {
                weekPlanRepo.save(week);
            }
        });
    }

    public List<WeekPlan> getByPlan(Long id) {
        log.info("Search for weeks of plan with is {}", id);
       List<WeekPlan> weekPlanList = weekPlanRepo.getWeekPlansByWeekPlanInfoId(id);
       return weekPlanList.stream().sorted(Comparator.comparing(WeekPlan::getSemester).thenComparing(WeekPlan::getStartWeek)).collect(Collectors.toList());
    }

    public Map<Integer, List<WeekPlan>> getByPlanAndCourse(Long planId, int course) {
        log.info("Search weeks for {} of plan with id {}", course, planId);
        int semF = course * 2 - 1;
        int semS = course * 2;
        List<WeekPlan> weekPlanList = weekPlanRepo.getWeekPlanByWeekPlanInfoIdAndSemesterGreaterThanEqualAndSemesterLessThanEqual(planId, semF, semS);
        return weekPlanList.stream().collect(Collectors.groupingBy(WeekPlan::getSemester));
    }

    public void deleteWeekItemById(Long id) {
        log.info("Delete week");
        weekPlanRepo.deleteById(id);
    }
}
