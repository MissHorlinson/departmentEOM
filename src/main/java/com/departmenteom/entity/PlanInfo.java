package com.departmenteom.entity;

import com.departmenteom.entity.dictionary.*;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "plan_info")
public class PlanInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "admission_year")
    private LocalDateTime admissionYear;

    @Column(name = "group_num")
    private int numberOfGroup;

    @Column(name = "student_num")
    private int numberOfStudent;

    @Column(name = "semester_num")
    private int numberOfSemester;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cipher_id")
    private Cipher planCipher;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "qualification_id")
    private Qualification qualification;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "base_id")
    private Base base;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "step_id")
    private Step step;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "studying_form_id")
    private StudyingForm studyingForm;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "studying_term_id")
    private StudyingTerm studyingTerm;

    @Column(name = "rector")
    private String rector;

    @OneToMany(mappedBy = "weekPlanInfo")
    private Set<WeekPlan> weekPlanSet = new HashSet<>();

    @OneToMany(mappedBy = "disciplinePlanInfo")
    private Set<Discipline> disciplineOfPlanSet = new HashSet<>();

    @OneToOne(mappedBy = "streamPlanInfo")
    private GroupStream groupStream;

    public Set<WeekPlan> getPlanWeeks  () {
        return weekPlanSet;
    }

    public Set<Discipline> getPlanDisciplines  () {
        return disciplineOfPlanSet;
    }


//    @Override
//    public String toString() {
//        return "PlanInfo(id = " + id +
//                ", admissionYear = " + admissionYear +
//                ", numberOfGroup = " + numberOfGroup +
//                ", numberOfStudent = " + numberOfStudent +
//                ", numberOfSemester = " + numberOfSemester +
//                ", qualification = " + qualification +
//                ", planCipherId = " + planCipher +
//                ", base = " + base +
//                ", step = " + step +
//                ", studyingForm = " + studyingForm +
//                ", studyingTerm = " + studyingTerm +
////                ", weekPlanSetSize = " + weekPlanSet.size() +
////                ", disciplineOfPlanSetSize = " + disciplineOfPlanSet.size() +
//                ")";
//    }

}
