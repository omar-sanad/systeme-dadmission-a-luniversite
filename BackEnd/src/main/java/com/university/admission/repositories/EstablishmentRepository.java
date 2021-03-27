package com.university.admission.repositories;

import com.university.admission.models.Establishment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstablishmentRepository extends CrudRepository<Establishment, Long> {
    //CRUD = create , read, update, delete
}
