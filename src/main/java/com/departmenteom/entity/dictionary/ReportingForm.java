package com.departmenteom.entity.dictionary;

import com.departmenteom.entity.Reporting;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "dictionary_reporting_form")
@Getter
@Setter
public class ReportingForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "disciplineReportingForm")
    private Set<Reporting> reportingSet = new HashSet<>();

    @Override
    public String toString() {
        return "ReportingForm(id = " + id + ", name = " + name +  ")";
    }
}
