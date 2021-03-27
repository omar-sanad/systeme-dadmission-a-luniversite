package com.university.admission.controllers;

import com.alibaba.fastjson.JSON;
import com.university.admission.models.Establishment;
import com.university.admission.models.Training;
import com.university.admission.services.TrainingService;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

@RestController()
@RequestMapping("/api/trainings")
public class TrainingController {

    final TrainingService trainingService;

    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @GetMapping("")
    public List<Training> getAll() {
        return trainingService.findAll();
    }

    @GetMapping("establishment/{id}")
    public List<Training> getAllByEstablishment(@PathVariable long id) {
        return trainingService.findByEstablishment(id);
    }

    @GetMapping("{id}")
    public Training get(@PathVariable long id) {
        return trainingService.findById(id);
    }

    @PostMapping("")
    public String add(@RequestBody Training element) {
        trainingService.insert(element);
        HashMap<String, String> message = new HashMap<>();
        message.put("message", "success");
        message.put("code", "1");
        return JSON.toJSONString(message);
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") long id) {
        HashMap<String, String> message = new HashMap<>();
        if (trainingService.delete(id)) {
            message.put("message", "success");
            message.put("code", "1");
        } else {
            message.put("message", "error");
            message.put("code", "2");
        }
        return JSON.toJSONString(message);
    }

    @PutMapping("")
    public String update(@RequestBody Training element) {
        HashMap<String, String> message = new HashMap<>();
        if (trainingService.update(element)) {
            message.put("code", "1");
            message.put("message", "success");
        } else {
            message.put("code", "2");
            message.put("message", "error");
        }
        return JSON.toJSONString(message);
    }

}

