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
        String replacedName = name.replace("'", "`");
        Optional<SubjectName> subjectNameOpt = subjectNameRepo.getByName(replacedName);
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
        return disciplineOpt.orElse(null);
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
            log.info("Discipline existing - need to update data");
            discipline = updateDiscipline(fullDisciplineData);

            if (fullDisciplineData.getAuditoryHoursList() != null) {
                log.info("Update auditory hours data");
                auditoryHoursList = updateAuditoryHours(fullDisciplineData.getAuditoryHoursList(), discipline);
                discipline.setAuditoryHoursSet(auditoryHoursList);
                log.info("Updated");
            }

            if (fullDisciplineData.getAuditoryHoursToRemove() != null && fullDisciplineData.getAuditoryHoursToRemove().size() > 0) {
                log.info("Remove auditory hours");
                fullDisciplineData.getAuditoryHoursToRemove().forEach(item -> auditoryHoursRepo.deleteById(item.getId()));
            }

            if (fullDisciplineData.getIndependentHours() != null) {
                log.info("Update independent hours data");
                independentHours = updateIndependentHours(fullDisciplineData.getIndependentHours(), discipline);
                discipline.setIndependentHours(independentHours);
                log.info("Updated");
            }

            if (fullDisciplineData.getPersonalTaskList() != null) {
                log.info("Update personal task data");
                personalTasksList = updatePersonalTasks(fullDisciplineData.getPersonalTaskList(), discipline);
                discipline.setPersonalTasksSet(personalTasksList);
                log.info("Updated");
            }

            if (fullDisciplineData.getReporting() != null) {
                Reporting reporting = fullDisciplineData.getReporting();
                if (reporting.getDisciplineReportingForm().getId() == null) {
                    log.info("Remove reporting data");
                    reportingRepo.deleteById(reporting.getId());
                    discipline.setReporting(null);
                } else {
                    log.info("Update reporting data");
                    reporting = updateReporting(fullDisciplineData.getReporting(), discipline);
                    discipline.setReporting(reporting);
                }
                log.info("Updated");
            }
        } else {
            log.info("Discipline not existing - save new data");
            discipline = saveDiscipline(fullDisciplineData);

            if (fullDisciplineData.getAuditoryHoursList() != null) {
                log.info("Create auditory hours data");
                auditoryHoursList = saveAuditoryHours(fullDisciplineData.getAuditoryHoursList(), discipline);
                discipline.setAuditoryHoursSet(auditoryHoursList);
                log.info("Saved");
            }

            if (fullDisciplineData.getIndependentHours() != null) {
                log.info("Create independent hours data");
                independentHours = saveIndependentHours(fullDisciplineData.getIndependentHours(), discipline);
                discipline.setIndependentHours(independentHours);
                log.info("Saved");
            }

            if (fullDisciplineData.getPersonalTaskList() != null) {
                log.info("Create personal tasks data");
                personalTasksList = savePersonalTasks(fullDisciplineData.getPersonalTaskList(), discipline);
                discipline.setPersonalTasksSet(personalTasksList);
                log.info("Saved");
            }

            if (fullDisciplineData.getReporting() != null) {
                log.info("Create reporting data");
                Reporting reporting = saveReporting(fullDisciplineData.getReporting(), discipline);
                discipline.setReporting(reporting);
                log.info("Saved");
            }
        }
        DisciplineType type = getDisciplineTypeById(fullDisciplineData.getDisciplineType().getId());
        discipline.setDisciplineType(type);

        log.info("Discipline data saved");
        return EntityToDtoConverter.disciplineToDto(discipline);
    }

    private Discipline saveDiscipline(FullDisciplineData fullDisciplineData) {
        SubjectName subjectName = getSubjectNameById(fullDisciplineData.getSubjectName().getId());
        Department department = utilDictionaryService.getDepartmentById(fullDisciplineData.getDepartment().getId());

        Discipline discipline = new Discipline();
        discipline.setDisciplinePlanInfo(fullDisciplineData.getPlan());
        discipline.setSemester(fullDisciplineData.getSemester());
        discipline.setDisciplineType(fullDisciplineData.getDisciplineType());
        discipline.setDisciplineNum(fullDisciplineData.getDisciplineNum());
        discipline.setDisciplineSubNum(fullDisciplineData.getDisciplineSubNum());
        discipline.setSubjectName(subjectName);
        discipline.setDepartment(department);
        discipline.setSubgroupNum(fullDisciplineData.getSubgroupNum());
        discipline = disciplineRepo.save(discipline);
        log.info("Discipline data saved");
        return discipline;
    }

    private Discipline updateDiscipline(FullDisciplineData fullDisciplineData) {
        if (disciplineRepo.findById(fullDisciplineData.getId()).isPresent()) {
            SubjectName subjectName = getSubjectNameById(fullDisciplineData.getSubjectName().getId());
            Department department = utilDictionaryService.getDepartmentById(fullDisciplineData.getDepartment().getId());

            Discipline disciplineOpt = disciplineRepo.findById(fullDisciplineData.getId()).get();
            disciplineOpt.setDisciplinePlanInfo(fullDisciplineData.getPlan());
            disciplineOpt.setSemester(fullDisciplineData.getSemester());
            disciplineOpt.setDisciplineType(fullDisciplineData.getDisciplineType());
            disciplineOpt.setDisciplineNum(fullDisciplineData.getDisciplineNum());
            disciplineOpt.setDisciplineSubNum(fullDisciplineData.getDisciplineSubNum());
            disciplineOpt.setSubjectName(subjectName);
            disciplineOpt.setDepartment(department);
            disciplineOpt = disciplineRepo.save(disciplineOpt);
            log.info("Discipline data updated");
            return disciplineOpt;
        }
        throw new IllegalArgumentException("Discipline with id " + fullDisciplineData.getId() + " does not exist in DB");
    }

    private Set<AuditoryHours> saveAuditoryHours(List<AuditoryHours> auditoryHours, Discipline finalDiscipline) {
        Set<AuditoryHours> auditoryHoursList = new HashSet<>();
        auditoryHours.forEach(item -> {
            if (item.getDisciplineForm() != null) {
                DisciplineForm form = getDisciplineFormById(item.getDisciplineForm().getId());
                auditoryHoursList.add(new AuditoryHours(item.getId(), finalDiscipline, form, item.getHoursNum()));
            } else
                log.warn("Can not save auditoryHours witch doesnt contain discipline or disciplineForm data");
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
        independentHours = independentHoursRepo.save(independentHours);
        return independentHours;
    }

    private IndependentHours updateIndependentHours(IndependentHours independentHoursFull, Discipline finalDiscipline) {
        independentHoursFull.setDisciplineIndependentHours(finalDiscipline);
        independentHoursFull = independentHoursRepo.save(independentHoursFull);
        return independentHoursFull;
    }

    private Reporting saveReporting(Reporting reportingForSave, Discipline finalDiscipline) {
        Reporting reporting = reportingForSave;
        ReportingForm form = getReportingById(reporting.getDisciplineReportingForm().getId());
        reporting.setDisciplineReporting(finalDiscipline);
        reporting.setDisciplineReportingForm(form);
        saveReporting(reporting);
        return reporting;
    }

    private Reporting updateReporting(Reporting reportingForSave, Discipline finalDiscipline) {
        Reporting reporting = reportingForSave;
        ReportingForm form = getReportingById(reporting.getDisciplineReportingForm().getId());
        reporting.setDisciplineReportingForm(form);
        reporting.setDisciplineReporting(finalDiscipline);
        saveReporting(reporting);
        return reporting;
    }

}
