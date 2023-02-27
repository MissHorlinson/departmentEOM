package com.departmenteom.typemapper;

import com.departmenteom.dto.dictionary.*;
import com.departmenteom.entity.dictionary.*;
import com.departmenteom.service.UtilDictionaryService;
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
public class UtilDictionaryMapper {
    private final UtilDictionaryService utilDictionaryService;

    public List<AcademicRankDTO> getRankList() {
        List<AcademicRank> academicRanksList = utilDictionaryService.getRankList();
        List<AcademicRankDTO> academicRankDTOList = new ArrayList<>();
        academicRanksList.forEach(item -> academicRankDTOList.add(EntityToDtoConverter.academicRankToDto(item)));
        return academicRankDTOList;
    }

    public AcademicRankDTO saveRank(AcademicRankDTO academicRankDTO) {
        AcademicRank academicRank = DtoToEntityConverter.academicRankToEntity(academicRankDTO);
        return EntityToDtoConverter.academicRankToDto(utilDictionaryService.saveRank(academicRank));
    }

    public List<BaseDTO> getBaseList() {
        List<Base> baseList = utilDictionaryService.getBaseList();
        List<BaseDTO> baseDTOList = new ArrayList<>();
        baseList.forEach(item -> baseDTOList.add(EntityToDtoConverter.baseToDto(item)));
        return baseDTOList;
    }

    public BaseDTO saveBase(BaseDTO baseDTO) {
        Base base = DtoToEntityConverter.baseToEntity(baseDTO);
        return EntityToDtoConverter.baseToDto(utilDictionaryService.saveBase(base));
    }

    public List<CipherDTO> getCipherList() {
        List<Cipher> cipherList = utilDictionaryService.getCipherList();
        List<CipherDTO> cipherDTOList = new ArrayList<>();
        cipherList.forEach(item -> cipherDTOList.add(EntityToDtoConverter.cipherToDto(item)));
        return cipherDTOList;
    }

    public CipherDTO saveCipher(CipherDTO cipherDTO) {
        Cipher cipher = DtoToEntityConverter.cipherToEntity(cipherDTO);
        return EntityToDtoConverter.cipherToDto(utilDictionaryService.saveCipher(cipher));
    }

    public List<DegreeDTO> getDegreeList() {
        List<Degree> degreeList = utilDictionaryService.getDegreeList();
        List<DegreeDTO> degreeDTOList = new ArrayList<>();
        degreeList.forEach(item -> degreeDTOList.add(EntityToDtoConverter.degreeToDto(item)));
        return degreeDTOList;
    }

    public DegreeDTO saveDegree(DegreeDTO degreeDTO) {
        Degree degree = DtoToEntityConverter.degreeToEntity(degreeDTO);
        return EntityToDtoConverter.degreeToDto(utilDictionaryService.saveDegree(degree));
    }

    public List<DepartmentDTO> getDepartmentList() {
        List<Department> departmentList = utilDictionaryService.getDepartmentList();
        List<DepartmentDTO> departmentDTOList = new ArrayList<>();
        departmentList.forEach(item -> departmentDTOList.add(EntityToDtoConverter.departmentToDto(item)));
        return departmentDTOList;
    }

    public DepartmentDTO saveDepartment(DepartmentDTO departmentDTO) {
        Department department = DtoToEntityConverter.departmentToEntity(departmentDTO);
        return EntityToDtoConverter.departmentToDto(utilDictionaryService.saveDepartment(department));
    }

    public DepartmentDTO getDepartmentById(Long id) {
        return EntityToDtoConverter.departmentToDto(utilDictionaryService.getDepartmentById(id));
    }

    public List<PositionDTO> getPositionList() {
        List<Position> positionList = utilDictionaryService.getPositionList();
        List<PositionDTO> positionDTOList = new ArrayList<>();
        positionList.forEach(item -> positionDTOList.add(EntityToDtoConverter.positionToDto(item)));
        return positionDTOList;
    }

