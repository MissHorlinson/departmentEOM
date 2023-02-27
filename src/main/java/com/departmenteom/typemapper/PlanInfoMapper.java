package com.departmenteom.typemapper;

import com.departmenteom.dto.PlanInfoDTO;
import com.departmenteom.entity.PlanInfo;
import com.departmenteom.entity.dictionary.*;
import com.departmenteom.service.PlanInfoService;
import com.departmenteom.service.UtilDictionaryService;
import com.departmenteom.util.DtoToEntityConverter;
import com.departmenteom.util.EntityToDtoConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component
public class PlanInfoMapper {
    private final PlanInfoService planInfoService;
    private final UtilDictionaryService utilDictionaryService;

    public List<PlanInfoDTO> getAllPlans() {
        return planInfoService.getAllPlans();
    }

    public PlanInfoDTO getPlanById(Long id) {
        return EntityToDtoConverter.planInfoToDto(planInfoService.getPlanById(id));
    }

    public PlanInfoDTO savePlanInfoData(PlanInfoDTO planInfoDTO) {
        PlanInfo planInfo = DtoToEntityConverter.planInfoToEntity(planInfoDTO);

        Base base = utilDictionaryService.getBaseById(planInfoDTO.getBaseId());
        Step step = utilDictionaryService.getStepById(planInfoDTO.getStepId());
        Cipher cipher = utilDictionaryService.getCipherById(planInfoDTO.getStudyingTermId());
        Qualification qualification = utilDictionaryService.getQualificationById(planInfoDTO.getQualificationId());
        StudyingTerm studyingTerm = utilDictionaryService.getStudyingTermById(planInfoDTO.getStudyingTermId());
        StudyingForm studyingForm = utilDictionaryService.getStudyingFormById(planInfoDTO.getStudyingFormId());

        planInfo.setBase(base);
        planInfo.setPlanCipher(cipher);
        planInfo.setStep(step);
        planInfo.setQualification(qualification);
        planInfo.setStudyingTerm(studyingTerm);
        planInfo.setStudyingForm(studyingForm);

        planInfo = planInfoService.savePlanData(planInfo);
        return EntityToDtoConverter.planInfoToDto(planInfo);
    }

    public int getPlanSemesterNum(Long id) {
        return planInfoService.getSemesterNum(id);
    }

    public long getPlanByGroupStream(Long streamId) {
        return planInfoService.getPlanIdForGroupStream(streamId);
    }
}