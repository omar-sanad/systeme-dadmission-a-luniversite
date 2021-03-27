package com.university.admission.services;

import com.university.admission.models.Administrator;
import com.university.admission.models.Student;
import com.university.admission.repositories.AdministratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdministratorServiceImplementation implements AdministratorService {
    @Autowired
    private AdministratorRepository repository;

    @Override
    public List<Administrator> findAll() {
        return (List<Administrator>) repository.findAll();
    }

    @Override
    public Administrator findById(long id) {
        Optional<Administrator> result = repository.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            return null;
        }
    }

    @Override
    public Administrator insert(Administrator element) {
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
    public boolean update(Administrator element) {
        try {
            repository.save(element);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Administrator findByEmail(String email) {
        List<Administrator> list = (List<Administrator>) repository.findAll();
        for (Administrator element : list) {
            if (element.getEmail().equals(email))
                return element;
        }
        return null;
    }
}
