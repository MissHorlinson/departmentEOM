package com.departmenteom.entity;

import com.departmenteom.entity.dictionary.PersonalTaskForm;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "individual_tasks")
@Getter
@Setter
@NoArgsConstructor
public class PersonalTasks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "discipline_id")
    private Discipline disciplinePersonalTask;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "form_id")
    private PersonalTaskForm personalTaskForm;

    public PersonalTasks(Discipline discipline, PersonalTaskForm personalTaskForm) {
        this.disciplinePersonalTask = discipline;
        this.personalTaskForm = personalTaskForm;
    }

    @Override
    public String toString() {
        return "PersonalTasks(id = " + id +
                ", discipline = " + disciplinePersonalTask +
                ", personalTaskForm = " + personalTaskForm +
                ')';
    }
}
