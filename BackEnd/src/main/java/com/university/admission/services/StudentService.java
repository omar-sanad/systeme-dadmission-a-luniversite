package com.university.admission.services;

import com.university.admission.models.Student;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {
    List<Student> findAll();
    List<Student> findAllByEstablishment(long id);
    Student findById(long id);
    Student findByEmail(String email);
    Student insert(Student element);
    boolean delete(long id);
    boolean update(Student element);
    boolean sendMail(String to, String subject, String body);
    boolean sendSms(String to, String from, String body);
}
