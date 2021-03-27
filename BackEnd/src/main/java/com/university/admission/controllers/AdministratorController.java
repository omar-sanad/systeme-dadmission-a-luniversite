package com.university.admission.controllers;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.university.admission.models.Administrator;
import com.university.admission.models.Student;
import com.university.admission.services.AdministratorService;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController()
@RequestMapping("/api/administrators")
public class AdministratorController {
    final AdministratorService administratorService;

    public AdministratorController(AdministratorService administratorService) {
        this.administratorService = administratorService;
    }

    @GetMapping("")
    public List<Administrator> getAll() {
        return administratorService.findAll();
    }

    @GetMapping("{id}")
    public Administrator get(@PathVariable long id) {
        Administrator administrator = administratorService.findById(id);
        administrator.setPassword("");
        return administrator;
    }

    @PostMapping("")
    public String add(@RequestBody Administrator element) {
        element.setPassword(BCrypt.withDefaults().hashToString(12, element.getPassword().toCharArray()));
        administratorService.insert(element);
        HashMap<String, String> message = new HashMap<>();
        message.put("message", "success");
        return JSON.toJSONString(message);
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") long id) {
        HashMap<String, String> message = new HashMap<>();
        if (administratorService.delete(id)) {
            message.put("message", "success");
        } else {
            message.put("message", "error");
        }
        return JSON.toJSONString(message);
    }

    @PostMapping("/login")
    public String login(@RequestBody Student element) {
        HashMap<String, String> message = new HashMap<>();
        Administrator administrator = administratorService.findByEmail(element.getEmail());
        if (administrator != null) {
            BCrypt.Result result = BCrypt.verifyer().verify(element.getPassword().toCharArray(), administrator.getPassword());
            if (result.verified) {
                String token = null;
                try {
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.MONTH, 1);
                    Date date = calendar.getTime();
                    Algorithm algorithm = Algorithm.HMAC256("yTAHbkECx?3@a^G?D5p=?rGs5crM$4=t");
                    token = JWT.create()
                            .withClaim("type", "administrator")
                            .withClaim("email", administrator.getEmail())
                            .withExpiresAt(date)
                            .sign(algorithm);
                } catch (JWTCreationException exception) {
                    exception.printStackTrace();
                }
                message.put("token", token);
                message.put("id", String.valueOf(administrator.getId()));
                message.put("message", "success");
                message.put("code", "1");
            } else {
                message.put("message", "failed");
                message.put("code", "2");
            }
        } else {
            message.put("message", "no admin with that email address");
            message.put("code", "3");
        }
        return JSON.toJSONString(message);
    }


    @PutMapping("")
    public String update(@RequestBody Administrator element) {
        HashMap<String, String> message = new HashMap<>();
        if (element != null) {
            Administrator administrator = administratorService.findById(element.getId());
            if (!element.getPassword().isEmpty()) {
                element.setPassword(BCrypt.withDefaults().hashToString(12, element.getPassword().toCharArray()));
            } else {
                element.setPassword(administrator.getPassword());
            }
            element.setEstablishment(administrator.getEstablishment());
            boolean updated = administratorService.update(element);
            if (updated) {
                message.put("message", "success");
                message.put("code", "1");
            } else {
                {
                    message.put("message", "error");
                    message.put("code", "2");
                }
            }
        }
        return JSON.toJSONString(message);
    }
}

