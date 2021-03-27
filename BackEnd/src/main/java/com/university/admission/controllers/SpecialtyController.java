package com.university.admission.controllers;

import com.alibaba.fastjson.JSON;
import com.university.admission.models.Establishment;
import com.university.admission.models.Specialty;
import com.university.admission.models.Training;
import com.university.admission.services.EstablishmentService;
import com.university.admission.services.SpecialtyService;
import com.university.admission.services.TrainingService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController()
@RequestMapping("/api/specialties")
public class SpecialtyController {

    final SpecialtyService specialtyService;
    final EstablishmentService establishmentService;
    final TrainingService trainingService;

    public SpecialtyController(SpecialtyService specialtyService, EstablishmentService establishmentService, TrainingService trainingService) {
        this.specialtyService = specialtyService;
        this.establishmentService = establishmentService;
        this.trainingService = trainingService;
    }

    @GetMapping("")
    public List<Specialty> getAll() {
        return specialtyService.findAll();
    }

    @GetMapping("/establishment/{id}")
    public List<Specialty> getAll(@PathVariable long id) {
        List<Specialty> theSpecialties = new ArrayList<>();
        Establishment establishment = establishmentService.findById(id);
        List<Training> trainings = trainingService.findByEstablishment(establishment.getId());
        for (Training training : trainings) {
            List<Specialty> specialties = specialtyService.findAll();
            for (Specialty specialty : specialties) {
                if (specialty.getTraining().equals(training.getId())) {
                    theSpecialties.add(specialty);
                }
            }
        }
        return theSpecialties;
    }

    @GetMapping("/trainings/establishment/{id}")
    public List<Training> getAllTraining(@PathVariable long id) {
        List<Specialty> theSpecialties = new ArrayList<>();
        Establishment establishment = establishmentService.findById(id);
        List<Training> trainings = trainingService.findByEstablishment(establishment.getId());

        return trainings;
    }

    @GetMapping("/specialties/trainings/establishment/{id}")
    public List<Specialty> getAllSpecialtiesOfTraining(@PathVariable long id) {
        List<Specialty> theSpecialties = new ArrayList<>();
        List<Specialty> specialties = specialtyService.findAll();
        for (Specialty specialty : specialties) {
            if (specialty.getTraining() == id)
                theSpecialties.add(specialty);
        }
        return theSpecialties;
    }

    @GetMapping("{id}")
    public Specialty get(@PathVariable long id) {
        return specialtyService.findById(id);
    }

    @PostMapping("")
    public String add(@RequestBody Specialty element) {
        specialtyService.insert(element);
        HashMap<String, String> message = new HashMap<>();
        message.put("message", "success");
        message.put("code", "1");
        return JSON.toJSONString(message);
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") long id) {
        HashMap<String, String> message = new HashMap<>();
        if (specialtyService.delete(id)) {
            message.put("message", "success");
            message.put("code", "1");
        } else {
            message.put("message", "error");
            message.put("code", "2");
        }
        return JSON.toJSONString(message);
    }

    @PutMapping("")
    public String update(@RequestBody Specialty element) {
        HashMap<String, String> message = new HashMap<>();
        if (specialtyService.update(element)) {
            message.put("code", "1");
            message.put("message", "success");
        } else {
            message.put("code", "2");
            message.put("message", "error");
        }
        return JSON.toJSONString(message);
    }
}

