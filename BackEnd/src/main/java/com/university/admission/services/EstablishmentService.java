package com.university.admission.services;

import com.university.admission.models.Establishment;
import com.university.admission.models.Student;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EstablishmentService {
    List<Establishment> findAll();
    Establishment findById(long id);
    Establishment findByName(String name);
    Establishment insert(Establishment element);
    boolean delete(long id);
    boolean update(Establishment element);
}
