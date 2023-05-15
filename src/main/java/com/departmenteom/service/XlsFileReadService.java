package com.departmenteom.service;

import com.departmenteom.dto.PlanInfoDTO;
import com.departmenteom.entity.*;
import com.departmenteom.entity.dictionary.*;
import com.departmenteom.util.EntityToDtoConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class XlsFileReadService {

    private final UtilDictionaryService utilDictionaryService;
    private final DisciplineService disciplineService;
    private final PlanInfoService planInfoService;

    private final Map<Integer, String> fullPlanLectureCellLetterMap = Map.of(1, "U", 2, "Y", 3, "AC", 4, "AG", 5, "AK", 6, "AO", 7, "AS", 8, "AW");
    private final Map<Integer, String> fullPlanLabCellLetterMap = Map.of(1, "W", 2, "AA", 3, "AE", 4, "AI", 5, "AM", 6, "AQ", 7, "AU", 8, "AY");
    private final Map<Integer, String> fullPlanPracticeCellLetterMap = Map.of(1, "V", 2, "Z", 3, "AD", 4, "AH", 5, "AL", 6, "AP", 7, "AT", 8, "AX");
    private final Map<Integer, String> fullPlanIndependentCellLetterMap = Map.of(1, "X", 2, "AB", 3, "AF", 4, "AJ", 5, "AN", 6, "AR", 7, "AV", 8, "AZ");

    private final int COURSE_WEEK_NUM = 52;
    private final String MAIN_SHEET_NAME = "Титул";
    private final String DISCIPLINE_SHEET_NAME = "План";

    public PlanInfoDTO writeFromFileToSystem(MultipartFile file) {

        try {
            String[] type = file.getOriginalFilename().split("\\.");
            InputStream inputStream = new BufferedInputStream(file.getInputStream());
            Workbook workbook = null;
            if (type[1].equals("xlsx")) {
                workbook = new XSSFWorkbook(inputStream);
                inputStream.close();
            } else if (type[1].equals("xls")) {
                workbook = new HSSFWorkbook(inputStream);
                inputStream.close();
            }

            if (workbook != null) {
                Map<String, Sheet> fileSheet = new HashMap<>();
                Sheet sheet0 = workbook.getSheetAt(0);
                Sheet sheet1 = workbook.getSheetAt(1);

                if (sheet0.getSheetName().toLowerCase().contains(MAIN_SHEET_NAME.toLowerCase()) &&
                        sheet1.getSheetName().toLowerCase().contains(DISCIPLINE_SHEET_NAME.toLowerCase())) {
                    fileSheet.put(MAIN_SHEET_NAME, sheet0);
                    fileSheet.put(DISCIPLINE_SHEET_NAME, sheet1);
                } else if (sheet1.getSheetName().toLowerCase().contains(MAIN_SHEET_NAME.toLowerCase()) &&
                        sheet0.getSheetName().toLowerCase().contains(DISCIPLINE_SHEET_NAME.toLowerCase())) {
                    fileSheet.put(MAIN_SHEET_NAME, sheet1);
                    fileSheet.put(DISCIPLINE_SHEET_NAME, sheet0);
                } else {
                    throw new IllegalArgumentException("Wrong file format");
                }

                    PlanInfo plan = readTitlePage(fileSheet.get(MAIN_SHEET_NAME));
                    readTableData(fileSheet.get(MAIN_SHEET_NAME), plan);
//                    readDisciplinePage(fileSheet.get(DISCIPLINE_SHEET_NAME), plan);
                    return EntityToDtoConverter.planInfoToDto(plan);
            }
        } catch (IOException ex) {
            log.error("Error: ", ex);
        }
        throw new IllegalArgumentException("Can not read file");
    }

    private void readDisciplinePage(Sheet disciplineSheet, PlanInfo planInfo) {
        List<FullDisciplineData> diciplineList = new ArrayList<>();
        DisciplineForm lectureForm = disciplineService.getDisciplineFormById(1L);
        DisciplineForm labForm = disciplineService.getDisciplineFormById(2L);
        DisciplineForm practiceForm = disciplineService.getDisciplineFormById(3L);

        ReportingForm examForm = disciplineService.getReportingById(1L);
        ReportingForm reportForm = disciplineService.getReportingById(2L);
        ReportingForm dReportForm = disciplineService.getReportingById(3L);

        DisciplineType general = disciplineService.getDisciplineTypeById(1L);
        DisciplineType professional = disciplineService.getDisciplineTypeById(2L);
        DisciplineType choice = disciplineService.getDisciplineTypeById(3L);

        PersonalTaskForm projectForm = disciplineService.getPersonalTaskFormById(1L);
        PersonalTaskForm workForm = disciplineService.getPersonalTaskFormById(2L);

        int startIndex = 10;

        for (int i = startIndex; i < disciplineSheet.getPhysicalNumberOfRows(); i++) {
            CellReference disciplineNum = new CellReference("A" + i);
            String text = disciplineSheet.getRow(disciplineNum.getRow()).getCell(disciplineNum.getCol()) != null ? disciplineSheet.getRow(disciplineNum.getRow()).getCell(disciplineNum.getCol()).getStringCellValue() : "";

            if (text.length() < 10 && (text.contains("ОКП") || text.contains("ОКЗ") || text.contains("ВК"))) {
                log.info(text);

                for (int sem = 1; sem <= planInfo.getNumberOfSemester(); sem++) {
                    FullDisciplineData fullDisciplineData = new FullDisciplineData();
                    getCipherAndNum(text, fullDisciplineData, general, professional, choice);


                    CellReference disciplineName = new CellReference("B" + i);
                    String subjectNameText = disciplineSheet.getRow(disciplineName.getRow()).getCell(disciplineName.getCol()).getStringCellValue();
                    log.info(subjectNameText);

                    SubjectName subjectName = disciplineService.getSubjectNameByName(subjectNameText.strip());
                    log.info("----------------------------------\n  subj - {}", subjectName);

                    if (subjectName == null) {
                        String nameForSave = subjectNameText.strip().replace("'","`");
                        SubjectName subject = new SubjectName();
                        subject.setName(nameForSave);
                        subjectName = disciplineService.saveSubjectName(subject);
                    }

                    CellReference departmentNameText = new CellReference("C" + i);
                    String departmentName = disciplineSheet.getRow(departmentNameText.getRow()).getCell(departmentNameText.getCol()).getStringCellValue();
                    Department department = utilDictionaryService.getDepartmentByName(departmentName);

                    if (department == null) {
                        department = new Department();
                        department.setAbbreviation(departmentName);
                        department.setName(departmentName);
                        department = utilDictionaryService.saveDepartment(department);
                    }


                    log.info("------sem {} -----", sem);
                    getReport(i, sem, disciplineSheet, fullDisciplineData, reportForm, dReportForm, examForm);
                    List<PersonalTasks> personalTasksList = getPersonalTask(i, sem, disciplineSheet, fullDisciplineData, projectForm, workForm);
                    List<AuditoryHours> auditoryHoursList = readAuditoryHours(i, sem, disciplineSheet, fullDisciplineData, lectureForm, labForm, practiceForm);
                    readIndependentHours(i, sem, disciplineSheet, fullDisciplineData);

                    if (personalTasksList.size() > 0) {
                        fullDisciplineData.setPersonalTaskList(personalTasksList);
                    }

                    if (auditoryHoursList.size() > 0 || fullDisciplineData.getIndependentHours() != null) {
                        fullDisciplineData.setPlan(planInfo);
                        fullDisciplineData.setSubjectName(subjectName);
                        fullDisciplineData.setDepartment(department);

                        fullDisciplineData.setAuditoryHoursList(auditoryHoursList);

                        diciplineList.add(fullDisciplineData);
                        disciplineService.saveFullData(fullDisciplineData);
                    }

                }
            }
        }
    }

    private List<PersonalTasks> getPersonalTask(int rowNum, int sem, Sheet disciplineSheet, FullDisciplineData fullDisciplineData, PersonalTaskForm project, PersonalTaskForm work) {
        CellReference projectCell = new CellReference("I" + rowNum);
        CellReference workCell = new CellReference("J" + rowNum);

        CellReference taskSemesterCell = new CellReference("K" + rowNum);
        CellReference taskCell = new CellReference("L" + rowNum);

        List<PersonalTasks> tasksList = new ArrayList<>();

        if (disciplineSheet.getRow(projectCell.getRow()).getCell(projectCell.getCol()) != null) {
            double courseProject = disciplineSheet.getRow(projectCell.getRow()).getCell(projectCell.getCol()).getNumericCellValue();

            if (courseProject == sem) {
                fullDisciplineData.setSemester(sem);
                PersonalTasks personalTask = new PersonalTasks();
                personalTask.setPersonalTaskForm(project);
                tasksList.add(personalTask);
            }
        }

        if (disciplineSheet.getRow(workCell.getRow()).getCell(workCell.getCol()) != null) {
            double courseProject = disciplineSheet.getRow(workCell.getRow()).getCell(workCell.getCol()).getNumericCellValue();

            if (courseProject == sem) {
                fullDisciplineData.setSemester(sem);
                PersonalTasks personalTask = new PersonalTasks();
                personalTask.setPersonalTaskForm(work);
                tasksList.add(personalTask);
            }
        }


        if (disciplineSheet.getRow(taskSemesterCell.getRow()).getCell(taskSemesterCell.getCol()) != null) {
            String cellText = disciplineSheet.getRow(taskSemesterCell.getRow()).getCell(taskSemesterCell.getCol()).toString();
            char[] personalTaskSemesterList = cellText.replaceAll(" ", "").toCharArray();

            String taskCellText = disciplineSheet.getRow(taskCell.getRow()).getCell(taskCell.getCol()).toString();
            String[] reportingList = taskCellText.split(" ");

            for (int task = 0; task < personalTaskSemesterList.length; task++) {
                if (String.valueOf(personalTaskSemesterList[task]).equals(String.valueOf(sem))) {
                    fullDisciplineData.setSemester(sem);
                    PersonalTasks personalTask = new PersonalTasks();
                    PersonalTaskForm personalTaskForm = disciplineService.getPersonalTaskFormByName(reportingList[task]);
                    if (personalTaskForm != null) {
                        personalTask.setPersonalTaskForm(personalTaskForm);
                        tasksList.add(personalTask);
                    }
                    break;
                }
            }
        }
        return tasksList;
    }

    private void getCipherAndNum(String text, FullDisciplineData fullDisciplineData, DisciplineType general, DisciplineType proffessional, DisciplineType choice) {
        String[] disciplineNumArray = text.split(" ");

        if (disciplineNumArray[0].equals("ОКП")) {
            fullDisciplineData.setDisciplineType(proffessional);
        } else if (disciplineNumArray[0].equals("ОКЗ")) {
            fullDisciplineData.setDisciplineType(general);
        } else if (disciplineNumArray[0].equals("ВК")) {
            fullDisciplineData.setDisciplineType(choice);
        }

        if (disciplineNumArray[1].contains(".")) {
            String[] num = disciplineNumArray[1].split("\\.");
            fullDisciplineData.setDisciplineNum(Integer.parseInt(num[0]));
            fullDisciplineData.setDisciplineSubNum(Integer.parseInt(num[1]));
        } else {
            fullDisciplineData.setDisciplineNum(Integer.parseInt(disciplineNumArray[1]));
        }
    }

    private void getReport(int rowNum, int sem, Sheet disciplineSheet, FullDisciplineData fullDisciplineData, ReportingForm reportForm, ReportingForm dReportForm, ReportingForm examForm) {
        CellReference reportingCell = new CellReference("H" + rowNum);
        CellReference examCell = new CellReference("G" + rowNum);

        Reporting reporting = new Reporting();

        if (disciplineSheet.getRow(reportingCell.getRow()).getCell(reportingCell.getCol()) != null) {
            String cellText = disciplineSheet.getRow(reportingCell.getRow()).getCell(reportingCell.getCol()).toString();
            String[] reportingList = cellText.contains(",") ? cellText.split(",") : cellText.split(" ");

            for (String report : reportingList) {
                if (report.contains("д")) {
                    String dReport = report.replace("д", "");
                    if (dReport.equals(String.valueOf(sem))) {
                        log.info("report text - {}", report);
                        reporting.setDisciplineReportingForm(dReportForm);
                        break;
                    }
                } else if (report.equals(String.valueOf(sem))) {
                    log.info("report text - {}", report);
                    reporting.setDisciplineReportingForm(reportForm);
                    break;
                }
            }
        }


        if (disciplineSheet.getRow(examCell.getRow()).getCell(examCell.getCol()) != null) {

            String cellText = disciplineSheet.getRow(examCell.getRow()).getCell(examCell.getCol()).toString();
            String[] reportingList = cellText.contains(",") ? cellText.split(",") : cellText.split(" ");

            for (String report : reportingList) {
                if (report.equals(String.valueOf(sem))) {
                    log.info("exam text - {}", cellText);
                    reporting.setDisciplineReportingForm(examForm);
                    break;
                }
            }
        }

        log.info("report - {}", reporting);
        if (reporting.getDisciplineReportingForm() == null) {
            fullDisciplineData.setReporting(null);
        } else {
            fullDisciplineData.setReporting(reporting);
        }
    }

    private List<AuditoryHours> readAuditoryHours(int rowNum, int sem, Sheet disciplineSheet, FullDisciplineData fullDisciplineData, DisciplineForm lectureForm, DisciplineForm labForm, DisciplineForm practiceForm) {
        List<AuditoryHours> auditoryHoursList = new ArrayList<>();
        CellReference crLecture = new CellReference(fullPlanLectureCellLetterMap.get(sem) + rowNum);
        CellReference crPract = new CellReference(fullPlanPracticeCellLetterMap.get(sem) + rowNum);
        CellReference crLab = new CellReference(fullPlanLabCellLetterMap.get(sem) + rowNum);

        Cell cellLecture = disciplineSheet.getRow(crLecture.getRow()).getCell(crLecture.getCol());
        Cell cellPract = disciplineSheet.getRow(crPract.getRow()).getCell(crPract.getCol());
        Cell cellLab = disciplineSheet.getRow(crLab.getRow()).getCell(crLab.getCol());

        if (cellLecture != null) {
            double hoursNum = cellLecture.getNumericCellValue();
            if (hoursNum > 0.0) {
                fullDisciplineData.setSemester(sem);
                AuditoryHours lectureHours = new AuditoryHours();
                lectureHours.setDisciplineForm(lectureForm);
                lectureHours.setHoursNum(hoursNum);
                auditoryHoursList.add(lectureHours);
                log.info("lecture {}", hoursNum);
            }
        }
        if (cellPract != null) {
            double hoursNum = cellPract.getNumericCellValue();
            if (hoursNum > 0.0) {
                fullDisciplineData.setSemester(sem);
                AuditoryHours practiceHours = new AuditoryHours();
                practiceHours.setDisciplineForm(practiceForm);
                practiceHours.setHoursNum(hoursNum);
                auditoryHoursList.add(practiceHours);
                log.info("pract {}", hoursNum);
            }
        }
        if (cellLab != null) {
            double hoursNum = cellLab.getNumericCellValue();
            if (hoursNum > 0.0) {
                fullDisciplineData.setSemester(sem);
                AuditoryHours labHours = new AuditoryHours();
                labHours.setDisciplineForm(labForm);
                labHours.setHoursNum(hoursNum);
                auditoryHoursList.add(labHours);
                log.info("lab {}", hoursNum);
            }
        }


        return auditoryHoursList;
    }

    private void readIndependentHours(int rowNum, int sem, Sheet disciplineSheet, FullDisciplineData fullDisciplineData) {
        CellReference crInd = new CellReference(fullPlanIndependentCellLetterMap.get(sem) + rowNum);
        Cell cellInd = disciplineSheet.getRow(crInd.getRow()).getCell(crInd.getCol());
        if (cellInd != null) {
            double hoursNum = cellInd.getNumericCellValue();
            if (hoursNum > 0.0) {
                IndependentHours independentHours = new IndependentHours();
                fullDisciplineData.setSemester(sem);
                independentHours.setHoursNum(hoursNum);

                fullDisciplineData.setIndependentHours(independentHours);
                log.info("int {}", hoursNum);
            }
        }
    }

    private PlanInfo readTitlePage(Sheet sheet) {

        CellReference admissionYearCR = new CellReference("T11");
        String admissionYear = sheet.getRow(admissionYearCR.getRow()).getCell(admissionYearCR.getCol()).getStringCellValue();

        CellReference termCR = new CellReference("AS6");
        String term = sheet.getRow(termCR.getRow()).getCell(termCR.getCol()).getStringCellValue();

        CellReference stepCR = new CellReference("T13");
        String step = sheet.getRow(stepCR.getRow()).getCell(stepCR.getCol()).getStringCellValue();

        CellReference baseCR = new CellReference("AS8");
        String base = sheet.getRow(baseCR.getRow()).getCell(baseCR.getCol()).getStringCellValue();

        CellReference formCR = new CellReference("U21");
        String form = sheet.getRow(formCR.getRow()).getCell(formCR.getCol()).getStringCellValue();

        LocalDateTime addmission = LocalDateTime.now();
        StudyingTerm t = null;
        Base b = null;
        Step s = utilDictionaryService.getStepByName(step);
        Qualification q = utilDictionaryService.getQualificationById(s.getId());

        StudyingForm sf = utilDictionaryService.getStudyingFormByName(form);


        if (admissionYear.contains("/")) {
            String[] year = admissionYear.split("/");
            addmission = LocalDateTime.of(Integer.parseInt(year[0].replace(" ", "")), Month.SEPTEMBER, 1, 00, 00, 00);
            log.info("year - {}", addmission);
        }

        if (term.contains("-")) {
            String[] termStrArr = term.split("- ");
            String tStr = termStrArr[1];
            t = utilDictionaryService.getStudyingTermByName(tStr);
            log.info("term - {}, {}", tStr, t.toString());
        }

        if (base.contains("на основі ")) {
            String bStr = base.replace("на основі ", "");
            b = utilDictionaryService.getBaseByName(bStr);
            log.info("base - {}, {}", bStr, b.toString());
        }

        log.info("year - {}, term - {}, step - {}, base - {}, form - {}", admissionYear, term, step, base, form);


        Cipher cipher = utilDictionaryService.getCipherById(1L);

        PlanInfo planInfo = new PlanInfo();
        // TODO read cipher from file
        planInfo.setPlanCipher(cipher);
        planInfo.setQualification(q);
        planInfo.setBase(b);
        planInfo.setStep(s);
        planInfo.setStudyingForm(sf);
        planInfo.setStudyingTerm(t);
        planInfo.setAdmissionYear(addmission);
        planInfo.setNumberOfSemester(t.getSemesterNum());

//        return planInfo;
        return planInfoService.savePlanData(planInfo);
    }

    private void readTableData(Sheet sheet, PlanInfo planInfo) {
        log.info(planInfo.getStudyingTerm().toString());
        int courses = (int) Math.ceil(planInfo.getStudyingTerm().getSemesterNum() / 2);
        log.info("courses {}", courses);

        int startRow = 25;

//        CellReference startWeekCR = new CellReference("F26");
//        Cell startWeek = sheet.getRow(startWeekCR.getRow()).getCell(startWeekCR.getCol());

        for (int i = 1; i <= courses; i++) {
            int startColumn = 5;
            for (int j = 1; j < COURSE_WEEK_NUM; j++) {
                String letter = sheet.getRow(startRow).getCell(startColumn).toString();
                log.info("course {}, week {} - {}",i,j, letter);
                if (!letter.isEmpty()) {
                    StudyingType type = utilDictionaryService.getStudyingTypeByLetter(letter);
                    log.info("Type {}", type);
                }
                startColumn++;
            }
            startRow++;
        }
    }
}
