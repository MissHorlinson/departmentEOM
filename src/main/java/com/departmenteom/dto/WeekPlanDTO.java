package com.departmenteom.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class WeekPlanDTO {

    private Long id;
    private int semester;
    private int startWeek;
    private int term;
    private Long planId;
    private String studyingTypeName;
    private Long studyingTypeId;
}
