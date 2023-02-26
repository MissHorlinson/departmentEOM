package com.departmenteom.entity.dictionary;

import com.departmenteom.entity.GroupInfo;
import com.departmenteom.entity.PlanInfo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "dictionary_cipher")
@Getter
@Setter
public class Cipher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "speciality_code")
    private String specialityCode;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "planCipher")
    private Set<PlanInfo> planInfoSet = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "groupCipher")
    private Set<GroupInfo> groupInfoSet = new HashSet<>();

    @Override
    public String toString() {
        return "Cipher(id = " + id + ", name = " + name + " specialityCode = " + specialityCode + ")";
    }
}
