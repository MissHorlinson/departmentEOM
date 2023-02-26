package com.departmenteom.entity.dictionary;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "dictionary_degree")
@Getter
@Setter
@ToString
public class Degree {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "name")
    private String name;

//    @Override
//    public String toString() {
//        return "Degree(id = " + id + ", name = " + name + ")";
//    }
}
