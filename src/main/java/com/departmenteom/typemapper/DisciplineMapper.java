package com.departmenteom.typemapper;

import com.departmenteom.dto.DisciplineDTO;
import com.departmenteom.dto.dictionary.*;
import com.departmenteom.entity.Discipline;
import com.departmenteom.entity.dictionary.*;
import com.departmenteom.service.DisciplineService;
import com.departmenteom.util.DtoToEntityConverter;
import com.departmenteom.util.EntityToDtoConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component
public class DisciplineMapper {

    private final DisciplineService disciplineService;

    public DisciplineDTO getById(Long id) {
        return EntityToDtoConverter.disciplineToDto(disciplineService.getDisciplineById(id));
    }

    public List<DisciplineFormDTO> getDisciplineFormList() {
        List<DisciplineForm> disciplineFormList = disciplineService.getDisciplineFormList();
        List<DisciplineFormDTO> disciplineFormDTOList = new ArrayList<>();
        disciplineFormList.forEach(item -> disciplineFormDTOList.add(EntityToDtoConverter.disciplineFormToDto(item)));
        return disciplineFormDTOList;
    }

    public DisciplineFormDTO saveDisciplineForm(DisciplineFormDTO disciplineFormDTO) {
        DisciplineForm disciplineForm = DtoToEntityConverter.disciplineFormToEntity(disciplineFormDTO);
        disciplineForm = disciplineService.saveDisciplineForm(disciplineForm);
        log.info("DisciplineForm saved");
        return EntityToDtoConverter.disciplineFormToDto(disciplineForm);
    }

    public List<DisciplineTypeDTO> getDisciplineTypeList() {
        List<DisciplineType> disciplineTypeList = disciplineService.getDisciplineTypeList();
        List<DisciplineTypeDTO> disciplineTypeDTOList = new ArrayList<>();
        disciplineTypeList.forEach(item -> disciplineTypeDTOList.add(EntityToDtoConverter.disciplineTypeToDto(item)));
        return disciplineTypeDTOList;
    }

    public DisciplineTypeDTO saveDisciplineType(DisciplineTypeDTO disciplineTypeDTO) {
        DisciplineType disciplineType = DtoToEntityConverter.disciplineTypeToEntity(disciplineTypeDTO);
        disciplineType = disciplineService.saveDisciplineType(disciplineType);
        log.info("DisciplineType saved");
        return EntityToDtoConverter.disciplineTypeToDto(disciplineType);
    }

    public List<PersonalTaskFormDTO> getPersonalTaskFormList() {
        List<PersonalTaskForm> personalTaskFormList = disciplineService.getPersonalTaskFormList();
        List<PersonalTaskFormDTO> personalTaskFormDTOList = new ArrayList<>();
        personalTaskFormList.forEach(item -> personalTaskFormDTOList.add(EntityToDtoConverter.personalTaskFormToDto(item)));
        return personalTaskFormDTOList;
    }

    public PersonalTaskFormDTO savePersonalTaskForm(PersonalTaskFormDTO personalTaskFormDTO) {
        PersonalTaskForm personalTaskForm = DtoToEntityConverter.personalTaskFormToEntity(personalTaskFormDTO);
        personalTaskForm = disciplineService.savePersonalTaskForm(personalTaskForm);
        log.info("PersonalTaskForm saved");
        return EntityToDtoConverter.personalTaskFormToDto(personalTaskForm);
    }

    public List<SubjectNameDTO> getSubjectNameList() {
        List<SubjectName> subjectNameList = disciplineService.getSubjectNameList();
        List<SubjectNameDTO> subjectNameDTOList = new ArrayList<>();
        subjectNameList.forEach(item -> subjectNameDTOList.add(EntityToDtoConverter.subjectNameToDto(item)));
        return subjectNameDTOList;
    }

    public SubjectNameDTO saveSubjectName(SubjectNameDTO subjectNameDTO) {
        SubjectName subjectName = DtoToEntityConverter.subjectNameToEntity(subjectNameDTO);
        subjectName = disciplineService.saveSubjectName(subjectName);
        log.info("Subject name saved");
        return EntityToDtoConverter.subjectNameToDto(subjectName);
    }

    public List<ReportingFormDTO> getReportingFormList() {
        List<ReportingForm> reportingFormList = disciplineService.getReportingFormList();
        List<ReportingFormDTO> reportingFormDTOList = new ArrayList<>();
        reportingFormList.forEach(item -> reportingFormDTOList.add(EntityToDtoConverter.reportingFormToDto(item)));
        return reportingFormDTOList;
    }

    public ReportingFormDTO saveReportingForm(ReportingFormDTO reportingFormDTO) {
        ReportingForm reportingForm = DtoToEntityConverter.reportingFormToEntity(reportingFormDTO);
        reportingForm = disciplineService.saveReportingForm(reportingForm);
        return EntityToDtoConverter.reportingFormToDto(reportingForm);
    }

    public List<DisciplineDTO> getDisciplineListByPlan(Long id) {
        List<Discipline> sortedDiscipline = disciplineService.getDisciplineByPlan(id);
        List<DisciplineDTO> disciplineDTOList = new ArrayList<>();
        sortedDiscipline.forEach(item -> disciplineDTOList.add(EntityToDtoConverter.disciplineToDto(item)));
        return disciplineDTOList;
    }

}
