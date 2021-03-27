package com.university.admission.controllers;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.layout.font.FontProvider;
import com.university.admission.models.*;
import com.university.admission.services.EstablishmentService;
import com.university.admission.services.MarkService;
import com.university.admission.services.StudentService;
import com.university.admission.services.TrainingService;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.prefs.Preferences;

@RestController()
@RequestMapping("/api/establishments")
public class EstablishmentController {

    final EstablishmentService establishmentService;
    final StudentService studentService;
    final MarkService markService;
    final TrainingService trainingService;

    public EstablishmentController(EstablishmentService establishmentService,
                                   StudentService studentService, MarkService markService, TrainingService trainingService) {
        this.establishmentService = establishmentService;
        this.studentService = studentService;
        this.markService = markService;
        this.trainingService = trainingService;
    }

    @GetMapping("")
    public List<Establishment> getAll() {
        return establishmentService.findAll();
    }

    @GetMapping("{id}")
    public Establishment get(@PathVariable long id) {
        return establishmentService.findById(id);
    }

    @PostMapping("")
    public String add(@RequestBody Establishment element, @RequestHeader HttpHeaders headers) {
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
            message.put("code", "3");
            return JSON.toJSONString(message);
        } catch (JWTVerificationException exception) {
            if (exception.getClass() == TokenExpiredException.class) {
                message.put("message", "token has been expired");
                message.put("code", "4");
                return JSON.toJSONString(message);
            }
        }
        String type = jwt.getClaim("type").asString();
        if (type.equals("administrator")) {
            String name = element.getName() + ".png";
            byte[] bytes = Base64.getDecoder().decode(element.getLogo().split("base64,")[1]);
            File file = new File(new File("").getAbsolutePath() + "/files/logos/" + name);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(bytes);
                fileOutputStream.flush();
                fileOutputStream.close();
                element.setLogo(name);
            } catch (Exception e) {
                e.printStackTrace();
            }
            establishmentService.insert(element);
            message.put("message", "success");
            message.put("code", "1");
        } else {
            message.put("message", "unauthorized access");
            message.put("code", "2");
        }
        return JSON.toJSONString(message);
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") long id) {
        HashMap<String, String> message = new HashMap<>();
        if (establishmentService.delete(id)) {
            message.put("code", "1");
            message.put("message", "success");
        } else {
            message.put("code", "2");
            message.put("message", "error");
        }
        return JSON.toJSONString(message);
    }

    @PutMapping("")
    public String update(@RequestBody Establishment element) {
        HashMap<String, String> message = new HashMap<>();
        String name = element.getName() + ".png";
        byte[] bytes = Base64.getDecoder().decode(element.getLogo().split("base64,")[1]);
        File file = new File(new File("").getAbsolutePath() + "/files/logos/" + name);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bytes);
            fileOutputStream.flush();
            fileOutputStream.close();
            element.setLogo(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (establishmentService.update(element)) {
            message.put("code", "1");
            message.put("message", "success");
        } else {
            message.put("code", "2");
            message.put("message", "error");
        }
        return JSON.toJSONString(message);
    }

    @PostMapping("university")
    public String setUniversityInformation(@RequestBody University element) {
        HashMap<String, String> message = new HashMap<>();
        Preferences preferences = Preferences.userNodeForPackage(getClass());
        preferences.put("name", element.getName());
        preferences.put("description", element.getDescription());
        String name = element.getName() + ".png";
        byte[] bytes = Base64.getDecoder().decode(element.getLogo().split("base64,")[1]);
        File file = new File(new File("").getAbsolutePath() + "/files/logos/" + name);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bytes);
            fileOutputStream.flush();
            fileOutputStream.close();
            element.setLogo(name);
            preferences.put("logo", element.getLogo());
            message.put("message", "success");
            message.put("code", "1");
            return JSON.toJSONString(message);
        } catch (Exception e) {
            e.printStackTrace();
            message.put("code", "2");
            message.put("message", "please try again later");
            return JSON.toJSONString(message);
        }
    }

    @GetMapping("university")
    public String getUniversityInformation() {
        HashMap<String, String> message = new HashMap<>();
        Preferences preferences = Preferences.userNodeForPackage(getClass());
        String name = preferences.get("name", "undefined");
        String description = preferences.get("description", "undefined");
        String logo = preferences.get("logo", "undefined");
        message.put("name", name);
        message.put("code", "1");
        message.put("description", description);
        message.put("logo", logo);
        return JSON.toJSONString(message);
    }


    @PostMapping("/principalList")
    public String generatePrincipalList(@RequestBody Training element, @RequestHeader HttpHeaders headers) {
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
        String email = jwt.getClaim("email").asString();
        element = trainingService.findById(element.getId());
        Establishment establishment = establishmentService.findById(element.getEstablishment());
        List<Student> students = studentService.findAllByEstablishment(element.getEstablishment());
        System.out.println(students.size());
        students.sort(comparator);
        ArrayList<Student> principalList = new ArrayList<>();
        int n = element.getStudentNumberPrincipalList();
        for (int i = 0; i < n; i++) {
            principalList.add(students.get(i));
        }

        try {
            Preferences preferences = Preferences.userNodeForPackage(getClass());
            String universityName = preferences.get("description", "undefined");
            String universityLogo = preferences.get("logo", "undefined");
            String establishmentLogo = establishment.getLogo();
            String establishmentName = establishment.getName();
            String trainingName = element.getName();
            StringWriter writer = new StringWriter();
            VelocityContext velocityContext = new VelocityContext();
            velocityContext.put("universityLogo", universityLogo);
            velocityContext.put("universityName", universityName);
            velocityContext.put("establishmentLogo", establishmentLogo);
            velocityContext.put("establishmentName", establishmentName);
            velocityContext.put("trainingName", trainingName);
            velocityContext.put("students", principalList);


            VelocityEngine velocityEngine = new VelocityEngine();
            velocityEngine.setProperty("resource.loader", "class");
            velocityEngine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
            velocityEngine.init();
            Template template = velocityEngine.getTemplate("templates/principalList.vm");
            StringWriter stringWriter = new StringWriter();
            template.merge(velocityContext, stringWriter);
            String html = stringWriter.toString();

            File htmlFile = new File(new File("").getAbsolutePath() + "/lists/" + "temporary" + ".html");
            PrintWriter printWriter = new PrintWriter(htmlFile.getAbsolutePath(), "UTF-8");
            printWriter.println(html);
            printWriter.close();


            ClassLoader classLoader = getClass().getClassLoader();
            URL resource = classLoader.getResource("templates/principalList.vm");
            File htmlSource = new File(resource.toURI());
            File pdfDest = new File(new File("").getAbsolutePath() + "/lists/" + establishment.getName() + "-" + element.getName() + "-PRINCIPAL" + ".pdf");

            ConverterProperties properties = new ConverterProperties();
            FontProvider fontProvider = new DefaultFontProvider(false, false, false);
            fontProvider.addDirectory(new File("").getAbsolutePath() + "/fonts");
            properties.setFontProvider(fontProvider);
            HtmlConverter.convertToPdf(new FileInputStream(htmlFile), new FileOutputStream(pdfDest), properties);
            if (pdfDest.exists()) {
                htmlFile.delete();
            }
            message.put("code", "1");
            message.put("message", establishment.getName() + "-" + element.getName() + "-PRINCIPAL" + ".pdf");
        } catch (Exception e) {
            e.printStackTrace();
            message.put("code", "2");
            message.put("message", "error");
        }

        return JSON.toJSONString(message);
    }


    @PostMapping("/waitingList")
    public String generateWaitingList(@RequestBody Training element, @RequestHeader HttpHeaders headers) {
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
        String email = jwt.getClaim("email").asString();
        element = trainingService.findById(element.getId());
        Establishment establishment = establishmentService.findById(element.getEstablishment());
        List<Student> students = studentService.findAllByEstablishment(element.getEstablishment());
        System.out.println(students.size());
        students.sort(comparator);
        ArrayList<Student> principalList = new ArrayList<>();
        int n = element.getStudentNumberPrincipalList();
        for (int i = n; i < students.size(); i++) {
            principalList.add(students.get(i));
        }

        try {
            Preferences preferences = Preferences.userNodeForPackage(getClass());
            String universityName = preferences.get("description", "undefined");
            String universityLogo = preferences.get("logo", "undefined");
            String establishmentLogo = establishment.getLogo();
            String establishmentName = establishment.getName();
            String trainingName = element.getName();
            StringWriter writer = new StringWriter();
            VelocityContext velocityContext = new VelocityContext();
            velocityContext.put("universityLogo", universityLogo);
            velocityContext.put("universityName", universityName);
            velocityContext.put("establishmentLogo", establishmentLogo);
            velocityContext.put("establishmentName", establishmentName);
            velocityContext.put("trainingName", trainingName);
            velocityContext.put("students", principalList);


            VelocityEngine velocityEngine = new VelocityEngine();
            velocityEngine.setProperty("resource.loader", "class");
            velocityEngine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
            velocityEngine.init();
            Template template = velocityEngine.getTemplate("templates/principalList.vm");
            StringWriter stringWriter = new StringWriter();
            template.merge(velocityContext, stringWriter);
            String html = stringWriter.toString();

            File htmlFile = new File(new File("").getAbsolutePath() + "/lists/" + "temporary" + ".html");
            PrintWriter printWriter = new PrintWriter(htmlFile.getAbsolutePath(), "UTF-8");
            printWriter.println(html);
            printWriter.close();


            ClassLoader classLoader = getClass().getClassLoader();
            URL resource = classLoader.getResource("templates/principalList.vm");
            File htmlSource = new File(resource.toURI());
            File pdfDest = new File(new File("").getAbsolutePath() + "/lists/" + establishment.getName() + "-" + element.getName() + "-ATTENTE" + ".pdf");

            ConverterProperties properties = new ConverterProperties();
            FontProvider fontProvider = new DefaultFontProvider(false, false, false);
            fontProvider.addDirectory(new File("").getAbsolutePath() + "/fonts");
            properties.setFontProvider(fontProvider);
            HtmlConverter.convertToPdf(new FileInputStream(htmlFile), new FileOutputStream(pdfDest), properties);
            if (pdfDest.exists()) {
                htmlFile.delete();
            }
            message.put("code", "1");
            message.put("message", establishment.getName() + "-" + element.getName() + "-ATTENTE" + ".pdf");
        } catch (Exception e) {
            e.printStackTrace();
            message.put("code", "2");
            message.put("message", "error");
        }

        return JSON.toJSONString(message);
    }


    Comparator<Student> comparator = new Comparator<Student>() {
        @Override
        public int compare(Student o1, Student o2) {
            double average1, average2;
            Mark mark1 = markService.findByStudent(o1.getId());
            Mark mark2 = markService.findByStudent(o2.getId());
            average1 = (mark1.getSemester1ExamMark() + mark1.getSemester2ExamMark() + mark1.getSemester3ExamMark() + mark1.getSemester4ExamMark()) / 4;
            average2 = (mark2.getSemester1ExamMark() + mark2.getSemester2ExamMark() + mark2.getSemester3ExamMark() + mark2.getSemester4ExamMark()) / 4;
            return -1 * Double.compare(average1, average2);
        }
    };


    @GetMapping("/clearPrefs")
    private String clearPrefs(){
        Preferences preferences = Preferences.userNodeForPackage(getClass());
        preferences.put("name", "");
        preferences.put("description", "");
        preferences.put("logo", "");
        return "cleared";
    }
}

