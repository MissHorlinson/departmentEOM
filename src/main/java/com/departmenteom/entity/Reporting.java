package com.departmenteom.entity;


import com.departmenteom.entity.dictionary.ReportingForm;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "reporting")
@Getter
@Setter
public class Reporting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @OneToOne
    @JoinColumn(name = "discipline_id")
    private Discipline disciplineReporting;

    @ManyToOne
    @JoinColumn(name = "form_id")
    private ReportingForm disciplineReportingForm;

    @Override
    public String toString() {
        return "Reporting(id = " + id +
                ", disciplineReporting = " + disciplineReporting +
                ", disciplineReportingForm = " + disciplineReportingForm +
                ")";
    }
}
