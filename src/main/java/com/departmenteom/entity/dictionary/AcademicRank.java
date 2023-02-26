package com.departmenteom.entity.dictionary;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "dictionary_academic_rank")
@Getter
@Setter
@ToString
public class AcademicRank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "name")
    private String name;

//    @Override
//    public String toString() {
//        return "AcademicRank(id = " + id + ", name = " + name + ")";
//    }
}
