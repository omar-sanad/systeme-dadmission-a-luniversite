package com.university.admission.services;

import com.university.admission.models.Establishment;
import com.university.admission.repositories.EstablishmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstablishmentServiceImplementation implements EstablishmentService {
    @Autowired
    private EstablishmentRepository repository;

    @Override
    public List<Establishment> findAll() {
        return (List<Establishment>) repository.findAll();
    }

    @Override
    public Establishment findById(long id) {
        Optional<Establishment> result = repository.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            return null;
        }
    }

    @Override
    public Establishment findByName(String name) {
        List<Establishment> all = (List<Establishment>) repository.findAll();
        for (Establishment element : all) {
            if (element.getName().equals(name))
                return element;
        }
        return null;
    }

    @Override
    public Establishment insert(Establishment element) {
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
    public boolean update(Establishment element) {
        try {
            repository.save(element);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
