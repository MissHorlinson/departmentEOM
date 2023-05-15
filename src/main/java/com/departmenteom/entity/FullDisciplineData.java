package com.departmenteom.entity;

import com.departmenteom.entity.dictionary.Department;
import com.departmenteom.entity.dictionary.DisciplineType;
import com.departmenteom.entity.dictionary.SubjectName;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class FullDisciplineData {
    private Long id;
    private SubjectName subjectName;
    private DisciplineType disciplineType;
    private Department department;
    private int semester;
    private String cipher;
    private int disciplineNum;
    private int disciplineSubNum;
    private PlanInfo plan;
    private Reporting reporting;
    private List<AuditoryHours> auditoryHoursList;
    private List<AuditoryHours> auditoryHoursToRemove;
    private IndependentHours independentHours;
    private List<PersonalTasks> personalTaskList;
    private int subgroupNum;
}
