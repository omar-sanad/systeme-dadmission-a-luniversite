package com.university.admission.services;

import com.university.admission.models.Scholarship;
import com.university.admission.models.Scholarship;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ScholarshipService {
    List<Scholarship> findAll();
    Scholarship findById(long id);
    Scholarship insert(Scholarship element);
    boolean delete(long id);
    boolean update(Scholarship element);
}
