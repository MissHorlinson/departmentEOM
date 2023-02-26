package com.departmenteom.entity;

import com.departmenteom.entity.dictionary.Department;
import com.departmenteom.entity.dictionary.DisciplineType;
import com.departmenteom.entity.dictionary.SubjectName;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "discipline")
@Getter
@Setter
public class Discipline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @ManyToOne
    @JoinColumn(name = "name_id")
    private SubjectName subjectName;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private DisciplineType disciplineType;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(name = "semester")
    private int semester;

    @Column(name = "cipher")
    private String cipher;

    @Column(name = "discipline_num")
    private int disciplineNum;

    @Column(name = "discipline_sub_num")
    private int disciplineSubNum;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private PlanInfo disciplinePlanInfo;

    @OneToOne(mappedBy = "disciplineIndependentHours")
    private IndependentHours independentHours;

    @OneToMany(mappedBy = "disciplineAuditoryHours")
    private Set<AuditoryHours> auditoryHoursSet = new HashSet<>();

    @OneToMany(mappedBy = "disciplinePersonalTask")
    private Set<PersonalTasks> personalTasksSet = new HashSet<>();

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "disciplineReporting")
    private Reporting reporting;

//    @Override
//    public String toString() {
//        return "Discipline(id= " + id +
//                ", subjectName = " + subjectName +
//                ", disciplineType = " + disciplineType +
//                ", department = " + department +
//                ", semester = " + semester +
//                ", cipher = " + cipher +
//                ", disciplineNum = " + disciplineNum +
//                ", disciplineSubNum = " + disciplineSubNum +
//                ", disciplinePlanInfo = " + disciplinePlanInfo +
////                ", auditoryHoursSetSize = " + auditoryHoursSet.size() +
////                ", personalTasksSetSize = " + personalTasksSet.size() +
//                ", independentHours = " + independentHours +
////                ", reportingSetSize = " + reportingSet.size() +
//                '}';
//    }
}
