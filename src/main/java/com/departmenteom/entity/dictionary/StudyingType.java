package com.departmenteom.entity.dictionary;

import com.departmenteom.entity.WeekPlan;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "dictionary_studying_type")
@Getter
@Setter
public class StudyingType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "letter_mean")
    private String letter;

    @OneToMany( mappedBy = "studyingType")
    private Set<WeekPlan> weekPlanSet;

    @Override
    public String toString() {
        return "StudyingType( id = " + id + ", name = " + name + ")";
    }
}
