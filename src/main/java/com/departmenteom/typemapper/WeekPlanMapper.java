package com.departmenteom.typemapper;

import com.departmenteom.dto.WeekPlanDTO;
import com.departmenteom.entity.PlanInfo;
import com.departmenteom.entity.WeekPlan;
import com.departmenteom.entity.dictionary.StudyingType;
import com.departmenteom.service.PlanInfoService;
import com.departmenteom.service.UtilDictionaryService;
import com.departmenteom.service.WeekPlanService;
import com.departmenteom.util.DtoToEntityConverter;
import com.departmenteom.util.EntityToDtoConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component
public class WeekPlanMapper {

    private final WeekPlanService weekPlanService;
    private final UtilDictionaryService utilDictionaryService;
    private final PlanInfoService planInfoService;

    public void saveWeekPlanData(List<WeekPlanDTO> weekPlanDTOList) {
        List<WeekPlan> weekPlanList = new ArrayList<>();
        weekPlanDTOList.stream().forEach(item -> {
            WeekPlan weekPlan = DtoToEntityConverter.weekPlanToEntity(item);
            StudyingType studyingType = utilDictionaryService.getStudyingTypeById(item.getStudyingTypeId());
            PlanInfo planInfo = planInfoService.getPlanById(item.getPlanId());
            weekPlan.setStudyingType(studyingType);
            weekPlan.setWeekPlanInfo(planInfo);
            weekPlanList.add(weekPlan);
        });
        weekPlanService.saveWeekPlanData(weekPlanList);
    }

    public List<WeekPlanDTO> getListByPlan(@PathVariable Long id) {
        List<WeekPlan> weekPlanList = weekPlanService.getByPlan(id);
        List<WeekPlanDTO> weekPlanDTOList = new ArrayList<>();
        weekPlanList.forEach(item -> weekPlanDTOList.add(EntityToDtoConverter.weekPlanToDto(item)));
        return weekPlanDTOList;
    }
}
