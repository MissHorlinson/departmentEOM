package com.departmenteom.service;

import com.departmenteom.entity.dictionary.*;
import com.departmenteom.repo.dictionary.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UtilDictionaryService {

    private final AcademicRankRepo academicRankRepo;
    private final BaseRepo baseRepo;
    private final CipherRepo cipherRepo;
    private final DegreeRepo degreeRepo;
    private final DepartmentRepo departmentRepo;
    private final PositionRepo positionRepo;
    private final QualificationRepo qualificationRepo;
    private final StepRepo stepRepo;
    private final StudyingFromRepo studyingFromRepo;
    private final StudyingTermRepo studyingTermRepo;
    private final StudyingTypeRepo studyingTypeRepo;


    public List<AcademicRank> getRankList() {
        return academicRankRepo.findAll();
    }

    public AcademicRank saveRank(AcademicRank academicRank) {
        return academicRankRepo.save(academicRank);
    }

    public List<Base> getBaseList() {
        return baseRepo.findAll();
    }

    public Base getBaseById(Long id) {
        Optional<Base> baseOpt = baseRepo.findById(id);
        return baseOpt.orElse(null);
    }

    public Base getBaseByName(String name) {
        Optional<Base> baseOpt = baseRepo.getByName(name);
        return baseOpt.orElse(null);
    }


    public Base saveBase(Base base) {
        return baseRepo.save(base);
    }

    public List<Cipher> getCipherList() {
        return cipherRepo.findAll();
    }

    public Cipher getCipherById(Long id) {
        Optional<Cipher> cipherOpt = cipherRepo.findById(id);
        return cipherOpt.orElse(null);
    }

    public Cipher saveCipher(Cipher cipher) {
        return cipherRepo.save(cipher);
    }

    public List<Degree> getDegreeList() {
        return degreeRepo.findAll();
    }

    public Degree saveDegree(Degree degree) {
        return degreeRepo.save(degree);
    }

    public List<Department> getDepartmentList() {
        return departmentRepo.findAll();
    }

    public Department getDepartmentById(Long id) {
        Optional<Department> departmentOpt = departmentRepo.findById(id);

        return departmentOpt.orElse(null);
    }

    public Department getDepartmentByName(String departmentName) {
        Optional<Department> departmentOpt = departmentRepo.getByName(departmentName);

        return departmentOpt.orElse(null);
    }

    public Department saveDepartment(Department department) {
        return departmentRepo.save(department);
    }

    public List<Position> getPositionList() {
        return positionRepo.findAll();
    }

    public Position savePosition(Position position) {
        return positionRepo.save(position);
    }

    public List<Qualification> getQualificationList() {
        return qualificationRepo.findAll();
    }

    public Qualification getQualificationById(Long id) {
        Optional<Qualification> qualificationOpt = qualificationRepo.findById(id);
        return qualificationOpt.orElse(null);
    }

    public Qualification getQualificationByName(String name) {
        Optional<Qualification> qualificationOpt = qualificationRepo.getByName(name);
        return qualificationOpt.orElse(null);
    }


    public Qualification saveQualification(Qualification qualification) {
        return qualificationRepo.save(qualification);
    }

    public List<Step> getStepList() {
        return stepRepo.findAll();
    }

    public Step getStepById(Long id) {
        Optional<Step> stepOpt = stepRepo.findById(id);
        return stepOpt.orElse(null);
    }

    public Step getStepByName(String name) {
        Optional<Step> stepOpt = stepRepo.getByName(name);
        return stepOpt.orElse(null);
    }

    public Step saveStep(Step step) {
        return stepRepo.save(step);
    }

    public List<StudyingForm> getStudyingFormList() {
        return studyingFromRepo.findAll();
    }

    public StudyingForm getStudyingFormById(Long id) {
        Optional<StudyingForm> studyingFormOpt = studyingFromRepo.findById(id);
        return studyingFormOpt.orElse(null);
    }

    public StudyingForm getStudyingFormByName(String name) {
        Optional<StudyingForm> studyingFormOpt = studyingFromRepo.getByName(name);
        return studyingFormOpt.orElse(null);
    }

    public StudyingForm saveStudyingForm(StudyingForm studyingForm) {
        return studyingFromRepo.save(studyingForm);
    }

    public List<StudyingTerm> getStudyingTermList() {
        return studyingTermRepo.findAll();
    }

    public StudyingTerm getStudyingTermById(Long id) {
        Optional<StudyingTerm> studyingTermOpt = studyingTermRepo.findById(id);
        return studyingTermOpt.orElse(null);
    }

    public StudyingTerm getStudyingTermByName(String name) {
        Optional<StudyingTerm> studyingTermOpt = studyingTermRepo.getByName(name);
        return studyingTermOpt.orElse(null);
    }

    public StudyingTerm saveStudyingTerm(StudyingTerm studyingTerm) {
        return studyingTermRepo.save(studyingTerm);
    }

    public List<StudyingType> getStudyingTypeList() {
        return studyingTypeRepo.findAll();
    }

    public StudyingType getStudyingTypeById(Long id) {
        Optional<StudyingType> studyingTypeOpt = studyingTypeRepo.findById(id);

        return studyingTypeOpt.orElse(null);
    }

    public StudyingType getStudyingTypeByLetter(String letter) {
        Optional<StudyingType> studyingTypeOpt = studyingTypeRepo.getByLetter(letter);

        return studyingTypeOpt.orElse(null);
    }

    public StudyingType saveStudyingType(StudyingType studyingType) {
        return studyingTypeRepo.save(studyingType);
    }

}
