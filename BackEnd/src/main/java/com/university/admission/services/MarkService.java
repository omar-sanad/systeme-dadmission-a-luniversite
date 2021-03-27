package com.university.admission.services;

import com.university.admission.models.Mark;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MarkService {
    List<Mark> findAll();
    Mark findById(long id);
    Mark findByStudentId(long id);
    Mark findByStudent(long id);
    Mark insert(Mark element);
    boolean delete(long id);
    boolean update(Mark element);
}
