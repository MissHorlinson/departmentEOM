package com.departmenteom.repo.dictionary;

import com.departmenteom.entity.dictionary.AcademicRank;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AcademicRankRepo extends CrudRepository<AcademicRank, Long> {
    List<AcademicRank> findAll();
}
