package com.university.admission.services;

import com.university.admission.models.Establishment;
import com.university.admission.models.Specialty;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SpecialtyService {
    List<Specialty> findAll();
    Specialty findById(long id);
    Specialty insert(Specialty element);
    boolean delete(long id);
    boolean update(Specialty element);
}
