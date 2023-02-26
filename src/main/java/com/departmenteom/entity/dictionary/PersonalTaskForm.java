package com.departmenteom.entity.dictionary;

import com.departmenteom.entity.PersonalTasks;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "dictionary_individual_task")
@Getter
@Setter
public class PersonalTaskForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "personalTaskForm")
    private Set<PersonalTasks> personalTasksSet = new HashSet<>();

    @Override
    public String toString() {
        return "PersonalTaskForm(id = " + id + ", name = " + name +  ")";
    }
}
