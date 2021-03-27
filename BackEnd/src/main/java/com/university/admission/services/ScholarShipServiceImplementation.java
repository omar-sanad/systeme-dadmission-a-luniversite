package com.university.admission.services;

import com.university.admission.models.Scholarship;
import com.university.admission.models.Scholarship;
import com.university.admission.repositories.ScholarshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScholarShipServiceImplementation implements ScholarshipService {
    @Autowired
    private ScholarshipRepository repository;

    @Override
    public List<Scholarship> findAll() {
        return (List<Scholarship>) repository.findAll();
    }

    @Override
    public Scholarship findById(long id) {
        Optional<Scholarship> result = repository.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            return null;
        }
    }

    @Override
    public Scholarship insert(Scholarship element) {
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
    public boolean update(Scholarship element) {
        try {
            repository.save(element);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
