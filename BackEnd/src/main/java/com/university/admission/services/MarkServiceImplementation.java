package com.university.admission.services;

import com.university.admission.models.Mark;
import com.university.admission.models.Student;
import com.university.admission.repositories.MarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MarkServiceImplementation implements MarkService {
    @Autowired
    private MarkRepository repository;

    @Override
    public List<Mark> findAll() {
        return (List<Mark>) repository.findAll();
    }

    @Override
    public Mark findById(long id) {
        Optional<Mark> result = repository.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            return null;
        }
    }

    @Override
    public Mark findByStudentId(long id) {
        List<Mark> marks = findAll();
        for (Mark mark : marks) {
            if (mark.getStudent() == id)
                return mark;
        }
        return null;
    }

    @Override
    public Mark findByStudent(long id) {
        List<Mark> list = (List<Mark>) repository.findAll();
        for (Mark element : list) {
            if (element.getStudent() == id)
                return element;
        }
        return null;
    }

    @Override
    public Mark insert(Mark element) {
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
    public boolean update(Mark element) {
        try {
            repository.save(element);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
