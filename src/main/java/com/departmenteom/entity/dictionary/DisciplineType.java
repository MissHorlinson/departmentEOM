package com.departmenteom.entity.dictionary;

import com.departmenteom.entity.Discipline;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "dictionary_discipline_type")
@Getter
@Setter
public class DisciplineType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "disciplineType")
    private Set<Discipline> disciplineSet = new HashSet<>();

    @Override
    public String toString() {
        return "DisciplineType(id = " + id +  ", name = " + name + ")";
    }
}
