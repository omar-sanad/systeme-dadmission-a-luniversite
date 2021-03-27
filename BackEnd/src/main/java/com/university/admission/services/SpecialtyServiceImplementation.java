package com.university.admission.services;

import com.university.admission.models.Specialty;
import com.university.admission.repositories.SpecialtyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpecialtyServiceImplementation implements SpecialtyService {
    @Autowired
    private SpecialtyRepository repository;

    @Override
    public List<Specialty> findAll() {
        return (List<Specialty>) repository.findAll();
    }

    @Override
    public Specialty findById(long id) {
        Optional<Specialty> result = repository.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            return null;
        }
    }

    @Override
    public Specialty insert(Specialty element) {
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
    public boolean update(Specialty element) {
        try {
            repository.save(element);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
