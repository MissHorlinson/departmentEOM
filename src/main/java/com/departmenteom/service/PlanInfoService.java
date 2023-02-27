package com.departmenteom.service;

import com.departmenteom.dto.PlanInfoDTO;
import com.departmenteom.entity.GroupInfo;
import com.departmenteom.entity.GroupStream;
import com.departmenteom.entity.PlanInfo;
import com.departmenteom.entity.dictionary.*;
import com.departmenteom.repo.GroupStreamRepo;
import com.departmenteom.repo.PlanInfoRepo;
import com.departmenteom.util.EntityToDtoConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlanInfoService {

    private final PlanInfoRepo planInfoRepo;
    private final UtilDictionaryService utilDictionaryService;
    private final GroupStreamRepo groupStreamRepo;
    private final GroupInfoService groupInfoService;


    public List<PlanInfoDTO> getAllPlans() {
        List<PlanInfo> planInfoList = planInfoRepo.findAll();
        List<PlanInfoDTO> planInfoDTOList = new ArrayList<>();
        planInfoList.forEach(item -> planInfoDTOList.add(EntityToDtoConverter.planInfoToDto(item)));
        return planInfoDTOList;
    }

    public PlanInfo savePlanData(PlanInfo planInfo) {
        if (planInfo.getId() != null) {
            if(planInfoRepo.findById(planInfo.getId()).isPresent()) {
                log.info("Update existing plan");
                planInfoRepo.save(planInfo);
            }
        } else {
            log.info("Save new plan");
            planInfo = planInfoRepo.save(planInfo);

            GroupStream groupStream = new GroupStream();

            log.info("planInfo.getAdmissionYear() {}, planInfo.getPlanCipher().getId() {}", planInfo.getAdmissionYear(), planInfo.getPlanCipher().getId());
            Optional<GroupInfo> groupData = groupInfoService.getGroupByYearAndCipherId(planInfo.getAdmissionYear(), planInfo.getPlanCipher().getId());

            if (groupData.isPresent() && groupData.get().getGroupInfoStream() != null) {
                groupStream = groupStreamRepo.getById(groupData.get().getGroupInfoStream().getId());
            }

            groupStream.setStreamPlanInfo(planInfo);
            groupStreamRepo.save(groupStream);
        }

//
//        Base base = utilDictionaryService.getBaseById(planInfo.getBase().getId());
//        Step step = utilDictionaryService.getStepById(planInfo.getStep().getId());
//        Cipher cipher = utilDictionaryService.getCipherById(planInfo.getPlanCipher().getId());
//
//        planInfo.setBase(base);
//        planInfo.setPlanCipher(cipher);
//        planInfo.setStep(step);


        return planInfo;
    }

    public PlanInfo getPlanById(Long id) {
        Optional<PlanInfo> planInfoOpt = planInfoRepo.findById(id);
        if (planInfoOpt.isPresent()) {
            return planInfoOpt.get();
        }
        throw new IllegalArgumentException("Plan with such id doesn`t exist");
    }

    public int getSemesterNum(Long id) {
        return getPlanById(id).getNumberOfSemester();
    }

    public long getPlanIdForGroupStream(Long id) {
         return groupStreamRepo.getById(id).getStreamPlanInfo().getId();
    }
}
