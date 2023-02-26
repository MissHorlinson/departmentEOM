package com.departmenteom.repo.dictionary;

import com.departmenteom.entity.dictionary.Position;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionRepo extends CrudRepository<Position, Long> {
    List<Position> findAll();
}
