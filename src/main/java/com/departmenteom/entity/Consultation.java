package com.departmenteom.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "consultation")
@Getter
@Setter
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

//
//    @Column(name = "discipline_id")
//    private Discipline discipline;

    @Column(name = "personal")
    private double personal;

    @Column(name = "pre_exam")
    private double preExam;

    @Column(name = "course")
    private double course;

    @Override
    public String toString() {
        return "Consultation(id = " + id +

                ", personal = " + personal +
                ", preExam = " + preExam +
                ", course = " + course +
                ")";
    }

}
