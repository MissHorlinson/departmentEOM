package com.departmenteom.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "independent_hours")
@Getter
@Setter
public class IndependentHours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @OneToOne
    @JoinColumn(name = "discipline_id")
    private Discipline disciplineIndependentHours;

    @Column(name = "hours_num")
    private double hoursNum;

    @Override
    public String toString() {
        return "IndependentHours(id = " + id +
                ", discipline = " + disciplineIndependentHours +
                ", hoursNum = " + hoursNum +
                ")";
    }
}
