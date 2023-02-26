package com.departmenteom.repo;

import com.departmenteom.entity.GroupStream;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface GroupStreamRepo extends CrudRepository<GroupStream, Long> {

    GroupStream getById(long id);

    Optional<GroupStream> getByStreamPlanInfoPlanCipherIdAndStreamPlanInfoAdmissionYear(long id, LocalDateTime year);
}