    public PositionDTO savePosition(PositionDTO positionDTO) {
        Position position = DtoToEntityConverter.positionToEntity(positionDTO);
        return EntityToDtoConverter.positionToDto(utilDictionaryService.savePosition(position));
    }

    public List<QualificationDTO> getQualificationList() {
        List<Qualification> qualificationList = utilDictionaryService.getQualificationList();
        List<QualificationDTO> qualificationDTOList = new ArrayList<>();
        qualificationList.forEach(item -> qualificationDTOList.add(EntityToDtoConverter.qualificationToDto(item)));
        return qualificationDTOList;
    }

    public QualificationDTO saveQualification(QualificationDTO qualificationDTO) {
        Qualification qualification = DtoToEntityConverter.qualificationToEntity(qualificationDTO);
        return EntityToDtoConverter.qualificationToDto(utilDictionaryService.saveQualification(qualification));
    }

    public List<StepDTO> getStepList() {
        List<Step> stepList = utilDictionaryService.getStepList();
        List<StepDTO> stepDTOList = new ArrayList<>();
        stepList.forEach(item -> stepDTOList.add(EntityToDtoConverter.stepToDto(item)));
        return stepDTOList;
    }

    public StepDTO saveStep(StepDTO stepDTO) {
        Step step = DtoToEntityConverter.stepToEntity(stepDTO);
        return EntityToDtoConverter.stepToDto(utilDictionaryService.saveStep(step));
    }

    public List<StudyingFormDTO> getStudyingFormList() {
        List<StudyingForm> studyingFormList = utilDictionaryService.getStudyingFormList();
        List<StudyingFormDTO> studyingFormDTOList = new ArrayList<>();
        studyingFormList.forEach(item -> studyingFormDTOList.add(EntityToDtoConverter.studyingFormToDto(item)));
        return studyingFormDTOList;
    }

    public StudyingFormDTO saveStudyingForm(StudyingFormDTO studyingFormDTO) {
        StudyingForm studyingForm = DtoToEntityConverter.studyingFormToEntity(studyingFormDTO);
        return EntityToDtoConverter.studyingFormToDto(utilDictionaryService.saveStudyingForm(studyingForm));
    }

    public List<StudyingTermDTO> getStudyingTermList() {
        List<StudyingTerm> studyingTermList = utilDictionaryService.getStudyingTermList();
        List<StudyingTermDTO> studyingTermDTOList = new ArrayList<>();
        studyingTermList.forEach(item -> studyingTermDTOList.add(EntityToDtoConverter.studyingTermToDto(item)));
        return studyingTermDTOList;
    }

    public StudyingTermDTO saveStudyingTerm(StudyingTermDTO studyingTermDTO) {
        StudyingTerm studyingTerm = DtoToEntityConverter.studyingTermToEntity(studyingTermDTO);
        return EntityToDtoConverter.studyingTermToDto(utilDictionaryService.saveStudyingTerm(studyingTerm));
    }

    public StudyingTermDTO getStudyingTermById(Long id) {
        return EntityToDtoConverter.studyingTermToDto(utilDictionaryService.getStudyingTermById(id));
    }

    public List<StudyingTypeDTO> getStudyingTypeList() {
        List<StudyingType> studyingTypeList = utilDictionaryService.getStudyingTypeList();
        List<StudyingTypeDTO> studyingTypeDTOList = new ArrayList<>();
        studyingTypeList.forEach(item -> studyingTypeDTOList.add(EntityToDtoConverter.studyingTypeToDto(item)));
        return studyingTypeDTOList;
    }

    public StudyingTypeDTO saveStudyingType(StudyingTypeDTO studyingTypeDTO) {
        StudyingType studyingType = DtoToEntityConverter.studyingTypeToEntity(studyingTypeDTO);
        return EntityToDtoConverter.studyingTypeToDto(utilDictionaryService.saveStudyingType(studyingType));
    }

    public StudyingTypeDTO getStudyingTypeById(Long id) {
        return EntityToDtoConverter.studyingTypeToDto(utilDictionaryService.getStudyingTypeById(id));
    }
}
