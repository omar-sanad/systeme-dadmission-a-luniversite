package com.university.admission.controllers;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.university.admission.models.Mark;
import com.university.admission.models.Student;
import com.university.admission.services.MarkService;
import com.university.admission.services.StudentService;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController()
@RequestMapping("/api/marks")
public class MarkController {

    final MarkService markService;
    final StudentService studentService;

    public MarkController(MarkService markService, StudentService studentService) {
        this.markService = markService;
        this.studentService = studentService;
    }

    @GetMapping("")
    public List<Mark> getAll() {
        return markService.findAll();
    }

    @GetMapping("{id}")
    public Mark get(@PathVariable long id) {
        return markService.findById(id);
    }

    @GetMapping("/student/{id}")
    public Mark getStudentMarks(@PathVariable long id) {
        return markService.findByStudent(id);
    }

    @PostMapping("/add")
    public String add(@RequestBody Mark element, @RequestHeader HttpHeaders headers) {
        HashMap<String, String> message = new HashMap<>();
        String token = headers.get("Authorization").get(0);
        DecodedJWT jwt = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256("yTAHbkECx?3@a^G?D5p=?rGs5crM$4=t");
            JWTVerifier verifier = JWT.require(algorithm)
                    .acceptLeeway(1)
                    .build();
            verifier.verify(token);
            jwt = JWT.decode(token);
        } catch (JWTDecodeException exception) {
            message.put("message", "invalid token");
            return JSON.toJSONString(message);
        } catch (JWTVerificationException exception) {
            if (exception.getClass() == TokenExpiredException.class) {
                message.put("message", "token has been expired");
                return JSON.toJSONString(message);
            }
        }
        try {
            String email = jwt.getClaim("email").asString();
            Student student = studentService.findByEmail(email);
            long id = student.getId();
            element.setStudent(id);
            markService.insert(element);
            message.put("message", "success");
        } catch (Exception e) {
            message.put("message", "error");
            e.printStackTrace();
        }
        return JSON.toJSONString(message);
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") long id) {
        HashMap<String, String> message = new HashMap<>();
        if (markService.delete(id)) {
            message.put("message", "success");
        } else {
            message.put("message", "error");
        }
        return JSON.toJSONString(message);
    }

    @PutMapping("")
    public String update(@RequestBody Mark element, @RequestHeader HttpHeaders headers) {
        HashMap<String, String> message = new HashMap<>();
        String token = headers.get("Authorization").get(0);
        DecodedJWT jwt = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256("yTAHbkECx?3@a^G?D5p=?rGs5crM$4=t");
            JWTVerifier verifier = JWT.require(algorithm)
                    .acceptLeeway(1)
                    .build();
            verifier.verify(token);
            jwt = JWT.decode(token);
        } catch (JWTDecodeException exception) {
            message.put("message", "invalid token");
            return JSON.toJSONString(message);
        } catch (JWTVerificationException exception) {
            if (exception.getClass() == TokenExpiredException.class) {
                message.put("message", "token has been expired");
                return JSON.toJSONString(message);
            }
        }
        try {
            String email = jwt.getClaim("email").asString();
            Student student = studentService.findByEmail(email);
            long id = student.getId();
            element.setStudent(id);
            Mark mark = markService.findByStudent(id);
            if (mark == null)
                mark = markService.insert(new Mark());
            element.setId(mark.getId());
            markService.update(element);
            message.put("code", "1");
            message.put("message", "success");
        } catch (Exception e) {
            message.put("code", "2");
            message.put("message", "error");
            e.printStackTrace();
        }
        return JSON.toJSONString(message);
    }

}

