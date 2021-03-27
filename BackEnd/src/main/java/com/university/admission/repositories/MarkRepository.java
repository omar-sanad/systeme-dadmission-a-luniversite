package com.university.admission.repositories;

import com.university.admission.models.Mark;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarkRepository extends CrudRepository<Mark, Long> {
}
