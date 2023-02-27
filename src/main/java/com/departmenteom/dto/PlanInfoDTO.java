package com.departmenteom.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Builder
@Data
@ToString
public class PlanInfoDTO {
    private Long planId;
    private LocalDateTime admissionYear;
    private int numberOfGroup;
    private int numberOfStudent;
    private int numberOfSemester;
    private String planCipher;
    private Long planCipherId;
    private String qualification;
    private Long qualificationId;
    private String base;
    private Long baseId;
    private String step;
    private Long stepId;
    private String studyingForm;
    private Long studyingFormId;
    private String studyingTerm;
    private Long studyingTermId;
    private String rector;
    private Long rectorId;
    private int weekPlanSetSize;
    private int disciplineSetSize;
//        private Set<WeekPlan> weekPlanSet = new HashSet<>();
//        private Set<Discipline> disciplineOfPlanSet = new HashSet<>();

}
