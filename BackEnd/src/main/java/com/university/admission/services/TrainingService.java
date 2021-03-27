package com.university.admission.services;

import com.university.admission.models.Training;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TrainingService {
    List<Training> findAll();
    Training findById(long id);
    List<Training>  findByEstablishment(long id);
    Training insert(Training element);
    boolean delete(long id);
    boolean update(Training element);
}
