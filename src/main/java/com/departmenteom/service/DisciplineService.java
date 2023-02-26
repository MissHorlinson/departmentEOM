package com.departmenteom.service;

import com.departmenteom.dto.DisciplineDTO;
import com.departmenteom.entity.*;
import com.departmenteom.entity.dictionary.*;
import com.departmenteom.repo.*;
import com.departmenteom.repo.dictionary.*;
import com.departmenteom.util.EntityToDtoConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DisciplineService {
    private final DisciplineRepo disciplineRepo;
    private final IndependentHoursRepo independentHoursRepo;
    private final DisciplineFormRepo disciplineFormRepo;
    private final DisciplineTypeRepo disciplineTypeRepo;
    private final PersonalTaskFormRepo personalTaskFormRepo;
    private final SubjectNameRepo subjectNameRepo;
    private final AuditoryHoursRepo auditoryHoursRepo;
    private final PersonalTaskRepo personalTaskRepo;
    private final ReportingFormRepo reportingFormRepo;
    private final ReportingRepo reportingRepo;
    private final UtilDictionaryService utilDictionaryService;

    public List<DisciplineForm> getDisciplineFormList() {
        return disciplineFormRepo.findAll();
    }

    public DisciplineForm getDisciplineFormById(Long id) {
        Optional<DisciplineForm> disciplineFormOpt = disciplineFormRepo.findById(id);

        return disciplineFormOpt.orElse(null);
    }

    public List<DisciplineType> getDisciplineTypeList() {
        return disciplineTypeRepo.findAll();
    }

    public DisciplineType getDisciplineTypeById(Long id) {
        Optional<DisciplineType> disciplineTypeOpt = disciplineTypeRepo.findById(id);

        return disciplineTypeOpt.orElse(null);
    }
    public List<PersonalTaskForm> getPersonalTaskFormList() {
        return personalTaskFormRepo.findAll();
    }

    public PersonalTaskForm getPersonalTaskFormById(Long id) {
        Optional<PersonalTaskForm> personalTaskFormOpt = personalTaskFormRepo.findById(id);

        return personalTaskFormOpt.orElse(null);
    }

    public PersonalTaskForm getPersonalTaskFormByName(String name) {
        Optional<PersonalTaskForm> personalTaskFormOpt = personalTaskFormRepo.getByName(name);

        return personalTaskFormOpt.orElse(null);
    }

    public List<SubjectName> getSubjectNameList() {
        return subjectNameRepo.findAll().stream()
                .sorted(Comparator.comparing(SubjectName::getName))
                .collect(Collectors.toList());
    }

    public SubjectName getSubjectNameById(Long id) {
        Optional<SubjectName> subjectNameOpt = subjectNameRepo.findById(id);

        return subjectNameOpt.orElse(null);
    }

    public SubjectName getSubjectNameByName(String name) {
        Optional<SubjectName> subjectNameOpt = subjectNameRepo.getByName(name);

        return subjectNameOpt.orElse(null);
    }

    public ReportingForm getReportingById(Long id) {
        Optional<ReportingForm> reportingFromOpt = reportingFormRepo.findById(id);

        return reportingFromOpt.orElse(null);
    }

    public Set<AuditoryHours> saveAuditoryHours(Set<AuditoryHours> auditoryHours) {
        Set<AuditoryHours> savedAuditoryHours = new HashSet<>();
        auditoryHours.forEach(item -> {
            log.info(item.toString());
            savedAuditoryHours.add(auditoryHoursRepo.save(item));
        });

        return savedAuditoryHours;
    }

    public Set<PersonalTasks> savePersonalTasks(Set<PersonalTasks> personalTasks) {
        Set<PersonalTasks> savedPersonalTask = new HashSet<>();
        personalTasks.forEach(item -> {
            log.info(item.toString());
            savedPersonalTask.add(personalTaskRepo.save(item));
        });

        return savedPersonalTask;
    }

    public IndependentHours saveIndependentHours(IndependentHours independentHours) {
        log.info(independentHours.toString());
//        Optional<IndependentHours> independentHoursOpt = independentHoursRepo.getIndependentHoursByDisciplineIndependentHoursId(independentHours.getDisciplineIndependentHours().getId());
//        if (independentHoursOpt.isPresent()) {
//            // TODO update logic
//            log.info("Independent hours for discipline is exist. It will be updated");
//        } else {
        independentHours = independentHoursRepo.save(independentHours);
//        }
        return independentHours;
    }

    public Reporting saveReporting(Reporting reporting) {
        return reportingRepo.save(reporting);
    }

    public List<ReportingForm> getReportingFormList() {
        return reportingFormRepo.findAll();
    }

    public SubjectName saveSubjectName(SubjectName subjectName) {
        return subjectNameRepo.save(subjectName);
    }

    public DisciplineForm saveDisciplineForm(DisciplineForm disciplineForm) {
        return disciplineFormRepo.save(disciplineForm);
    }

    public DisciplineType saveDisciplineType(DisciplineType disciplineType) {
        return disciplineTypeRepo.save(disciplineType);
    }

    public PersonalTaskForm savePersonalTaskForm(PersonalTaskForm personalTaskForm) {
        return personalTaskFormRepo.save(personalTaskForm);
    }

    public ReportingForm saveReportingForm(ReportingForm reportingForm) {
        return reportingFormRepo.save(reportingForm);
    }

    public Discipline getDisciplineById(Long id) {
        Optional<Discipline> disciplineOpt = disciplineRepo.findById(id);

        if (disciplineOpt.isPresent()) {
            return disciplineOpt.get();
        } else {
            return null;
        }
    }

    @Transactional
    public List<Discipline> getDisciplineByPlan(Long id) {
        List<Discipline> disciplineList = disciplineRepo.getDisciplinesByDisciplinePlanInfoId(id);

        IndependentHours independentHours = new IndependentHours();
        independentHours.setId(0L);
        independentHours.setHoursNum(0);

        disciplineList.forEach(item -> {
            Optional<IndependentHours> independentHoursOpt = independentHoursRepo.getIndependentHoursByDisciplineIndependentHoursId(item.getId());
            if (independentHoursOpt.isPresent()) {
                item.setIndependentHours(independentHoursOpt.get());
            } else {
                item.setIndependentHours(null);
            }
        });

        disciplineList.forEach(item -> {
                    Hibernate.initialize(item.getAuditoryHoursSet());
                    Hibernate.initialize(item.getPersonalTasksSet());
                }
        );

        return disciplineList.stream()
                .sorted(Comparator.comparing((Discipline discipline) -> discipline.getDisciplineType().getId())
                        .thenComparingInt(Discipline::getDisciplineNum)
                        .thenComparingInt(Discipline::getDisciplineSubNum)
                ).collect(Collectors.toList());
    }

    @Transactional
    public List<Discipline> getDisciplineByPlanAndCourse(Long planId, int course) {
        int semF = course * 2 - 1;
        int semS = course * 2;
        List<Discipline> disciplineList = disciplineRepo.getDisciplinesByDisciplinePlanInfoIdAndSemesterGreaterThanEqualAndSemesterLessThanEqual(planId, semF, semS);

        disciplineList.forEach(item -> {
            Optional<IndependentHours> independentHoursOpt = independentHoursRepo.getIndependentHoursByDisciplineIndependentHoursId(item.getId());
            if (independentHoursOpt.isPresent()) {
                item.setIndependentHours(independentHoursOpt.get());
            } else {
                item.setIndependentHours(null);
            }
        });

        disciplineList.forEach(item -> {
                    Hibernate.initialize(item.getAuditoryHoursSet());
                    Hibernate.initialize(item.getPersonalTasksSet());
                }
        );

        return disciplineList.stream()
                .sorted(Comparator.comparing((Discipline discipline) -> discipline.getDisciplineType().getId())
                        .thenComparingInt(Discipline::getDisciplineNum)
                        .thenComparingInt(Discipline::getDisciplineSubNum)
                ).collect(Collectors.toList());
    }

    public DisciplineDTO saveFullData(FullDisciplineData fullDisciplineData) {
        Discipline discipline;
        Set<AuditoryHours> auditoryHoursList;
        IndependentHours independentHours;
        Set<PersonalTasks> personalTasksList;
        if (fullDisciplineData.getId() != null) {
            log.info("need to update");
            discipline = updateDiscipline(fullDisciplineData);

            if (fullDisciplineData.getAuditoryHoursList() != null) {
                auditoryHoursList = updateAuditoryHours(fullDisciplineData.getAuditoryHoursList(), discipline);
                discipline.setAuditoryHoursSet(auditoryHoursList);
            }

            if (fullDisciplineData.getIndependentHours() != null) {
                independentHours = updateIndependentHours(fullDisciplineData.getIndependentHours(), discipline);
                discipline.setIndependentHours(independentHours);
            }

            if (fullDisciplineData.getPersonalTaskList() != null) {
                personalTasksList = updatePersonalTasks(fullDisciplineData.getPersonalTaskList(), discipline);
                discipline.setPersonalTasksSet(personalTasksList);
            }
//            if (fullDisciplineData.getReporting() != null) {
//                Reporting reporting = saveReporting(fullDisciplineData.getReporting(), discipline);
//                discipline.setReporting(reporting);
//            }
        } else {
            log.info("save new");
            discipline = saveDiscipline(fullDisciplineData);

            if (fullDisciplineData.getAuditoryHoursList() != null) {
                auditoryHoursList = saveAuditoryHours(fullDisciplineData.getAuditoryHoursList(), discipline);
                discipline.setAuditoryHoursSet(auditoryHoursList);
            }

            if (fullDisciplineData.getIndependentHours() != null) {
                independentHours = saveIndependentHours(fullDisciplineData.getIndependentHours(), discipline);
                discipline.setIndependentHours(independentHours);
            }

            if (fullDisciplineData.getPersonalTaskList() != null) {
                personalTasksList = savePersonalTasks(fullDisciplineData.getPersonalTaskList(), discipline);
                discipline.setPersonalTasksSet(personalTasksList);
            }

            if (fullDisciplineData.getReporting() != null) {
                Reporting reporting = saveReporting(fullDisciplineData.getReporting(), discipline);
                discipline.setReporting(reporting);
            }
        }

        return EntityToDtoConverter.disciplineToDto(discipline);
    }

    private Discipline updateDiscipline(FullDisciplineData fullDisciplineData) {

        if (disciplineRepo.findById(fullDisciplineData.getId()).isPresent()) {
            SubjectName subjectName = getSubjectNameById(fullDisciplineData.getSubjectName().getId());
            Department department = utilDictionaryService.getDepartmentById(fullDisciplineData.getDepartment().getId());

            Discipline disciplineOpt = disciplineRepo.findById(fullDisciplineData.getId()).get();
            disciplineOpt.setDisciplinePlanInfo(fullDisciplineData.getPlan());
            disciplineOpt.setSemester(fullDisciplineData.getSemester());
            disciplineOpt.setDisciplineType(fullDisciplineData.getDisciplineType());
            disciplineOpt.setCipher(fullDisciplineData.getCipher());
            disciplineOpt.setDisciplineNum(fullDisciplineData.getDisciplineNum());
            disciplineOpt.setDisciplineSubNum(fullDisciplineData.getDisciplineSubNum());
            disciplineOpt.setSubjectName(subjectName);
            disciplineOpt.setDepartment(department);
            disciplineOpt = disciplineRepo.save(disciplineOpt);
            return disciplineOpt;
        }
        return null;
    }

    private Discipline saveDiscipline(FullDisciplineData fullDisciplineData) {
        SubjectName subjectName = getSubjectNameById(fullDisciplineData.getSubjectName().getId());
        Department department = utilDictionaryService.getDepartmentById(fullDisciplineData.getDepartment().getId());

        Discipline discipline = new Discipline();
        discipline.setDisciplinePlanInfo(fullDisciplineData.getPlan());
        discipline.setSemester(fullDisciplineData.getSemester());
        discipline.setDisciplineType(fullDisciplineData.getDisciplineType());
        discipline.setCipher(fullDisciplineData.getCipher());
        discipline.setDisciplineNum(fullDisciplineData.getDisciplineNum());
        discipline.setDisciplineSubNum(fullDisciplineData.getDisciplineSubNum());
        discipline.setSubjectName(subjectName);
        discipline.setDepartment(department);
        discipline = disciplineRepo.save(discipline);

        return discipline;
    }

    private Set<AuditoryHours> saveAuditoryHours(List<AuditoryHours> auditoryHours, Discipline finalDiscipline) {
        Set<AuditoryHours> auditoryHoursList = new HashSet<>();
        auditoryHours.forEach(item -> {
            DisciplineForm form = getDisciplineFormById(item.getDisciplineForm().getId());
            auditoryHoursList.add(new AuditoryHours(item.getId(), finalDiscipline, form, item.getHoursNum()));
        });
        return saveAuditoryHours(auditoryHoursList);
    }

    private Set<AuditoryHours> updateAuditoryHours(List<AuditoryHours> auditoryHours, Discipline discipline) {
        Set<AuditoryHours> auditoryHoursList = new HashSet<>();
        auditoryHours.forEach(item -> {
            DisciplineForm form = getDisciplineFormById(item.getDisciplineForm().getId());
            item.setDisciplineForm(form);
            item.setDisciplineAuditoryHours(discipline);
            auditoryHoursList.add(item);
        });
        return saveAuditoryHours(auditoryHoursList);
    }

    private Set<PersonalTasks> savePersonalTasks(List<PersonalTasks> personalTasks, Discipline finalDiscipline) {
        Set<PersonalTasks> personalTasksList = new HashSet<>();
        personalTasks.forEach(item -> {
            PersonalTaskForm form = getPersonalTaskFormById(item.getPersonalTaskForm().getId());
            personalTasksList.add(new PersonalTasks(finalDiscipline, form));
        });
        return savePersonalTasks(personalTasksList);
    }

    private Set<PersonalTasks> updatePersonalTasks(List<PersonalTasks> personalTasks, Discipline discipline) {
        Set<PersonalTasks> personalTasksList = new HashSet<>();
        personalTasks.forEach(item -> {
            PersonalTaskForm form = getPersonalTaskFormById(item.getPersonalTaskForm().getId());
            item.setPersonalTaskForm(form);
            item.setDisciplinePersonalTask(discipline);
            personalTasksList.add(item);
        });
        return savePersonalTasks(personalTasksList);
    }

    private IndependentHours saveIndependentHours(IndependentHours independentHoursFull, Discipline finalDiscipline) {
        IndependentHours independentHours = new IndependentHours();
        independentHours.setDisciplineIndependentHours(finalDiscipline);
        independentHours.setHoursNum(independentHoursFull.getHoursNum());
        return saveIndependentHours(independentHours);
    }

    private IndependentHours updateIndependentHours(IndependentHours independentHoursFull, Discipline finalDiscipline) {
        independentHoursFull.setDisciplineIndependentHours(finalDiscipline);
        return saveIndependentHours(independentHoursFull);
    }

    private Reporting saveReporting(Reporting reportingForSave, Discipline finalDiscipline) {
        Reporting reporting = reportingForSave;
        ReportingForm form = getReportingById(reporting.getDisciplineReportingForm().getId());
        reporting.setDisciplineReporting(finalDiscipline);
        reporting.setDisciplineReportingForm(form);
        saveReporting(reporting);

        return reporting;
    }
}
