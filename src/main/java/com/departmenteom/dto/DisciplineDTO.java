package com.departmenteom.dto;

import com.departmenteom.dto.dictionary.*;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Builder
@Data
public class DisciplineDTO {

    private Long id;
    private String subjectName;
    private Long subjectNameId;
    private String disciplineType;
    private Long disciplineTypeId;
    private String department;
    private String departmentAbr;
    private Long departmentId;
    private int semester;
    private String cipher;
    private String disciplineNum;
    private String reporting;
    private Long reportingFormId;
    private Long reportingId;
    private Long plan;
    private Set<AuditoryHoursDTO> auditoryHoursList;
    private double independentHours;
    private Long independentHoursId;
    private Set<PersonalTasksDTO> personalTaskList;
    private int subgroupNum;
}
