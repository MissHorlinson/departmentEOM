package com.departmenteom.repo;

import com.departmenteom.entity.UsersCred;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersCredRepo extends CrudRepository<UsersCred, Long> {

    Optional<UsersCred> findByUsername(String username);
}
