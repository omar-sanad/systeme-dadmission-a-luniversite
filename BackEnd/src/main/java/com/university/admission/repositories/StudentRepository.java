package com.university.admission.repositories;

import com.university.admission.models.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {
}
