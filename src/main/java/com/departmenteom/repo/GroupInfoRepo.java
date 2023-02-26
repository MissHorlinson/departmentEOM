package com.departmenteom.repo;

import com.departmenteom.entity.GroupInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface GroupInfoRepo extends CrudRepository<GroupInfo, Long> {

    List<GroupInfo> findAll();

    Optional<GroupInfo> getFirstByAdmissionYearAndGroupCipherId(LocalDateTime year, long id);
}
