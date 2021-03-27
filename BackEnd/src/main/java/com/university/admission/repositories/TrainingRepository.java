package com.university.admission.repositories;

import com.university.admission.models.Training;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingRepository extends CrudRepository<Training, Long> {
}
