package com.university.admission.controllers;

import com.alibaba.fastjson.JSON;
import com.university.admission.models.Scholarship;
import com.university.admission.models.Training;
import com.university.admission.services.ScholarshipService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController()
@RequestMapping("/api/scholarships")
public class ScholarShipController {

    final ScholarshipService scholarshipService;

    public ScholarShipController(ScholarshipService scholarshipService) {
        this.scholarshipService = scholarshipService;
    }

    @GetMapping("")
    public List<Scholarship> getAll() {
        return scholarshipService.findAll();
    }

    @GetMapping("{id}")
    public Scholarship get(@PathVariable long id) {
        return scholarshipService.findById(id);
    }

    @PostMapping("")
    public String add(@RequestBody Scholarship element) {
        scholarshipService.insert(element);
        HashMap<String,String> message = new HashMap<>();
        message.put("message","success");
        message.put("code","1");
        return JSON.toJSONString(message);
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") long id) {
        HashMap<String,String> message = new HashMap<>();
        if (scholarshipService.delete(id)) {
            message.put("message","success");
            message.put("code","1");
        }else{
            message.put("message","error");
            message.put("code","2");
        }
        return JSON.toJSONString(message);
    }

    @PutMapping("")
    public String update(@RequestBody Scholarship element) {
        HashMap<String, String> message = new HashMap<>();
        if (scholarshipService.update(element)) {
            message.put("code", "1");
            message.put("message", "success");
        } else {
            message.put("code", "2");
            message.put("message", "error");
        }
        return JSON.toJSONString(message);
    }
}

