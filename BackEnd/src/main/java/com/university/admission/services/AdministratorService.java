package com.university.admission.services;

import com.university.admission.models.Administrator;
import com.university.admission.models.Student;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdministratorService {
    List<Administrator> findAll();
    Administrator findById(long id);
    Administrator findByEmail(String email);
    Administrator insert(Administrator element);
    boolean delete(long id);
    boolean update(Administrator element);
}
