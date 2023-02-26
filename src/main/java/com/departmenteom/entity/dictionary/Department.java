package com.departmenteom.entity.dictionary;

import com.departmenteom.entity.Discipline;
import com.departmenteom.entity.Teacher;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "dictionary_department")
@Getter
@Setter
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "abbreviation")
    private String abbreviation;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "departmentInfo")
    private Set<Teacher> teacherSet = new HashSet<>();

    @OneToMany(mappedBy = "department")
    private Set<Discipline> disciplineSet = new HashSet<>();

    public Set<Teacher> addTeacher(Teacher teacher) {
        teacherSet.add(teacher);
        return teacherSet;
    }

    @Override
    public String toString() {
        return "Department(id = " + id + ", name = " + name + ", abbreviation = " + abbreviation + ")";
    }
}
