package com.departmenteom.entity.dictionary;

import com.departmenteom.entity.AuditoryHours;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "dictionary_discipline_form")
@Getter
@Setter
public class DisciplineForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "disciplineForm")
    private Set<AuditoryHours> auditoryHoursSet = new HashSet<>();

    @Override
    public String toString() {
        return "DisciplineForm(id = " + id + ", name = " + name + ")";
    }
}
