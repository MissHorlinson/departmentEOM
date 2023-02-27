package com.departmenteom.util;

import com.departmenteom.dto.*;
import com.departmenteom.dto.dictionary.*;
import com.departmenteom.entity.*;
import com.departmenteom.entity.dictionary.*;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;


@Slf4j
public class EntityToDtoConverter {

    public static BaseDTO baseToDto(Base base) {
        return BaseDTO.builder()
                .id(base.getId())
                .name(base.getName())
                .build();
    }

    public static AcademicRankDTO academicRankToDto(AcademicRank academicRank) {
        return AcademicRankDTO.builder()
                .id(academicRank.getId())
                .name(academicRank.getName())
                .build();
    }


    public static CipherDTO cipherToDto(Cipher cipher) {
        return CipherDTO.builder()
                .id(cipher.getId())
                .name(cipher.getName())
                .build();
    }

    public static DegreeDTO degreeToDto(Degree degree) {
        return DegreeDTO.builder()
                .id(degree.getId())
                .name(degree.getName())
                .build();
    }

    public static PositionDTO positionToDto(Position position) {
        return PositionDTO.builder()
                .id(position.getId())
                .name(position.getName())
                .build();
    }

    public static QualificationDTO qualificationToDto(Qualification qualification) {
        return QualificationDTO.builder()
                .id(qualification.getId())
                .name(qualification.getName())
                .build();
    }

    public static StepDTO stepToDto(Step step) {
        return StepDTO.builder()
                .id(step.getId())
                .name(step.getName())
                .build();
    }

    public static StudyingTermDTO studyingTermToDto(StudyingTerm studyingTerm) {
        return StudyingTermDTO.builder()
                .id(studyingTerm.getId())
                .name(studyingTerm.getName())
                .termInMonthInt(studyingTerm.getTermInMonth())
                .semesterNum(studyingTerm.getSemesterNum())
                .build();
    }

    public static StudyingFormDTO studyingFormToDto(StudyingForm studyingForm) {
        return StudyingFormDTO.builder()
                .id(studyingForm.getId())
                .name(studyingForm.getName())
                .build();
    }

    public static DepartmentDTO departmentToDto(Department department) {
        return DepartmentDTO.builder()
                .id(department.getId())
                .name(department.getName())
                .abbreviation(department.getAbbreviation())
                .build();
    }

    public static StudyingTypeDTO studyingTypeToDto(StudyingType studyingType) {
        String letter = studyingType.getLetter() == null ? "" : studyingType.getLetter();
        return StudyingTypeDTO.builder()
                .id(studyingType.getId())
                .name(studyingType.getName())
                .letter(letter)
                .build();
    }

    public static DisciplineFormDTO disciplineFormToDto(DisciplineForm disciplineForm) {
        return DisciplineFormDTO.builder()
                .id(disciplineForm.getId())
                .name(disciplineForm.getName())
                .build();
    }

    public static DisciplineTypeDTO disciplineTypeToDto(DisciplineType disciplineType) {
        return DisciplineTypeDTO.builder()
                .id(disciplineType.getId())
                .name(disciplineType.getName())
                .build();
    }

    public static PersonalTaskFormDTO personalTaskFormToDto(PersonalTaskForm personalTaskForm) {
        return PersonalTaskFormDTO.builder()
                .id(personalTaskForm.getId())
                .name(personalTaskForm.getName())
                .build();
    }

    public static SubjectNameDTO subjectNameToDto(SubjectName subjectName) {
        return SubjectNameDTO.builder()
                .id(subjectName.getId())
                .name(subjectName.getName())
                .build();
    }

    public static ReportingFormDTO reportingFormToDto(ReportingForm reportingForm) {
        return ReportingFormDTO.builder()
                .id(reportingForm.getId())
                .name(reportingForm.getName())
                .build();
    }

