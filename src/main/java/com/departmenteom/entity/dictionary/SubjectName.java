package com.departmenteom.entity.dictionary;

import com.departmenteom.entity.Discipline;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "dictionary_subject_name")
@Getter
@Setter
public class SubjectName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "subjectName")
    private Set<Discipline> disciplineSet = new HashSet<>();

    @Override
    public String toString() {
        return "SubjectName(id = " + id + ", name = " + name + ")";
    }
}
