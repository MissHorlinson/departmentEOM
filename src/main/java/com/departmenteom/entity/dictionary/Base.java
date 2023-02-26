package com.departmenteom.entity.dictionary;


import com.departmenteom.entity.PlanInfo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "dictionary_base")
@Getter
@Setter
public class Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "base")
    private Set<PlanInfo> planInfoSet = new HashSet<>();

    @Override
    public String toString() {
        return "Base(id = " + id + ", name = " + name + ")";
    }
}