    public static AuditoryHoursDTO auditoryHoursToDto(AuditoryHours auditoryHours) {
        return AuditoryHoursDTO.builder()
                .id(auditoryHours.getId())
                .disciplineName(auditoryHours.getDisciplineAuditoryHours().getSubjectName().getName())
                .disciplineForm(auditoryHours.getDisciplineForm().getName())
                .disciplineFormId(auditoryHours.getDisciplineForm().getId())
                .hoursNum(auditoryHours.getHoursNum())
                .build();
    }

    public static IndependentHoursDTO independentHoursToDto(IndependentHours independentHours) {
        return IndependentHoursDTO.builder()
                .id(independentHours.getId())
                .disciplineName(independentHours.getDisciplineIndependentHours().getSubjectName().getName())
                .hoursNum(independentHours.getHoursNum())
                .build();
    }

    public static PersonalTasksDTO personalTasksToDto(PersonalTasks personalTasks) {
        return PersonalTasksDTO.builder()
                .id(personalTasks.getId())
                .disciplineName(personalTasks.getDisciplinePersonalTask().getSubjectName().getName())
                .personalTaskForm(personalTasks.getPersonalTaskForm().getName())
                .personalTaskFormId(personalTasks.getPersonalTaskForm().getId())
                .build();
    }

    public static ReportingDTO reportingToDto(Reporting reporting) {
        return ReportingDTO.builder()
                .id(reporting.getId())
                .disciplineReporting(reporting.getDisciplineReporting().getSubjectName().getName())
                .disciplineReportingForm(reporting.getDisciplineReportingForm().getName())
                .disciplineReportingFormId(reporting.getDisciplineReportingForm().getId())
                .build();
    }

    public static GroupInfoDTO groupInfoToDto(GroupInfo groupInfo) {
        String name = groupInfo.getAdmissionYear().getYear() + "-" + groupInfo.getGroupCipher().getName() + "-" + groupInfo.getIndex();
        long stream = groupInfo.getGroupInfoStream() != null ? groupInfo.getGroupInfoStream().getId() : 0L;
        return GroupInfoDTO.builder()
                .id(groupInfo.getId())
                .groupCipher(groupInfo.getGroupCipher().getName())
                .groupCipherId(groupInfo.getGroupCipher().getId())
                .index(groupInfo.getIndex())
                .admissionYear(groupInfo.getAdmissionYear())
                .groupFullName(name)
                .streamId(stream)
                .build();
    }

    public static PlanInfoDTO planInfoToDto(PlanInfo plan) {
        return PlanInfoDTO.builder()
                .planId(plan.getId())
                .rector(plan.getRector())
                .planCipher(plan.getPlanCipher().getName())
                .planCipherId(plan.getPlanCipher().getId())
                .qualification(plan.getQualification().getName())
                .qualificationId(plan.getQualification().getId())
                .studyingTerm(plan.getStudyingTerm().getName())
                .studyingTermId(plan.getStudyingTerm().getId())
                .base(plan.getBase().getName())
                .baseId(plan.getBase().getId())
                .admissionYear(plan.getAdmissionYear())
                .step(plan.getStep().getName())
                .stepId(plan.getStep().getId())
                .studyingForm(plan.getStudyingForm().getName())
                .studyingFormId(plan.getStudyingForm().getId())
                .numberOfGroup(plan.getNumberOfGroup())
                .numberOfStudent(plan.getNumberOfStudent())
                .numberOfSemester(plan.getNumberOfSemester())
                .weekPlanSetSize(plan.getPlanWeeks().size())
                .disciplineSetSize(plan.getDisciplineOfPlanSet().size())
                .build();
    }

