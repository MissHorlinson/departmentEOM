package com.departmenteom.entity;

import com.departmenteom.entity.dictionary.Cipher;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
@Table(name = "group_info")
public class GroupInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cipher_id")
    private Cipher groupCipher;


    @Column(name = "admission_year")
    private LocalDateTime admissionYear;

    @Column(name = "group_index")
    private int index;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "groupInfo")
    private Set<Student> studentSet = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "stream_id")
    private GroupStream groupInfoStream;
}