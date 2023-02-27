package com.departmenteom.web;

import com.departmenteom.dto.DisciplineDTO;
import com.departmenteom.dto.dictionary.*;
import com.departmenteom.entity.FullDisciplineData;
import com.departmenteom.service.DisciplineService;
import com.departmenteom.typemapper.DisciplineMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/discipline")
@RequiredArgsConstructor
@Slf4j
public class DisciplineController {

    private final DisciplineService disciplineService;

    private final DisciplineMapper disciplineMapper;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('depart:read')")
    public DisciplineDTO getById(@PathVariable Long id) {
        return disciplineMapper.getById(id);
    }

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('depart:write')")
    public DisciplineDTO saveFullDiscipline(@RequestBody FullDisciplineData fullDisciplineData) {
       return disciplineService.saveFullData(fullDisciplineData);
    }

    @GetMapping("/disciplineForm/all")
    @PreAuthorize("hasAnyAuthority('depart:read')")
    public List<DisciplineFormDTO> getDisciplineFormList() {
        return disciplineMapper.getDisciplineFormList();
    }

    @PostMapping("/disciplineForm/save")
    @PreAuthorize("hasAnyAuthority('depart:write')")
    public DisciplineFormDTO saveDisciplineForm(@RequestBody DisciplineFormDTO disciplineForm) {
        return disciplineMapper.saveDisciplineForm(disciplineForm);
    }

    @GetMapping("/disciplineType/all")
    @PreAuthorize("hasAnyAuthority('depart:read')")
    public List<DisciplineTypeDTO> getDisciplineTypeList() {
        return disciplineMapper.getDisciplineTypeList();
    }

    @PostMapping("/disciplineType/save")
    @PreAuthorize("hasAnyAuthority('depart:write')")
    public DisciplineTypeDTO saveDisciplineType(@RequestBody DisciplineTypeDTO disciplineType) {
        return disciplineMapper.saveDisciplineType(disciplineType);
    }

    @GetMapping("/personalTaskForm/all")
    @PreAuthorize("hasAnyAuthority('depart:read')")
    public List<PersonalTaskFormDTO> getPersonalTaskFormList() {
        return disciplineMapper.getPersonalTaskFormList();
    }

    @PostMapping("/personalTaskForm/save")
    @PreAuthorize("hasAnyAuthority('depart:write')")
    public PersonalTaskFormDTO savePersonalTaskForm(@RequestBody PersonalTaskFormDTO personalTaskForm) {
        return disciplineMapper.savePersonalTaskForm(personalTaskForm);
    }

    @GetMapping("/subjectName/all")
    @PreAuthorize("hasAnyAuthority('depart:read')")
    public List<SubjectNameDTO> getSubjectNameList() {
        return disciplineMapper.getSubjectNameList();
    }

    @PostMapping("/subjectName/save")
    @PreAuthorize("hasAnyAuthority('depart:write')")
    public SubjectNameDTO saveSubjectName(@RequestBody SubjectNameDTO subjectName) {
        return disciplineMapper.saveSubjectName(subjectName);
    }

    @GetMapping("/reportingForm/all")
    @PreAuthorize("hasAnyAuthority('depart:read')")
    public List<ReportingFormDTO> getReportingFormList() {
        return disciplineMapper.getReportingFormList();
    }

    @PostMapping("/reportingForm/save")
    @PreAuthorize("hasAnyAuthority('depart:write')")
    public ReportingFormDTO saveReportingForm(@RequestBody ReportingFormDTO reportingForm) {
        return disciplineMapper.saveReportingForm(reportingForm);
    }

    @GetMapping("/getByPlan/{id}")
    @PreAuthorize("hasAnyAuthority('depart:read')")
    public List<DisciplineDTO> getDisciplineListByPlan(@PathVariable Long id) {
        return disciplineMapper.getDisciplineListByPlan(id);
    }
}
