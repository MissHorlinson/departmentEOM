package com.departmenteom.entity.dictionary;

import com.departmenteom.entity.PlanInfo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "dictionary_step")
@Getter
@Setter
public class Step {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "step")
    private Set<PlanInfo> planInfoSet = new HashSet<>();

    @Override
    public String toString() {
        return "Step( id = " + id + ", name = " + name + ")";
    }
}
