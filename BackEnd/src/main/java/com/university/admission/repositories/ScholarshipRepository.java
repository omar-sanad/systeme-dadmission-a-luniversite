package com.university.admission.repositories;

import com.university.admission.models.Scholarship;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScholarshipRepository extends CrudRepository<Scholarship, Long> {
}
