package com.departmenteom.web;

import com.departmenteom.dto.dictionary.*;
import com.departmenteom.typemapper.UtilDictionaryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/util")
@Slf4j
public class UtilDictionaryController {
    private final UtilDictionaryMapper utilDictionaryMapper;

    @GetMapping("/academicRank/all")
    @PreAuthorize("hasAnyAuthority('depart:read')")
    public List<AcademicRankDTO> getRankList() {
        return utilDictionaryMapper.getRankList();
    }

    @PostMapping("/academicRank/save")
    @PreAuthorize("hasAnyAuthority('depart:write')")
    public AcademicRankDTO saveRank(@RequestBody AcademicRankDTO academicRank) {
        return utilDictionaryMapper.saveRank(academicRank);
    }

    @GetMapping("/base/all")
    @PreAuthorize("hasAnyAuthority('depart:read')")
    public List<BaseDTO> getBaseList() {
        return utilDictionaryMapper.getBaseList();
    }

    @PostMapping("/base/save")
    @PreAuthorize("hasAnyAuthority('depart:write')")
    public BaseDTO saveBase(@RequestBody BaseDTO base) {
        return utilDictionaryMapper.saveBase(base);
    }

    @GetMapping("/cipher/all")
    @PreAuthorize("hasAnyAuthority('depart:read')")
    public List<CipherDTO> getCipherList() {
        return utilDictionaryMapper.getCipherList();
    }

    @PostMapping("/cipher/save")
    @PreAuthorize("hasAnyAuthority('depart:write')")
    public CipherDTO saveCipher(@RequestBody CipherDTO cipher) {
        return utilDictionaryMapper.saveCipher(cipher);
    }

    @GetMapping("/degree/all")
    @PreAuthorize("hasAnyAuthority('depart:read')")
    public List<DegreeDTO> getDegreeList() {
        return utilDictionaryMapper.getDegreeList();
    }

    @PostMapping("/degree/save")
    @PreAuthorize("hasAnyAuthority('depart:read')")
    public DegreeDTO saveDegree(@RequestBody DegreeDTO degree) {
        return utilDictionaryMapper.saveDegree(degree);
    }

    @GetMapping("/department/all")
    @PreAuthorize("hasAnyAuthority('depart:read')")
    public List<DepartmentDTO> getDepartmentList() {
        return utilDictionaryMapper.getDepartmentList();
    }

    @PostMapping("/department/save")
    @PreAuthorize("hasAnyAuthority('depart:write')")
    public DepartmentDTO saveDepartment(@RequestBody DepartmentDTO department) {
        return utilDictionaryMapper.saveDepartment(department);
    }

    @GetMapping("/department/getById/{id}")
    @PreAuthorize("hasAnyAuthority('depart:read')")
    public DepartmentDTO getDepartmentById(@PathVariable Long id) {
        return utilDictionaryMapper.getDepartmentById(id);
    }

    @GetMapping("/position/all")
    @PreAuthorize("hasAnyAuthority('depart:read')")
    public List<PositionDTO> getPositionList() {
        return utilDictionaryMapper.getPositionList();
    }

    @PostMapping("/position/save")
    @PreAuthorize("hasAnyAuthority('depart:write')")
    public PositionDTO savePosition(@RequestBody PositionDTO position) {
        return utilDictionaryMapper.savePosition(position);
    }

    @GetMapping("/qualification/all")
    @PreAuthorize("hasAnyAuthority('depart:read')")
    public List<QualificationDTO> getQualificationList() {
        return utilDictionaryMapper.getQualificationList();
    }

    @PostMapping("/qualification/save")
    @PreAuthorize("hasAnyAuthority('depart:write')")
    public QualificationDTO saveQualification(@RequestBody QualificationDTO qualification) {
        return utilDictionaryMapper.saveQualification(qualification);
    }

    @GetMapping("/step/all")
    @PreAuthorize("hasAnyAuthority('depart:read')")
    public List<StepDTO> getStepList() {
        return utilDictionaryMapper.getStepList();
    }

    @PostMapping("/step/save")
    @PreAuthorize("hasAnyAuthority('depart:write')")
    public StepDTO saveQualification(@RequestBody StepDTO step) {
        return utilDictionaryMapper.saveStep(step);
    }

    @GetMapping("/studyingForm/all")
    @PreAuthorize("hasAnyAuthority('depart:read')")
    public List<StudyingFormDTO> getStudyingFormList() {
        return utilDictionaryMapper.getStudyingFormList();
    }

    @PostMapping("/studyingForm/save")
    @PreAuthorize("hasAnyAuthority('depart:write')")
    public StudyingFormDTO saveStudyingForm(@RequestBody StudyingFormDTO studyingForm) {
        return utilDictionaryMapper.saveStudyingForm(studyingForm);
    }

    @GetMapping("/studyingTerm/all")
    @PreAuthorize("hasAnyAuthority('depart:read')")
    public List<StudyingTermDTO> getStudyingTermList() {
        return utilDictionaryMapper.getStudyingTermList();
    }

    @PostMapping("/studyingTerm/save")
    @PreAuthorize("hasAnyAuthority('depart:write')")
    public StudyingTermDTO saveStudyingTerm(@RequestBody StudyingTermDTO studyingTerm) {
        return utilDictionaryMapper.saveStudyingTerm(studyingTerm);
    }


    @GetMapping("/studyingTerm/getById/{id}")
    @PreAuthorize("hasAnyAuthority('depart:read')")
    public StudyingTermDTO getStudyingTermById(@PathVariable Long id) {
        return utilDictionaryMapper.getStudyingTermById(id);
    }


    @GetMapping("/studyingType/all")
    @PreAuthorize("hasAnyAuthority('depart:read')")
    public List<StudyingTypeDTO> getStudyingTypeList() {
        return utilDictionaryMapper.getStudyingTypeList();
    }

    @PostMapping("/studyingType/save")
    @PreAuthorize("hasAnyAuthority('depart:write')")
    public StudyingTypeDTO saveStudyingType(@RequestBody StudyingTypeDTO studyingType) {
        return utilDictionaryMapper.saveStudyingType(studyingType);
    }

    @GetMapping("/studyingType/getById/{id}")
    @PreAuthorize("hasAnyAuthority('depart:read')")
    public StudyingTypeDTO getStudyingTypeById(@PathVariable Long id) {
        return utilDictionaryMapper.getStudyingTypeById(id);
    }
}