    public static DisciplineDTO disciplineToDto(Discipline discipline) {

        int disciplineNum = discipline.getDisciplineNum();
        String disciplineSubNum = discipline.getDisciplineSubNum() > 0 ? ".".concat(String.valueOf(discipline.getDisciplineSubNum())) : "";
        Set<AuditoryHours> auditoryHoursSet = discipline.getAuditoryHoursSet();
        Set<AuditoryHoursDTO> auditoryHoursDTOSet = new HashSet<>();
        auditoryHoursSet.forEach(item -> auditoryHoursDTOSet.add(auditoryHoursToDto(item)));

        Set<PersonalTasks> personalTasksSet = discipline.getPersonalTasksSet();
        Set<PersonalTasksDTO> personalTasksDTOSet = new HashSet<>();
        personalTasksSet.forEach(item -> personalTasksDTOSet.add(personalTasksToDto(item)));


        double independentHours = 0.0;
        long independentHoursId = 0L;
        if (discipline.getIndependentHours() != null && discipline.getIndependentHours().getId() != 0L && discipline.getIndependentHours().getHoursNum() != 0) {
            independentHours = discipline.getIndependentHours().getHoursNum();
            independentHoursId = discipline.getIndependentHours().getId();

        }

        String reporting = discipline.getReporting() == null ? null : discipline.getReporting().getDisciplineReportingForm().getName();
        Long reportingFormId = discipline.getReporting() == null ? null : discipline.getReporting().getDisciplineReportingForm().getId();
        Long reportingId = discipline.getReporting() == null ? null : discipline.getReporting().getId();

        return DisciplineDTO.builder()
                .id(discipline.getId())
                .subjectName(discipline.getSubjectName().getName())
                .subjectNameId(discipline.getSubjectName().getId())
                .disciplineType(discipline.getDisciplineType().getName())
                .disciplineTypeId(discipline.getDisciplineType().getId())
                .departmentId(discipline.getDisciplineType().getId())
                .department(discipline.getDepartment().getName())
                .departmentAbr(discipline.getDepartment().getAbbreviation())
                .departmentId(discipline.getDepartment().getId())
                .semester(discipline.getSemester())
                .cipher(discipline.getCipher())
                .disciplineNum(disciplineNum + disciplineSubNum)
                .plan(discipline.getDisciplinePlanInfo().getId())
                .reporting(reporting)
                .reportingFormId(reportingFormId)
                .reportingId(reportingId)
                .auditoryHoursList(auditoryHoursDTOSet)
                .independentHours(independentHours)
                .independentHoursId(independentHoursId)
                .personalTaskList(personalTasksDTOSet)
                .build();
    }

    public static WeekPlanDTO weekPlanToDto(WeekPlan weekPlan) {
        return WeekPlanDTO.builder()
                .id(weekPlan.getId())
                .semester(weekPlan.getSemester())
                .startWeek(weekPlan.getStartWeek())
                .term(weekPlan.getTerm())
                .studyingTypeName(weekPlan.getStudyingType().getName())
                .studyingTypeId(weekPlan.getStudyingType().getId())
                .planId(weekPlan.getWeekPlanInfo().getId())
                .build();
    }

    public static StudentDTO studentToDTO(Student student) {
        String name = student.getGroupInfo().getAdmissionYear().getYear() + "-" + student.getGroupInfo().getGroupCipher().getName() + "-" + student.getGroupInfo().getIndex();
        return StudentDTO.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .secondName(student.getSecondName())
                .lastName(student.getLastName())
                .email(student.getEmail())
                .recordBook(student.getRecordBook())
                .phone(student.getPhone())
                .groupName(name)
                .groupId(student.getGroupInfo().getId())
                .build();
    }

    public static TeacherDTO teacherToDTO(Teacher teacher) {
        return TeacherDTO.builder()
                .id(teacher.getId())
                .firstName(teacher.getFirstName())
                .secondName(teacher.getSecondName())
                .lastName(teacher.getLastName())
                .email(teacher.getEmail())
                .phone(teacher.getPhone())
                .department(teacher.getDepartmentInfo().getAbbreviation())
                .departmentId(teacher.getDepartmentInfo().getId())
                .build();
    }

    public static UserDTO userToDTO(UsersCred usersCred) {
        return UserDTO.builder()
                .id(usersCred.getId())
                .username(usersCred.getUsername())
                .role(usersCred.getRole())
                .status(usersCred.getStatus())
                .build();
    }
}
