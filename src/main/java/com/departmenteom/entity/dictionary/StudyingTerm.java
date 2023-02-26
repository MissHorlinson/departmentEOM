package com.departmenteom.entity.dictionary;

import com.departmenteom.entity.PlanInfo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "dictionary_studying_term")
@Getter
@Setter
public class StudyingTerm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "term_in_month")
    private int termInMonth;

    @Column(name = "semester_num")
    private int semesterNum;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "studyingTerm")
    private Set<PlanInfo> planInfoSet = new HashSet<>();

    @Override
    public String toString() {
        return "StudyingTerm( id = " + id + ", name = " + name + " monthNum = " + termInMonth + " semesterNum = " + semesterNum + ")";
    }
}
