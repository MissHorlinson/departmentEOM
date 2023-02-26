package com.departmenteom.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "discipline_student_teacher")
@Getter
@Setter
@ToString
public class DisciplineStudentTeachet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
//    @Column(name = "dicsipline_id")
//    private Discipline discipline;

//    @Column(name = "student_id")
//    private Student student;

//    @Column(name = "teacher_id")
//    private Teacher teacher;

    @Column(name = "isNeeded")
    private boolean isNeeded;
}
