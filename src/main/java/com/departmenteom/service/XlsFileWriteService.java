package com.departmenteom.service;

import com.departmenteom.entity.*;
import com.departmenteom.entity.dictionary.DisciplineType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class XlsFileWriteService {

    private final PlanInfoService planInfoService;
    private final WeekPlanService weekPlanService;
    private final DisciplineService disciplineService;
    private final StudentService studentService;

    private final Map<Integer, String> fullPlanLectureCellLetterMap = Map.of(1, "U", 2, "Y", 3, "AC", 4, "AG", 5, "AK", 6, "AO", 7, "AS", 8, "AW");
    private final Map<Integer, String> fullPlanLabCellLetterMap = Map.of(1, "W", 2, "AA", 3, "AE", 4, "AI", 5, "AM", 6, "AQ", 7, "AU", 8, "AY");
    private final Map<Integer, String> fullPlanPracticeCellLetterMap = Map.of(1, "V", 2, "Z", 3, "AD", 4, "AH", 5, "AL", 6, "AP", 7, "AT", 8, "AX");
    private final Map<Integer, String> fullPlanIndependentCellLetterMap = Map.of(1, "X", 2, "AB", 3, "AF", 4, "AJ", 5, "AN", 6, "AR", 7, "AV", 8, "AZ");


    private final Map<Integer, String> personalPlanLectureCellLetterMap = Map.of(1, "K", 2, "O");
    private final Map<Integer, String> personalPlanLabCellLetterMap = Map.of(1, "M", 2, "Q");
    private final Map<Integer, String> personalPlanPracticeCellLetterMap = Map.of(1, "L", 2, "P");
    private final Map<Integer, String> personalPlanIndependentCellLetterMap = Map.of(1, "N", 2, "R");


    @Value("${shablon.file.path}")
    public String PLAN_SHABLONS_PATH;
    @Value("${full.plan.shablon}")
    public String FULL_PLAN_SHABLON_NAME;
    @Value("${personal.plan.shablon}")
    public String PERSONAL_PLAN_SHABLON_NAME;

    public int generalSumRow = 0;
    public int professionalSumRow = 0;

    public List<Reporting> personalExamCount = new ArrayList<>();
    public List<Reporting> personalReportCount = new ArrayList<>();

    static final int SEMESTER_START = 20;
    static final int EXAM_COL = 7;
    static final int REPORTING_COL = 8;
    static final int CP_COL = 9;
    static final int CW_COL = 10;

    static final int RECORD_BOOK_ROW = 7;
    static final int RECORD_BOOK_COL = 8;
    static final int STUDENT_ROW = 10;
    static final int STUDENT_FIO_COL = 3;
    static final int STUDENT_GROUP_COL = 13;
    static final int STUDENT_COURSE_COL = 15;
    static final int SEMESTER_ROW = 13;
    static final int SEMESTER_1_COL = 4;
    static final int SEMESTER_2_COL = 12;

    static final int COURSE_PLAN_SEMESTER_ROW = 20;
    static final int COURSE_PLAN_WEEK_ROW = 22;

    static final int FIRST_SEM = 1;
    static final int SECOND_SEM = 2;

    static final int MAIN_SHEET = 0;
    static final int DISCIPLINE_SHEET = 1;


    /**
     * Font style for simple text (not bold ot italic)
     */
    private XSSFFont simpleFont(XSSFWorkbook workbook, int fontSize, String fontName) {
        XSSFFont defaultFont = workbook.createFont();
        defaultFont.setFontHeightInPoints((short) fontSize);
        defaultFont.setFontName(fontName);
        defaultFont.setBold(false);
        defaultFont.setItalic(false);
        defaultFont.setColor(IndexedColors.BLACK.getIndex());

        return defaultFont;
    }

    /**
     * Font style for bold and italic text
     */
    private XSSFFont boldItalicFont(XSSFWorkbook workbook, int fontSize, String fontName) {
        XSSFFont defaultFont = workbook.createFont();
        defaultFont.setFontHeightInPoints((short) fontSize);
        defaultFont.setFontName(fontName);
        defaultFont.setBold(true);
        defaultFont.setItalic(true);
        defaultFont.setColor(IndexedColors.BLACK.getIndex());

        return defaultFont;
    }

    /**
     * Create borders for cell
     */
    private XSSFCellStyle createBorder(XSSFCellStyle style) {
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    private XSSFCellStyle createDefaultFontStyle(XSSFWorkbook workbook) {
        XSSFCellStyle text = workbook.createCellStyle();
        text.setFont(simpleFont(workbook, 11, "Arial"));
        return createBorder(text);
    }

    private XSSFCellStyle createBoldItalicFontStyle(XSSFWorkbook workbook) {
        XSSFCellStyle text = workbook.createCellStyle();
        text.setFont(boldItalicFont(workbook, 11, "Arial"));
        return createBorder(text);
    }

    /**
     * Plan sheet header text (columns name)
     */
    private XSSFCellStyle createVerticalFont(XSSFWorkbook workbook) {
        XSSFCellStyle text = workbook.createCellStyle();
        text.setRotation((short) 90);
        text.getStyleXf().setApplyAlignment(true);
        text.setFont(simpleFont(workbook, 9, "Arial"));
        return createBorder(text);
    }

    private XSSFCellStyle createCenterText(XSSFWorkbook workbook) {
        XSSFCellStyle text = workbook.createCellStyle();
        text.setAlignment(HorizontalAlignment.CENTER);
        text.setFont(simpleFont(workbook, 9, "Arial"));
        return createBorder(text);
    }


    /** FORMING COURSE PLAN FILE */

    public ByteArrayInputStream getPersonalPlan(Long planId, Long studentId, int course) throws IOException {
        Student student = studentService.getStudentById(studentId);
        log.info("Start creating file with {} course plan for student {}", course, student.toString());
        File shablonFile = new File(PLAN_SHABLONS_PATH + PERSONAL_PLAN_SHABLON_NAME);

        FileInputStream inputStream = new FileInputStream(shablonFile);
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        inputStream.close();
        XSSFCellStyle defaultText = createDefaultFontStyle(workbook);
        Sheet sheet = workbook.getSheetAt(MAIN_SHEET);

        setStudentData(sheet, student, course, defaultText);
        setIntegerByColumnNum(sheet, SEMESTER_1_COL, SEMESTER_ROW, course * 2 - 1, defaultText);
        setIntegerByColumnNum(sheet, SEMESTER_2_COL, SEMESTER_ROW, course * 2, defaultText);

        Map<Integer, List<WeekPlan>> weekPlanList = weekPlanService.getByPlanAndCourse(planId, course);
        setWeekSchedule(sheet, weekPlanList.get(course * 2 - 1), 4, defaultText);
        setWeekSchedule(sheet, weekPlanList.get(course * 2), 12, defaultText);

        Map<Integer, Integer> semesterWeeksTerm = new HashMap<>();

        weekPlanList.keySet().forEach(semester -> weekPlanList.get(semester).forEach(week -> {
            if (week.getStudyingType().getId() == 1L)
                semesterWeeksTerm.put(week.getSemester(), week.getTerm());
        }));

        setTextByColumnLetter(sheet, personalPlanLectureCellLetterMap.get(FIRST_SEM), COURSE_PLAN_SEMESTER_ROW, course * 2 - 1, defaultText);
        setTextByColumnLetter(sheet, personalPlanLectureCellLetterMap.get(FIRST_SEM), COURSE_PLAN_WEEK_ROW, semesterWeeksTerm.get(course * 2 - 1), defaultText);

        setTextByColumnLetter(sheet, personalPlanLectureCellLetterMap.get(SECOND_SEM), COURSE_PLAN_SEMESTER_ROW, course * 2, defaultText);
        setTextByColumnLetter(sheet, personalPlanLectureCellLetterMap.get(SECOND_SEM), COURSE_PLAN_WEEK_ROW, semesterWeeksTerm.get(course * 2), defaultText);


        List<Discipline> disciplineList = disciplineService.getDisciplineByPlanAndCourse(planId, course);

        Map<DisciplineType, List<Discipline>> disciplineTypeDict = disciplineList.stream().collect(Collectors.groupingBy(Discipline::getDisciplineType));
        Set<DisciplineType> sortedKey = disciplineTypeDict.keySet().stream().sorted(Comparator.comparingLong(DisciplineType::getId)).collect(Collectors.toCollection(LinkedHashSet::new));

        AtomicInteger startRow = new AtomicInteger(25);
        sortedKey.forEach(key -> {
            startRow.set(setDisciplineDataForPersonalPlan(workbook, sheet, key.getId(), disciplineTypeDict.get(key), startRow.get(), defaultText));
        });

        long sem1Exam = countReportingBySemester(personalExamCount, course * 2 - 1);
        long sem2Exam = countReportingBySemester(personalExamCount, course * 2);
        setTextByColumnNum(sheet, 3, startRow.get(), sem1Exam + " " + sem2Exam, defaultText);

        long sem1Report = countReportingBySemester(personalReportCount, course * 2 - 1);
        long sem2Report = countReportingBySemester(personalReportCount, course * 2);
        setTextByColumnNum(sheet, 4, startRow.get(), sem1Report + " " + sem2Report, defaultText);


        calculateHoursSumForPersonal(workbook, sheet, FIRST_SEM, startRow.get(), defaultText);
        calculateHoursSumForPersonal(workbook, sheet, SECOND_SEM, startRow.get(), defaultText);

//        for (int i = 1; i <= 2; i++) {
//            setFormulaByColumnLetter(workbook, sheet, personalPlanLectureCellLetterMap.get(i), startRow.get(),
//                    createSumHoursFormula(personalPlanLectureCellLetterMap, i, 25, startRow.get()), defaultText);
//            setFormulaByColumnLetter(workbook, sheet, personalPlanLabCellLetterMap.get(i), startRow.get(),
//                    createSumHoursFormula(personalPlanLabCellLetterMap, i, 25, startRow.get()), defaultText);
//            setFormulaByColumnLetter(workbook, sheet, personalPlanIndependentCellLetterMap.get(i), startRow.get(),
//                    createSumHoursFormula(personalPlanIndependentCellLetterMap, i, 25, startRow.get()), defaultText);
//            setFormulaByColumnLetter(workbook, sheet, personalPlanPracticeCellLetterMap.get(i), startRow.get(),
//                    createSumHoursFormula(personalPlanPracticeCellLetterMap, i, 25, startRow.get()), defaultText);
//        }

        setFormula(workbook, sheet, 8, startRow.get(), "SUM($H$" + 25 + ":" + "$H$" + (startRow.get() - 1) + ")", defaultText);
        setFormula(workbook, sheet, 9, startRow.get(), "SUM($I$" + 25 + ":" + "$I$" + (startRow.get() - 1) + ")", defaultText);
        setFormula(workbook, sheet, 10, startRow.get(), "SUM($J$" + 25 + ":" + "$J$" + (startRow.get() - 1) + ")", defaultText);
        startRow.getAndIncrement();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);

        log.info("Course plan file created");
        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    /**
     * Set data about student to course plan
     * @param sheet
     * @param student
     * @param course
     * @param defaultText
     */
    private void setStudentData(Sheet sheet, Student student, int course, CellStyle defaultText) {
        String groupName = student.getGroupInfo().getGroupCipher().getName() + "-" + student.getGroupInfo().getAdmissionYear().getYear() + "-" + student.getGroupInfo().getIndex();
        setTextByColumnNum(sheet, RECORD_BOOK_COL, RECORD_BOOK_ROW, student.getRecordBook(), defaultText);
        setTextByColumnNum(sheet, STUDENT_FIO_COL, STUDENT_ROW, student.getFirstName().toUpperCase() + " " + student.getLastName().toUpperCase(), defaultText);
        setTextByColumnNum(sheet, STUDENT_GROUP_COL, STUDENT_ROW, groupName, defaultText);
        setIntegerByColumnNum(sheet, STUDENT_COURSE_COL, STUDENT_ROW, course, defaultText);
    }

    /**
     * Set weeks schedule
     * @param sheet
     * @param weekPlanList
     * @param col
     * @param style
     */
    private void setWeekSchedule(Sheet sheet, List<WeekPlan> weekPlanList, int col, CellStyle style) {
        Optional<WeekPlan> theoretic = weekPlanList.stream().filter(weekPlan -> weekPlan.getStudyingType().getId() == 1L).findFirst();
        Optional<WeekPlan> exam = weekPlanList.stream().filter(weekPlan -> weekPlan.getStudyingType().getId() == 2L).findFirst();
        Optional<WeekPlan> practice = weekPlanList.stream().filter(weekPlan -> weekPlan.getStudyingType().getId() == 4L).findFirst();
        Optional<WeekPlan> certification = weekPlanList.stream().filter(weekPlan -> weekPlan.getStudyingType().getId() == 5L).findFirst();

        if (theoretic.isPresent()) {
            setIntegerByColumnNum(sheet, col, 15, theoretic.get().getStartWeek(), style);
            setIntegerByColumnNum(sheet, col + 3, 15, theoretic.get().getStartWeek() + theoretic.get().getTerm(), style);
        }
        if (exam.isPresent()) {
            setIntegerByColumnNum(sheet, col, 16, exam.get().getStartWeek(), style);
            setIntegerByColumnNum(sheet, col + 3, 16, exam.get().getStartWeek() + exam.get().getTerm(), style);
        }
        if (practice.isPresent()) {
            setIntegerByColumnNum(sheet, col, 17, practice.get().getStartWeek(), style);
            setIntegerByColumnNum(sheet, col + 3, 17, practice.get().getStartWeek() + practice.get().getTerm(), style);
        }
        if (certification.isPresent()) {
            setIntegerByColumnNum(sheet, col, 18, certification.get().getStartWeek(), style);
            setIntegerByColumnNum(sheet, col + 3, 18, certification.get().getStartWeek() + certification.get().getTerm(), style);
        }
    }

    private int setDisciplineDataForPersonalPlan(XSSFWorkbook workbook, Sheet sheet, Long type, List<Discipline> disciplineList, int startRow, CellStyle style) {
        int index = 0;

        AtomicInteger countCP = new AtomicInteger();
        AtomicInteger countCW = new AtomicInteger();

        Map<Integer, List<Discipline>> disciplineDict = groupAndSortDisciplineList(disciplineList, type);

        for (Integer subject : disciplineDict.keySet()) {
            StringBuilder reportingSB = new StringBuilder("");
            StringBuilder examSB = new StringBuilder("");
            int reportingRow = 0;
            int disciplineNum = 1;
            for (Discipline discipline : disciplineDict.get(subject)) {
                setTextByColumnNum(sheet, 2, startRow + index, discipline.getSubjectName().getName(), style);
                int finalIndex = index;
                String disciplineOPPNum = discipline.getDisciplineType().getId() + "." + disciplineNum;
                disciplineNum++;

                setTextByColumnNum(sheet, 1, startRow + finalIndex, disciplineOPPNum, style);

                if (discipline.getReporting() != null) {

                    long reportType = discipline.getReporting().getDisciplineReportingForm().getId();

                    if (reportType == 1L) {
                        personalExamCount.add(discipline.getReporting());
                        examSB = examSB.length() > 0 ? examSB.append(" ").append(discipline.getSemester()) : examSB.append(discipline.getSemester());
                    } else {
                        personalReportCount.add(discipline.getReporting());
                        if (reportingSB.length() > 0) {
                            reportingSB = reportType == 3L ? reportingSB.append(" ").append(discipline.getSemester()).append("д") : reportingSB.append(" ").append(discipline.getSemester());
                        } else {
                            reportingSB = reportType == 3L ? reportingSB.append(discipline.getSemester()).append("д") : reportingSB.append(discipline.getSemester());
                        }
                    }

                    reportingRow = startRow + finalIndex;
                }

                if (discipline.getPersonalTasksSet().size() > 0) {
                    setPersonalTasksData(sheet, discipline, startRow, finalIndex, style, countCP, countCW, 7,6,6,6);
                }

                if (discipline.getAuditoryHoursSet().size() > 0) {
                    discipline.getAuditoryHoursSet().forEach(item -> {
                        int sem = discipline.getSemester() % 2 == 1 ? 1 : 2;
                        if (item.getDisciplineForm().getId() == 1L)  // lecture
                            setDoubleByColumnNum(sheet, 10 + sem * 4 - 3, startRow + finalIndex, item.getHoursNum(), style);
                        else if (item.getDisciplineForm().getId() == 2L) // labs
                            setDoubleByColumnNum(sheet, 10 + sem * 4 - 1, startRow + finalIndex, item.getHoursNum(), style);
                        else if (item.getDisciplineForm().getId() == 3L)  // practice
                            setDoubleByColumnNum(sheet, 10 + sem * 4 - 2, startRow + finalIndex, item.getHoursNum(), style);
                    });
                }

                if (discipline.getIndependentHours() != null) {
                    int sem = discipline.getSemester() % 2 == 1 ? 1 : 2;
                    setDoubleByColumnNum(sheet, 10 + sem * 4, startRow + finalIndex, Math.ceil(discipline.getIndependentHours().getHoursNum()), style);
                }

                StringBuilder formula = new StringBuilder();
                for (int sem = 1; sem <= 2; sem++) {
                    String semesterVal = "SUM($" + personalPlanLectureCellLetterMap.get(sem) + (startRow + finalIndex) + ":" + personalPlanLabCellLetterMap.get(sem) + (startRow + finalIndex) + ")*($" + personalPlanLectureCellLetterMap.get(sem) + "$22" + " - 1)";
                    formula.append(semesterVal);
                    if (sem < 2) formula.append("+");
                }
                setFormula(workbook, sheet, 10, startRow + finalIndex, formula.toString(), style);

                setFormula(workbook, sheet, 9, startRow + finalIndex, "$J$" + (startRow + finalIndex) + "+$" + personalPlanIndependentCellLetterMap.get(1) + "$" + (startRow + finalIndex) + "+$" + personalPlanIndependentCellLetterMap.get(2) + "$" + (startRow + finalIndex), style);

                setFormula(workbook, sheet, 8, startRow + finalIndex, "$I$" + (startRow + finalIndex) + "/30", style);
            }

            if (reportingSB.length() > 0) {
                setTextByColumnNum(sheet, 4, reportingRow, reportingSB.toString(), style);
            }

            if (examSB.length() > 0) {
                setTextByColumnNum(sheet, 3, reportingRow, examSB.toString(), style);
            }
            index++;
        }
        return startRow + index;
    }

    private long countReportingBySemester(List<Reporting> reportingList, int semester) {
        return reportingList.stream().filter(report -> report.getDisciplineReporting().getSemester() == semester).count();
    }

    private void calculateHoursSumForPersonal(Workbook workbook, Sheet sheet, int sem, int startRow, CellStyle defaultText) {
        setFormulaByColumnLetter(workbook, sheet, personalPlanLectureCellLetterMap.get(sem), startRow,
                createSumHoursFormula(personalPlanLectureCellLetterMap, sem, 25, startRow), defaultText);
        setFormulaByColumnLetter(workbook, sheet, personalPlanLabCellLetterMap.get(sem), startRow,
                createSumHoursFormula(personalPlanLabCellLetterMap, sem, 25, startRow), defaultText);
        setFormulaByColumnLetter(workbook, sheet, personalPlanIndependentCellLetterMap.get(sem), startRow,
                createSumHoursFormula(personalPlanIndependentCellLetterMap, sem, 25, startRow), defaultText);
        setFormulaByColumnLetter(workbook, sheet, personalPlanPracticeCellLetterMap.get(sem), startRow,
                createSumHoursFormula(personalPlanPracticeCellLetterMap, sem, 25, startRow), defaultText);
    }

    private String createSumHoursFormula(Map<Integer, String> hoursMap, int sem, int startRow, int endRow) {
        return "SUM($" + hoursMap.get(sem) + "$" + startRow + ":$" + hoursMap.get(sem) + "$" + (endRow - 1) + ")";
    }



    /** FORMING FULL PLAN FILE */

    public ByteArrayInputStream getPlanInFile(Long id) throws IOException {
        PlanInfo planInfo = planInfoService.getPlanById(id);

        log.info("Start creating file with full plan (search plan with id {})", id);
        File shablonFile = new File(PLAN_SHABLONS_PATH + FULL_PLAN_SHABLON_NAME);

        FileInputStream inputStream = new FileInputStream(shablonFile);
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        inputStream.close();

        XSSFCellStyle defaultText = createDefaultFontStyle(workbook);
        XSSFCellStyle boldItalicText = createBoldItalicFontStyle(workbook);

        Sheet sheet = workbook.getSheetAt(MAIN_SHEET);


        List<WeekPlan> weekPlanList = weekPlanService.getByPlan(id);
        Map<Integer, Integer> semesterWeeksTerm = new HashMap<>();

        weekPlanList.forEach(week -> {
            if (week.getStudyingType().getId() == 1L) semesterWeeksTerm.put(week.getSemester(), week.getTerm());
        });

        int semesterNum = planInfoService.getSemesterNum(id);
        List<Discipline> disciplineList = disciplineService.getDisciplineByPlan(id);

        setMainPlanData(sheet, planInfo, defaultText);
        setTableData(workbook, sheet, weekPlanList, semesterNum, defaultText);

        sheet = workbook.getSheetAt(DISCIPLINE_SHEET);
        Sheet finalSheet = sheet;

        setFullPlanDisciplineHeaderTable(sheet, workbook, semesterNum, semesterWeeksTerm);

        Map<DisciplineType, List<Discipline>> disciplineTypeDict = disciplineList.stream().collect(Collectors.groupingBy(Discipline::getDisciplineType));
        Set<DisciplineType> sortedKey = disciplineTypeDict.keySet().stream().sorted(Comparator.comparingLong(DisciplineType::getId)).collect(Collectors.toCollection(LinkedHashSet::new));

        AtomicInteger startRow = new AtomicInteger(7);
        sortedKey.forEach(key -> {
            setTextByColumnNum(finalSheet, 1, startRow.get(), key.getName(), boldItalicText);
            startRow.set(startRow.get() + 1);
            startRow.set(setDisciplineData(workbook, finalSheet, key.getId(), disciplineTypeDict.get(key), startRow.get(), semesterNum, defaultText));
        });
        startRow.getAndIncrement();

        calculateFullPlanHours(workbook, sheet, startRow.get(), semesterNum, defaultText);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        log.info("Full plan file created");
        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    /**
     * Set header plan info
     * @param sheet
     * @param planInfo
     * @param defaultText text style
     */
    private void setMainPlanData(Sheet sheet, PlanInfo planInfo, XSSFCellStyle defaultText) {
        setTextByColumnLetter(sheet, "AX", 3, planInfo.getQualification().getName(), defaultText);
        setTextByColumnLetter(sheet, "AW", 9, planInfo.getBase().getName(), defaultText);
        setTextByColumnLetter(sheet, "T", 11, planInfo.getAdmissionYear().getYear() + "/" + (planInfo.getAdmissionYear().getYear() + 1) + "н.р.", defaultText);
        setTextByColumnLetter(sheet, "T", 13, planInfo.getStep().getName(), defaultText);
        setTextByColumnLetter(sheet, "AH", 19, planInfo.getQualification().getName(), defaultText);
        setTextByColumnLetter(sheet, "T", 21, planInfo.getStudyingForm().getName(), defaultText);
        setTextByColumnLetter(sheet, "AY", 7, planInfo.getStudyingTerm().getName(), defaultText);
    }

    /**
     * Set data to tables I - IV
     * I - studying week type by course info
     * II - auditory and independent hours type and sum by course info
     * III - practice type and semester info
     * IV - diploma project type and semester info
     * @param workbook
     * @param sheet
     * @param weekPlanList
     * @param semesterNum
     * @param defaultText
     */
    private void setTableData(Workbook workbook, Sheet sheet, List<WeekPlan> weekPlanList, int semesterNum, XSSFCellStyle defaultText) {
        int courses = semesterNum / 2;
        int startCourseRow = 25;
        int startCourseCol = 5;
        int table2StartRow = 34;

        for (int i = 1; i <= courses; i++) {
            AtomicInteger col = new AtomicInteger(startCourseCol);
            setIntegerByColumnNum(sheet, col.get(), startCourseRow + i, i, defaultText);

            int semester = i * 2;
            List<WeekPlan> weeks1 = weekPlanList.stream().filter(week -> week.getSemester() == semester - 1).collect(Collectors.toList());
            List<WeekPlan> weeks2 = weekPlanList.stream().filter(week -> week.getSemester() == semester).collect(Collectors.toList());

            col = setStudyingTypeBySemester(sheet, weeks1, col, startCourseRow, i, defaultText);
            col = setStudyingTypeBySemester(sheet, weeks2, col, startCourseRow, i, defaultText);

            int lecture = getStudyingTypeCount(weekPlanList, semester, 1L);
            int exams = getStudyingTypeCount(weekPlanList, semester, 2L);
            int holidays = getStudyingTypeCount(weekPlanList, semester, 3L);
            int practice = getStudyingTypeCount(weekPlanList, semester, 4L);
            int attestation = getStudyingTypeCount(weekPlanList, semester, 5L);
            int diplomaWriting = getStudyingTypeCount(weekPlanList, semester, 7L);


            int r = table2StartRow + i;
            setIntegerByColumnNum(sheet, 3, r, i, defaultText);
            setIntegerByColumnNum(sheet, 5, r, lecture, defaultText);
            setIntegerByColumnNum(sheet, 7, r, exams, defaultText);
            setIntegerByColumnNum(sheet, 9, r, practice, defaultText);
            setIntegerByColumnNum(sheet, 11, r, attestation, defaultText);
            setIntegerByColumnNum(sheet, 13, r, diplomaWriting, defaultText);
            setIntegerByColumnNum(sheet, 17, r, holidays, defaultText);
            setFormula(workbook, sheet, 19, r, "SUM($E$" + r + ":$R$" + r + ")", defaultText);
        }
    }

    private AtomicInteger setStudyingTypeBySemester(Sheet sheet, List<WeekPlan> weeks, AtomicInteger col, int startCourseRow, int semester, XSSFCellStyle defaultText) {
        List<WeekPlan> sortedWeek = weeks.stream().sorted(Comparator.comparing(WeekPlan::getStartWeek)).collect(Collectors.toList());
        sortedWeek.forEach(week -> {
            for (int j = 1; j <= week.getTerm(); j++) {
                col.getAndIncrement();
                setTextByColumnNum(sheet, col.get(), startCourseRow + semester, week.getStudyingType().getLetter(), defaultText);
            }
        });
        return col;
    }

    /**
     * Calculate sum of studying week for course by type
     * @param weekPlanList
     * @param semester
     * @param typeId
     * @return
     */
    private int getStudyingTypeCount(List<WeekPlan> weekPlanList, int semester, long typeId) {
        return weekPlanList.stream().filter(weekPlan -> (weekPlan.getSemester() == semester - 1 || weekPlan.getSemester() == semester) && weekPlan.getStudyingType().getId() == typeId).mapToInt(WeekPlan::getTerm).sum();
    }

    /**
     * Set hours column names to discipline sheet by course/semester
     * @param sheet
     * @param workbook
     * @param semesterNum
     * @param semesterWeesTerm
     */
    private void setFullPlanDisciplineHeaderTable(Sheet sheet, XSSFWorkbook workbook, int semesterNum, Map<Integer, Integer> semesterWeesTerm) {
        XSSFCellStyle vertical = createVerticalFont(workbook);
        XSSFCellStyle centerText = createCenterText(workbook);

        for (int sem = 1; sem <= semesterNum; sem++) {
            if (sem % 2 == 1)
                setTextByColumnLetter(sheet, fullPlanLectureCellLetterMap.get(sem), 3, (sem / 2 + 1) + " курс", centerText);
            setTextByColumnLetter(sheet, fullPlanLectureCellLetterMap.get(sem), 4, sem + " семестр", centerText);
            setTextByColumnLetter(sheet, fullPlanLectureCellLetterMap.get(sem), 5, "лекції", vertical);
            setTextByColumnLetter(sheet, fullPlanLabCellLetterMap.get(sem), 5, "лабораторні роботи", vertical);
            setTextByColumnLetter(sheet, fullPlanPracticeCellLetterMap.get(sem), 5, "практ. (сем.) заняття", vertical);
            setTextByColumnLetter(sheet, fullPlanIndependentCellLetterMap.get(sem), 5, "самостійна робота", vertical);
            setTextByColumnLetter(sheet, fullPlanLectureCellLetterMap.get(sem), 6, semesterWeesTerm.get(sem), centerText);
        }
    }

    /**
     * Set full discipline data (all hours and formulas)
     * @param workbook
     * @param sheet
     * @param type
     * @param disciplineList
     * @param startRow
     * @param semesterNum
     * @param defaultText
     * @return last filled row num
     */

    private int setDisciplineData(XSSFWorkbook workbook, Sheet sheet, Long type, List<Discipline> disciplineList, int startRow, int semesterNum, CellStyle defaultText) {
        int index = 0;

        int examCount = 0;
        int reportCount = 0;
        AtomicInteger countCP = new AtomicInteger();
        AtomicInteger countCW = new AtomicInteger();

        Map<Integer, List<Discipline>> disciplineDict = groupAndSortDisciplineList(disciplineList, type);

        for (Integer subject : disciplineDict.keySet()) {
            StringBuilder reportingSB = new StringBuilder("");
            StringBuilder examSB = new StringBuilder("");
            int reportingRow = 0;
            for (Discipline discipline : disciplineDict.get(subject)) {
                setTextByColumnNum(sheet, 2, startRow + index, discipline.getSubjectName().getName(), defaultText);
                int finalIndex = index;
                String disciplineOPPNum = discipline.getDisciplineType().getCipher() + " " + discipline.getDisciplineNum() + "." + discipline.getDisciplineSubNum();
                setTextByColumnNum(sheet, 1, startRow + finalIndex, disciplineOPPNum, defaultText);
                setTextByColumnNum(sheet, 3, startRow + finalIndex, discipline.getDepartment().getAbbreviation(), defaultText);


                if (discipline.getReporting() != null) {
                    long reportType = discipline.getReporting().getDisciplineReportingForm().getId();

                    if (reportType == 1L) {
                        examCount++;
                        examSB = examSB.length() > 0 ? examSB.append(" ").append(discipline.getSemester()) : examSB.append(discipline.getSemester());
                    } else {
                        reportCount++;
                        if (reportingSB.length() > 0) {
                            reportingSB = reportType == 3L ? reportingSB.append(" ").append(discipline.getSemester()).append("д") : reportingSB.append(" ").append(discipline.getSemester());
                        } else {
                            reportingSB = reportType == 3L ? reportingSB.append(discipline.getSemester()).append("д") : reportingSB.append(discipline.getSemester());
                        }
                    }

                    reportingRow = startRow + finalIndex;
                }


                if (discipline.getPersonalTasksSet().size() > 0) {
                    setPersonalTasksData(sheet, discipline, startRow, finalIndex, defaultText, countCP, countCW, 12,9,10,11);
                }

                if (discipline.getAuditoryHoursSet().size() > 0) {
                    discipline.getAuditoryHoursSet().stream().forEach(item -> {
                        if (item.getDisciplineForm().getId() == 1L)  // lecture
                            setDoubleByColumnNum(sheet, SEMESTER_START + discipline.getSemester() * 4 - 3, startRow + finalIndex, item.getHoursNum(), defaultText);
                        else if (item.getDisciplineForm().getId() == 2L) // labs
                            setDoubleByColumnNum(sheet, SEMESTER_START + discipline.getSemester() * 4 - 1, startRow + finalIndex, item.getHoursNum(), defaultText);
                        else if (item.getDisciplineForm().getId() == 3L)  // practice
                            setDoubleByColumnNum(sheet, SEMESTER_START + discipline.getSemester() * 4 - 2, startRow + finalIndex, item.getHoursNum(), defaultText);
                    });
                }

                if (discipline.getIndependentHours() != null) {
                    setDoubleByColumnNum(sheet, SEMESTER_START + discipline.getSemester() * 4, startRow + finalIndex, Math.ceil(discipline.getIndependentHours().getHoursNum()), defaultText);
                }

                int formulaRow = startRow + finalIndex;
                setAuditoryHoursFormula(workbook, sheet, 16, fullPlanLectureCellLetterMap, formulaRow, semesterNum, defaultText);
                setAuditoryHoursFormula(workbook, sheet, 17, fullPlanPracticeCellLetterMap, formulaRow, semesterNum, defaultText);
                setAuditoryHoursFormula(workbook, sheet, 18, fullPlanLabCellLetterMap, formulaRow, semesterNum, defaultText);
                setIndependentHoursFormula(workbook, sheet, 19, formulaRow, semesterNum, defaultText);
                setFormula(workbook, sheet, 15, formulaRow, "SUM(P" + formulaRow + ":R" + formulaRow + ")", defaultText);
                setFormula(workbook, sheet, 14, formulaRow, "$O$" + formulaRow + "+$S$" + formulaRow, defaultText);
                setFormula(workbook, sheet, 13, formulaRow, "$N" + formulaRow + "/30", defaultText);
                setFormula(workbook, sheet, 20, formulaRow, "($S$" + formulaRow + "*100)/$N$" + formulaRow, defaultText);
            }

            if (reportingSB.length() > 0) {
                setTextByColumnNum(sheet, REPORTING_COL, reportingRow, reportingSB.toString(), defaultText);
            }

            if (examSB.length() > 0) {
                setTextByColumnNum(sheet, EXAM_COL, reportingRow, examSB.toString(), defaultText);
            }
            index++;
        }

        int formulaRow = startRow + index;
        int to = startRow + index - 1;

        setIntegerByColumnNum(sheet, CP_COL, formulaRow, countCP.get(), defaultText);
        setIntegerByColumnNum(sheet, CW_COL, formulaRow, countCW.get(), defaultText);
        setIntegerByColumnNum(sheet, EXAM_COL, formulaRow, examCount, defaultText);
        setIntegerByColumnNum(sheet, REPORTING_COL, formulaRow, reportCount, defaultText);

        calculateFullCycleHours(workbook, sheet, formulaRow, startRow, formulaRow - 1, defaultText);


        if (type == 1L) {
            calculateCycleHoursSum(workbook, sheet, semesterNum, formulaRow, startRow, to, defaultText);
            generalSumRow = formulaRow;
            index++;
        } else if (type == 2L) {
            calculateCycleHoursSum(workbook, sheet, semesterNum, formulaRow, startRow, to, defaultText);
            calculateHoursSumByCycle(workbook, sheet, semesterNum, formulaRow + 1, generalSumRow, formulaRow, defaultText);
            calculateAuditoryHoursSum(workbook, sheet, semesterNum, formulaRow + 2, formulaRow + 1, formulaRow + 1, defaultText);
            calculateReportingHoursAndPersonalTaskSum(workbook, sheet, formulaRow + 1, generalSumRow, formulaRow, defaultText);
            professionalSumRow = formulaRow + 2;
            index += 3;
        } else if (type == 3L) {
            calculateChosenCycle(workbook, sheet, semesterNum, formulaRow, startRow, to, defaultText);
            calculateHoursSumByCycle(workbook, sheet, semesterNum, formulaRow + 1, professionalSumRow, formulaRow, defaultText);
            calculateIndependentHoursFullSum(workbook, sheet, semesterNum, formulaRow + 1, professionalSumRow - 1, formulaRow, defaultText);
            calculateReportingHoursAndPersonalTaskSum(workbook, sheet, formulaRow + 1, professionalSumRow - 1, formulaRow, defaultText);
            index++;
        }
        return startRow + index;
    }

    /**
     * Create formula for auditory hours (lecture, labs and practice) for full plan file
     * @param workbook
     * @param finalSheet
     * @param col
     * @param semesterCellLetterMap
     * @param row
     * @param semesterNum
     * @param style
     */
    private void setAuditoryHoursFormula(XSSFWorkbook workbook, Sheet finalSheet, int col, Map<Integer, String> semesterCellLetterMap, int row, int semesterNum, CellStyle style) {
        StringBuilder formula = new StringBuilder();
        for (int sem = 1; sem <= semesterNum; sem++) {
            String semesterVal = "$" + semesterCellLetterMap.get(sem) + "$" + row + "*($" + fullPlanLectureCellLetterMap.get(sem) + "$6" + " - 1)";
            formula.append(semesterVal);
            if (sem < semesterNum) formula.append("+");
        }
        setFormula(workbook, finalSheet, col, row, formula.toString(), style);
    }

    /**
     * Create formula for independent hours for full plan file
     * @param workbook
     * @param finalSheet
     * @param col
     * @param row
     * @param semesterNum
     * @param style
     */
    private void setIndependentHoursFormula(XSSFWorkbook workbook, Sheet finalSheet, int col, int row, int semesterNum, CellStyle style) {
        StringBuilder formula = new StringBuilder();
        for (int sem = 1; sem <= semesterNum; sem++) {
            String semesterVal = "$" + fullPlanIndependentCellLetterMap.get(sem) + "$" + row;
            formula.append(semesterVal);
            if (sem < semesterNum) formula.append("+");
        }
        setFormula(workbook, finalSheet, col, row, formula.toString(), style);
    }

    /**
     * Create formula for each discipline cycle sours sum
     * @param workbook
     * @param sheet
     * @param formulaRow
     * @param startRow
     * @param endRow
     * @param style
     */
    private void calculateFullCycleHours(Workbook workbook, Sheet sheet, int formulaRow, int startRow, int endRow, CellStyle style) {
        setFormula(workbook, sheet, 13, formulaRow, "SUM($M$" + startRow + ":" + "$M$" + endRow + ")", style);
        setFormula(workbook, sheet, 14, formulaRow, "SUM($N$" + startRow + ":" + "$N$" + endRow + ")", style);
        setFormula(workbook, sheet, 15, formulaRow, "SUM($O$" + startRow + ":" + "$O$" + endRow + ")", style);
        setFormula(workbook, sheet, 16, formulaRow, "SUM($P$" + startRow + ":" + "$P$" + endRow + ")", style);
        setFormula(workbook, sheet, 17, formulaRow, "SUM($Q$" + startRow + ":" + "$Q$" + endRow + ")", style);
        setFormula(workbook, sheet, 18, formulaRow, "SUM($R$" + startRow + ":" + "$R$" + endRow + ")", style);
        setFormula(workbook, sheet, 19, formulaRow, "SUM($S$" + startRow + ":" + "$S$" + endRow + ")", style);
    }

    /**
     * Create formula for general and professional cycles hours sum
     * @param workbook
     * @param sheet
     * @param semesterNum
     * @param formulaRow
     * @param startRow
     * @param endRow
     * @param style
     */
    public void calculateCycleHoursSum(Workbook workbook, Sheet sheet, int semesterNum, int formulaRow, int startRow, int endRow, CellStyle style) {
        for (int sem = 1; sem <= semesterNum; sem++) {
            setFormulaByColumnLetter(workbook, sheet, fullPlanLectureCellLetterMap.get(sem), formulaRow,
                    "SUM(" + fullPlanLectureCellLetterMap.get(sem) + startRow + ":" + fullPlanLectureCellLetterMap.get(sem) + endRow + ")", style);
            setFormulaByColumnLetter(workbook, sheet, fullPlanPracticeCellLetterMap.get(sem), formulaRow,
                    "SUM(" + fullPlanPracticeCellLetterMap.get(sem) + startRow + ":" + fullPlanPracticeCellLetterMap.get(sem) + endRow + ")", style);
            setFormulaByColumnLetter(workbook, sheet, fullPlanLabCellLetterMap.get(sem), formulaRow,
                    "SUM(" + fullPlanLabCellLetterMap.get(sem) + startRow + ":" + fullPlanLabCellLetterMap.get(sem) + endRow + ")", style);
            setFormulaByColumnLetter(workbook, sheet, fullPlanIndependentCellLetterMap.get(sem), formulaRow,
                    "SUM(" + fullPlanIndependentCellLetterMap.get(sem) + startRow + ":" + fullPlanIndependentCellLetterMap.get(sem) + endRow + ")", style);
        }
    }

    /**
     * Create formula for general auditory hors sum of chosen cycle
     * @param workbook
     * @param sheet
     * @param semesterNum
     * @param formulaRow
     * @param startRow
     * @param endRow
     * @param style
     */
    private void calculateChosenCycle(XSSFWorkbook workbook, Sheet sheet, int semesterNum, int formulaRow, int startRow, int endRow, CellStyle style) {
        for (int sem = 1; sem <= semesterNum; sem++) {
            setFormulaByColumnLetter(workbook, sheet, fullPlanLectureCellLetterMap.get(sem), formulaRow,
                    "SUM(" + fullPlanLectureCellLetterMap.get(sem) + startRow + ":" + fullPlanLectureCellLetterMap.get(sem) + endRow + ")" + "+" +
                            "SUM(" + fullPlanPracticeCellLetterMap.get(sem) + startRow + ":" + fullPlanPracticeCellLetterMap.get(sem) + endRow + ")" + "+" +
                            "SUM(" + fullPlanLabCellLetterMap.get(sem) + startRow + ":" + fullPlanLabCellLetterMap.get(sem) + endRow + ")", style);
        }
    }

    /**
     * Create formula form hours sum of several cycle
     * @param workbook
     * @param sheet
     * @param semesterNum
     * @param formulaRow
     * @param startRow
     * @param endRow
     * @param style
     */
    private void calculateHoursSumByCycle(Workbook workbook, Sheet sheet, int semesterNum, int formulaRow, int startRow, int endRow, CellStyle style) {
        for (int sem = 1; sem <= semesterNum; sem++) {
            setFormulaByColumnLetter(workbook, sheet, fullPlanLectureCellLetterMap.get(sem), formulaRow,
                    fullPlanLectureCellLetterMap.get(sem) + startRow + "+" + fullPlanLectureCellLetterMap.get(sem) + endRow, style);
            setFormulaByColumnLetter(workbook, sheet, fullPlanPracticeCellLetterMap.get(sem), formulaRow,
                    fullPlanPracticeCellLetterMap.get(sem) + startRow + "+" + fullPlanPracticeCellLetterMap.get(sem) + endRow, style);
            setFormulaByColumnLetter(workbook, sheet, fullPlanLabCellLetterMap.get(sem), formulaRow,
                    fullPlanLabCellLetterMap.get(sem) + startRow + "+" + fullPlanLabCellLetterMap.get(sem) + endRow, style);
            setFormulaByColumnLetter(workbook, sheet, fullPlanIndependentCellLetterMap.get(sem), formulaRow,
                    fullPlanIndependentCellLetterMap.get(sem) + startRow + "+" + fullPlanIndependentCellLetterMap.get(sem) + endRow, style);
        }
    }

    /**
     * Create formula for hours sum of general and professional cycle
     * @param workbook
     * @param sheet
     * @param semesterNum
     * @param formulaRow
     * @param startRow
     * @param endRow
     * @param style
     */
    public void calculateAuditoryHoursSum(Workbook workbook, Sheet sheet, int semesterNum, int formulaRow, int startRow, int endRow, CellStyle style) {
        for (int sem = 1; sem <= semesterNum; sem++) {
            setFormulaByColumnLetter(workbook, sheet, fullPlanLectureCellLetterMap.get(sem), formulaRow,
                    "SUM(" + fullPlanLectureCellLetterMap.get(sem) + startRow + ":" + fullPlanLabCellLetterMap.get(sem) + endRow + ")", style);
        }
    }

    /**
     * Create formula for independent hours sum of all cycles
     * @param workbook
     * @param sheet
     * @param semesterNum
     * @param formulaRow
     * @param startRow
     * @param endRow
     * @param style
     */
    public void calculateIndependentHoursFullSum(Workbook workbook, Sheet sheet, int semesterNum, int formulaRow, int startRow, int endRow, CellStyle style) {
        for (int sem = 1; sem <= semesterNum; sem++) {
            setFormulaByColumnLetter(workbook, sheet, fullPlanIndependentCellLetterMap.get(sem), formulaRow, fullPlanIndependentCellLetterMap.get(sem) + startRow + "+" + fullPlanIndependentCellLetterMap.get(sem) + endRow, style);
        }
    }

    public void calculateReportingHoursAndPersonalTaskSum(Workbook workbook, Sheet sheet, int formulaRow, int startRow, int endRow, CellStyle style) {
        setFormula(workbook, sheet, CP_COL, formulaRow, "$I$" + startRow + "+" + "$I$" + endRow, style);
        setFormula(workbook, sheet, CW_COL, formulaRow, "$J$" + startRow + "+" + "$J$" + endRow, style);
        setFormula(workbook, sheet, EXAM_COL, formulaRow, "$G$" + startRow + "+" + "$G$" + endRow, style);
        setFormula(workbook, sheet, REPORTING_COL, formulaRow, "$H$" + startRow + "+" + "$H$" + endRow, style);

        setFormula(workbook, sheet, 13, formulaRow, "$M$" + startRow + "+" + "$M$" + endRow, style);
        setFormula(workbook, sheet, 14, formulaRow, "$N$" + startRow + "+" + "$N$" + endRow, style);
        setFormula(workbook, sheet, 15, formulaRow, "$O$" + startRow + "+" + "$O$" + endRow, style);
        setFormula(workbook, sheet, 16, formulaRow, "$P$" + startRow + "+" + "$P$" + endRow, style);
        setFormula(workbook, sheet, 17, formulaRow, "$Q$" + startRow + "+" + "$Q$" + endRow, style);
        setFormula(workbook, sheet, 18, formulaRow, "$R$" + startRow + "+" + "$R$" + endRow, style);
        setFormula(workbook, sheet, 19, formulaRow, "$S$" + startRow + "+" + "$S$" + endRow, style);
    }

    private void calculateFullPlanHours(Workbook workbook, Sheet sheet, int startRow, int semesterNum, CellStyle style) {
        for (int sem = 1; sem <= semesterNum; sem++) {
            setFormulaByColumnLetter(workbook, sheet, fullPlanLectureCellLetterMap.get(sem), startRow + 1,
                    "(" + fullPlanLectureCellLetterMap.get(sem) + (startRow - 1) + "*(" + fullPlanLectureCellLetterMap.get(sem) + 6 + "-1)+" +
                            fullPlanIndependentCellLetterMap.get(sem) + (startRow - 1) + ")/30", style);
            if (sem % 2 == 1) {
                setFormulaByColumnLetter(workbook, sheet, fullPlanLectureCellLetterMap.get(sem), startRow,
                        fullPlanLectureCellLetterMap.get(sem) + (startRow - 1) + "*(" + fullPlanLectureCellLetterMap.get(sem) + 6 + "-1)+" +
                                fullPlanIndependentCellLetterMap.get(sem) + (startRow - 1) + "+" + fullPlanLectureCellLetterMap.get(sem + 1) + (startRow - 1) +
                                "*(" + fullPlanLectureCellLetterMap.get(sem + 1) + 6 + "-1)+" + fullPlanIndependentCellLetterMap.get(sem + 1) + (startRow - 1), style);
                setFormulaByColumnLetter(workbook, sheet, fullPlanLectureCellLetterMap.get(sem), startRow + 2,
                        fullPlanLectureCellLetterMap.get(sem) + startRow + "/30", style);
                setFormulaByColumnLetter(workbook, sheet, fullPlanLectureCellLetterMap.get(sem),startRow + 4,
                        "(" + fullPlanLectureCellLetterMap.get(sem) + (startRow - 2) + "*(" + fullPlanLectureCellLetterMap.get(sem) + 6 + "-1)+" +
                                fullPlanIndependentCellLetterMap.get(sem) + (startRow - 1) + "+" + fullPlanLectureCellLetterMap.get(sem + 1) + (startRow - 2) + "*(" +
                                fullPlanLectureCellLetterMap.get(sem + 1) + 6 + "-1)+" + fullPlanIndependentCellLetterMap.get(sem + 1) + (startRow - 1) + ")/30", style);

            }
        }
    }


    /**
     * Group discipline by type and sort by num
     * @param disciplineList
     * @param type
     * @return
     */
    private Map<Integer, List<Discipline>> groupAndSortDisciplineList(List<Discipline> disciplineList, Long type) {
        if (type == 3L) {
            return disciplineList.stream().collect(Collectors.groupingBy(Discipline::getDisciplineNum));
        } else {
            return disciplineList.stream().collect(Collectors.groupingBy(Discipline::getDisciplineSubNum));
        }
    }

    private void setPersonalTasksData(Sheet sheet, Discipline discipline, int startRow, int finalIndex, CellStyle defaultText, AtomicInteger countCP, AtomicInteger countCW, int courseColumn, int cpCell, int cwCell, int otherCell) {
        discipline.getPersonalTasksSet().stream().forEach(item -> {
            if (item.getPersonalTaskForm().getId() == 1L) {
                countCP.getAndIncrement();
                setIntegerByColumnNum(sheet, cpCell, startRow + finalIndex, discipline.getSemester(), defaultText);
            } else if (item.getPersonalTaskForm().getId() == 2L) {
                countCW.getAndIncrement();
                setIntegerByColumnNum(sheet, cwCell, startRow + finalIndex, discipline.getSemester(), defaultText);
            } else {
                setIntegerByColumnNum(sheet, otherCell, startRow + finalIndex, discipline.getSemester(), defaultText);
            }

            setTextByColumnNum(sheet, courseColumn, startRow + finalIndex, item.getPersonalTaskForm().getName(), defaultText);
        });
    }


    /**  Methods for writing data and formula to xlsx document*/

    private void setTextByColumnLetter(Sheet sheet, String col, int rowNum, Object data, CellStyle style) {
        CellReference cr = new CellReference(col + rowNum);
        Row row = sheet.getRow(cr.getRow());
        Cell cell = row.createCell(cr.getCol());
        cell.setCellValue(String.valueOf(data));
        cell.setCellStyle(style);
    }

    private void setTextByColumnNum(Sheet sheet, int col, int row, String data, CellStyle style) {
        Cell cell = sheet.getRow(row - 1).createCell(col - 1);
        cell.setCellValue(data);
        cell.setCellStyle(style);
    }

    private void setIntegerByColumnNum(Sheet sheet, int col, int row, int data, CellStyle style) {
        Cell cell = sheet.getRow(row - 1).createCell(col - 1);
        cell.setCellValue(data);
        cell.setCellStyle(style);
    }

    private void setDoubleByColumnNum(Sheet sheet, int col, int row, double data, CellStyle style) {
        Cell cell = sheet.getRow(row - 1).createCell(col - 1);
        cell.setCellValue(data);
        cell.setCellStyle(style);
        cell.setCellStyle(style);
    }

    private void setFormula(Workbook wb, Sheet sheet, int col, int row, String formula, CellStyle style) {
        Cell formulaCell = sheet.getRow(row - 1).createCell(col - 1);
        formulaCell.setCellFormula(formula);
        XSSFFormulaEvaluator formulaEvaluator = (XSSFFormulaEvaluator) wb.getCreationHelper().createFormulaEvaluator();
        formulaEvaluator.evaluateFormulaCell(formulaCell);
        formulaCell.setCellStyle(style);
    }

    private void setFormulaByColumnLetter(Workbook wb, Sheet sheet, String col, int rowNum, String formula, CellStyle style) {
        CellReference cr = new CellReference(col + rowNum);
        Row row = sheet.getRow(cr.getRow());
        Cell formulaCell = row.createCell(cr.getCol());

        formulaCell.setCellFormula(formula);
        XSSFFormulaEvaluator formulaEvaluator = (XSSFFormulaEvaluator) wb.getCreationHelper().createFormulaEvaluator();
        formulaEvaluator.evaluateFormulaCell(formulaCell);
        formulaCell.setCellStyle(style);
    }
}