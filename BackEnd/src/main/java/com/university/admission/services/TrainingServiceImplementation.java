package com.university.admission.services;

import com.university.admission.models.Training;
import com.university.admission.repositories.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TrainingServiceImplementation implements TrainingService {
    @Autowired
    private TrainingRepository repository;

    @Override
    public List<Training> findAll() {
        return (List<Training>) repository.findAll();
    }

    @Override
    public Training findById(long id) {
        Optional<Training> result = repository.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            return null;
        }
    }

    @Override
    public List<Training> findByEstablishment(long id) {
        List<Training> trainings = (List<Training>) repository.findAll();
        List<Training> returned = new ArrayList<>();
        for (Training training : trainings) {
            if (training.getEstablishment() == id)
                returned.add(training);
        }
        return returned;
    }

    @Override
    public Training insert(Training element) {
        return repository.save(element);
    }

    @Override
    public boolean delete(long id) {
        try {
            repository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Training element) {
        try {
            repository.save(element);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
