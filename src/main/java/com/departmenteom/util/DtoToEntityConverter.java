package com.departmenteom.util;

import com.departmenteom.dto.*;
import com.departmenteom.dto.dictionary.*;
import com.departmenteom.entity.*;
import com.departmenteom.entity.dictionary.*;

public class DtoToEntityConverter {

    public static UsersCred userCredToEntity(UserDTO usersCred) {
        UsersCred newUser = new UsersCred();
        newUser.setUsername(usersCred.getUsername());
        newUser.setRole(usersCred.getRole());
        newUser.setStatus(usersCred.getStatus());
        return newUser;
    }
    public static DisciplineForm disciplineFormToEntity(DisciplineFormDTO disciplineFormDTO) {
        DisciplineForm disciplineForm = new DisciplineForm();
        disciplineForm.setId(disciplineFormDTO.getId());
        disciplineForm.setName(disciplineFormDTO.getName());
        return disciplineForm;
    }

    public static DisciplineType disciplineTypeToEntity(DisciplineTypeDTO disciplineTypeDTO) {
        DisciplineType disciplineType = new DisciplineType();
        disciplineType.setId(disciplineTypeDTO.getId());
        disciplineType.setName(disciplineTypeDTO.getName());
        return disciplineType;
    }

    public static PersonalTaskForm personalTaskFormToEntity(PersonalTaskFormDTO personalTaskFormDTO) {
        PersonalTaskForm personalTaskForm = new PersonalTaskForm();
        personalTaskForm.setId(personalTaskFormDTO.getId());
        personalTaskForm.setName(personalTaskFormDTO.getName());
        return personalTaskForm;
    }

    public static SubjectName subjectNameToEntity(SubjectNameDTO subjectNameDTO) {
        SubjectName subjectName = new SubjectName();
        subjectName.setId(subjectNameDTO.getId());
        subjectName.setName(subjectNameDTO.getName());
        return subjectName;
    }

    public static ReportingForm reportingFormToEntity(ReportingFormDTO reportingFormDTO) {
        ReportingForm reportingForm = new ReportingForm();
        reportingForm.setId(reportingFormDTO.getId());
        reportingForm.setName(reportingFormDTO.getName());
        return reportingForm;
    }

    public static GroupInfo groupInfoToEntity(GroupInfoDTO groupInfoDTO) {
        GroupInfo groupInfo = new GroupInfo();
        groupInfo.setId(groupInfoDTO.getId());
        groupInfo.setAdmissionYear(groupInfoDTO.getAdmissionYear());
        groupInfo.setIndex(groupInfoDTO.getIndex());
        return groupInfo;
    }

    public static PlanInfo planInfoToEntity(PlanInfoDTO planInfoDTO) {
        PlanInfo planInfo = new PlanInfo();
        planInfo.setId(planInfoDTO.getPlanId());
        planInfo.setAdmissionYear(planInfoDTO.getAdmissionYear());
        planInfo.setNumberOfSemester(planInfoDTO.getNumberOfSemester());
        planInfo.setNumberOfGroup(planInfoDTO.getNumberOfGroup());
        planInfo.setNumberOfStudent(planInfoDTO.getNumberOfStudent());
        return planInfo;
    }

    public static Student studentToEntity(StudentDTO studentDTO) {
        Student student = new Student();
        student.setId(studentDTO.getId());
        student.setFirstName(studentDTO.getFirstName());
        student.setSecondName(studentDTO.getSecondName());
        student.setLastName(studentDTO.getLastName());
        student.setPhone(studentDTO.getPhone());
        student.setRecordBook(studentDTO.getRecordBook());
        student.setEmail(studentDTO.getEmail());
        return student;
    }

    public static Teacher teacherToEntity(TeacherDTO teacherDTO) {
        Teacher teacher = new Teacher();
        teacher.setId(teacherDTO.getId());
        teacher.setFirstName(teacherDTO.getFirstName());
        teacher.setSecondName(teacherDTO.getSecondName());
        teacher.setLastName(teacherDTO.getLastName());
        teacher.setPhone(teacherDTO.getPhone());
        teacher.setEmail(teacherDTO.getEmail());
        return teacher;
    }

    public static AcademicRank academicRankToEntity(AcademicRankDTO academicRankDTO) {
        AcademicRank academicRank = new AcademicRank();
        academicRank.setId(academicRankDTO.getId());
        academicRank.setName(academicRankDTO.getName());
        return academicRank;
    }

    public static Base baseToEntity(BaseDTO baseDTO) {
        Base base = new Base();
        base.setId(baseDTO.getId());
        base.setName(baseDTO.getName());
        return base;
    }

    public static Cipher cipherToEntity(CipherDTO cipherDTO) {
        Cipher cipher = new Cipher();
        cipher.setId(cipherDTO.getId());
        cipher.setName(cipherDTO.getName());
        return cipher;
    }

    public static Degree degreeToEntity(DegreeDTO degreeDTO) {
        Degree degree = new Degree();
        degree.setId(degreeDTO.getId());
        degree.setName(degreeDTO.getName());
        return degree;
    }

    public static Department departmentToEntity(DepartmentDTO departmentDTO) {
        Department department = new Department();
        department.setId(departmentDTO.getId());
        department.setName(departmentDTO.getName());
        department.setAbbreviation(departmentDTO.getAbbreviation());
        return department;
    }

    public static Position positionToEntity(PositionDTO positionDTO) {
        Position position = new Position();
        position.setId(positionDTO.getId());
        position.setName(positionDTO.getName());
        return position;
    }

    public static Qualification qualificationToEntity(QualificationDTO qualificationDTO) {
        Qualification qualification = new Qualification();
        qualification.setId(qualificationDTO.getId());
        qualification.setName(qualificationDTO.getName());
        return qualification;
    }

    public static Step stepToEntity(StepDTO stepDTO) {
        Step step = new Step();
        step.setId(stepDTO.getId());
        step.setName(stepDTO.getName());
        return step;
    }

    public static StudyingForm studyingFormToEntity(StudyingFormDTO studyingFormDTO) {
        StudyingForm studyingForm = new StudyingForm();
        studyingForm.setId(studyingFormDTO.getId());
        studyingForm.setName(studyingFormDTO.getName());
        return studyingForm;
    }

    public static StudyingTerm studyingTermToEntity(StudyingTermDTO studyingTermDTO) {
        StudyingTerm studyingTerm = new StudyingTerm();
        studyingTerm.setId(studyingTermDTO.getId());
        studyingTerm.setName(studyingTermDTO.getName());
        studyingTerm.setTermInMonth(studyingTermDTO.getTermInMonthInt());
        studyingTerm.setSemesterNum(studyingTermDTO.getSemesterNum());
        return studyingTerm;
    }

    public static StudyingType studyingTypeToEntity(StudyingTypeDTO studyingTypeDTO) {
        StudyingType studyingType = new StudyingType();
        studyingType.setId(studyingTypeDTO.getId());
        studyingType.setName(studyingTypeDTO.getName());
        studyingType.setLetter(studyingTypeDTO.getLetter());
        return studyingType;
    }

    public static WeekPlan weekPlanToEntity(WeekPlanDTO weekPlanDTO) {
        WeekPlan weekPlan = new WeekPlan();
        weekPlan.setId(weekPlanDTO.getId());
        weekPlan.setStartWeek(weekPlanDTO.getStartWeek());
        weekPlan.setTerm(weekPlanDTO.getTerm());
        weekPlan.setSemester(weekPlanDTO.getSemester());
        return weekPlan;
    }
}
