package com.departmenteom.repo;

import com.departmenteom.entity.PlanInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanInfoRepo extends CrudRepository<PlanInfo, Long> {

    List<PlanInfo> findAll();
}
