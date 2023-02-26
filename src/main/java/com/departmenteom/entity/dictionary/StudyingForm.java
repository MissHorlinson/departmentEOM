package com.departmenteom.entity.dictionary;

import com.departmenteom.entity.PlanInfo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "dictionary_studying_form")
@Getter
@Setter
public class StudyingForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "studyingForm")
    private Set<PlanInfo> planInfoSet = new HashSet<>();

    @Override
    public String toString() {
        return "StudyingForm(id = " + id + ", name = " + name + ")";
    }
}
