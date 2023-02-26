package com.departmenteom.entity;

import com.departmenteom.entity.dictionary.DisciplineForm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "auditory_hours")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuditoryHours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "discipline_id")
    private Discipline disciplineAuditoryHours;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_id")
    private DisciplineForm disciplineForm;

    @Column(name = "hours_num")
    private double hoursNum;

    public AuditoryHours(Discipline discipline, DisciplineForm disciplineForm, double hours) {
        this.disciplineAuditoryHours = discipline;
        this.disciplineForm = disciplineForm;
        this.hoursNum = hours;
    }

    @Override
    public String toString() {
        return "AuditoryHours(id = " + id +
                ", discipline = " + disciplineAuditoryHours +
                ", disciplineForm = " + disciplineForm +
                ", hoursNum = " + hoursNum +
                ")";
    }

}
