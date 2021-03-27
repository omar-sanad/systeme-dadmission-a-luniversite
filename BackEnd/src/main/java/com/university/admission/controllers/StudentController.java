package com.university.admission.controllers;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.prefs.Preferences;

@RestController()
@RequestMapping("/api/students")
public class StudentController {

    final StudentService studentService;
    final MarkService markService;
    final TrainingService trainingService;
    final EstablishmentService establishmentService;

    public StudentController(StudentService studentService, MarkService markService, EstablishmentService establishmentService, TrainingService trainingService) {
        this.studentService = studentService;
        this.markService = markService;
        this.establishmentService = establishmentService;
        this.trainingService = trainingService;
    }

    @GetMapping("")
    public List<Student> getAll() {
        return studentService.findAll();
    }

    @GetMapping("{id}")
    public Student get(@PathVariable long id) {
        Student student = studentService.findById(id);
        student.setPassword("");
        return student;
    }

    @GetMapping("/verify/{token}")
    public String verify(@PathVariable String token) {
        DecodedJWT jwt = null;
        HashMap<String, String> message = new HashMap<>();
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
        Student student = studentService.findByEmail(email);
        if (student.isVerified()) {
            message.put("message", "your email is already verified !");
            return JSON.toJSONString(message);
        } else {
            student.setVerified(true);
            studentService.update(student);
            message.put("message", "your email has been verified successfully");
            return JSON.toJSONString(message);
        }
    }


    @PostMapping("/generatePdf")
    public String generatePdfList(@RequestBody Student element, @RequestHeader HttpHeaders headers) {
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
        List<Student> students = studentService.findAll();
        String[] establishments = new String[students.size()];
        int index = 0;
        for (Student student : students) {
            Establishment establishment = establishmentService.findById(student.getEstablishment());
            String name = "";
            if (establishment != null && establishment.getName()!=null)
                name = establishment.getName();
            establishments[index] = name;
            index += 1;
        }
        try {
            VelocityContext velocityContext = new VelocityContext();
            velocityContext.put("students", students);
            velocityContext.put("establishments", establishments);

            VelocityEngine velocityEngine = new VelocityEngine();
            velocityEngine.setProperty("resource.loader", "class");
            velocityEngine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
            velocityEngine.init();
            Template template = velocityEngine.getTemplate("templates/StudentsList.vm");
            StringWriter stringWriter = new StringWriter();
            template.merge(velocityContext, stringWriter);
            String html = stringWriter.toString();

            File htmlFile = new File(new File("").getAbsolutePath() + "/files/pdf/" + "temporary" + ".html");
            PrintWriter printWriter = new PrintWriter(htmlFile.getAbsolutePath(), "UTF-8");
            printWriter.println(html);
            printWriter.close();
            String year = new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime());
            File pdfDest = new File(new File("").getAbsolutePath() + "/files/pdf/" + "Students-List-" + year + ".pdf");

            ConverterProperties properties = new ConverterProperties();
            FontProvider fontProvider = new DefaultFontProvider(false, false, false);
            fontProvider.addDirectory(new File("").getAbsolutePath() + "/fonts");
            properties.setFontProvider(fontProvider);
            PdfDocument pdfDocument = new PdfDocument(new PdfWriter(new File(pdfDest.getAbsolutePath())));
            pdfDocument.setDefaultPageSize(PageSize.A4.rotate());
            HtmlConverter.convertToPdf(new FileInputStream(htmlFile), pdfDocument, properties);
            if (pdfDest.exists()) {
                htmlFile.delete();
            }
            message.put("code", "1");
            message.put("message", "Students-List-" + year + ".pdf");
        } catch (Exception e) {
            e.printStackTrace();
            message.put("message", "error");
        }

        return JSON.toJSONString(message);
    }


    @PostMapping("/generateExcel")
    public String generateExcelList(@RequestBody Student element, @RequestHeader HttpHeaders headers) {
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

        String year = new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime());
        Path original = Paths.get(new File("").getAbsolutePath() + "/src/main/resources/templates/ExcelTemplate.xlsx");
        Path copy = Paths.get(new File("").getAbsolutePath() + "/files/excel/Students-List-" + year + ".xlsx");
        try {
            Files.copy(original, copy, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Student> students = studentService.findAll();

        try {
            System.setProperty("ROW", "5");
            System.setProperty("COLUMN", "1");
            Fillo fillo = new Fillo();
            Connection connection = fillo.getConnection(new File("").getAbsolutePath() + "/files/excel/Students-List-" + year + ".xlsx");
            int counter = 1;
            for (Student student : students) {
                Establishment establishment = establishmentService.findById(student.getEstablishment());
                String name = "";
                if (establishment != null && establishment.getName()!=null)
                    name = establishment.getName();
                String strQuery = "INSERT INTO Sheet1(N,Nom,Prenom,Naissance,CNE,CIN,Email,GSM,Etablissement) VALUES(" + counter + ",'" + student.getLastName() + "','" + student.getFirstName() + "','" + student.getBirthDate() + "','" + student.getNationalCode() + "','" + student.getIdentityCode() + "','" + student.getEmail() + "','" + student.getPhoneNumber() + "','" + name + "')";
                connection.executeUpdate(strQuery);
                counter++;
            }
            connection.close();
        } catch (FilloException e) {
            e.printStackTrace();
        }

        message.put("code", "1");
        message.put("message", "Students-List-" + year + ".xlsx");
        return JSON.toJSONString(message);
    }


    @PostMapping("/generateCsv")
    public String generateCsvList(@RequestBody Student element, @RequestHeader HttpHeaders headers) {
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
        //String email = jwt.getClaim("email").asString();

        String year = new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime());


        List<Student> students = studentService.findAll();


        File htmlFile = new File(new File("").getAbsolutePath() + "/files/csv/" + "Students-List-" + year + ".csv");
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(htmlFile.getAbsolutePath(), "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        printWriter.println("N,Nom,Prenom,Naissance,CNE,CIN,Email,GSM,Etablissement");
        int counter = 1;
        for (Student student : students) {
            Establishment establishment = establishmentService.findById(student.getEstablishment());
            String name = "";
            if (establishment != null && establishment.getName()!=null)
                name = establishment.getName();
            printWriter.println(counter + "," + student.getLastName() + "," + student.getFirstName() + "," + student.getBirthDate() + "," + student.getNationalCode() + "," + student.getIdentityCode() + "," + student.getEmail() + "," + student.getPhoneNumber() + "," + name);
            counter++;
        }
        printWriter.close();
        message.put("code", "1");
        message.put("message", "Students-List-" + year + ".csv");
        return JSON.toJSONString(message);
    }


    @PostMapping("/signup")
    public String add(@RequestBody Student element) {
        element.setPassword(BCrypt.withDefaults().hashToString(12, element.getPassword().toCharArray()));
        element.setGender("null");
        element.setType("null");
        element.setAddress("null");
        element.setSecondPhoneNumber("null");
        element.setCity("null");
        element.setBirthDate("null");
        element.setIdentityCode("null");
        element.setNationalCode("null");
        element.setNationality("null");
        HashMap<String, String> message = new HashMap<>();
        try {
            element.setVerified(false);
            studentService.insert(element);
            String token = null;
            try {
                Algorithm algorithm = Algorithm.HMAC256("yTAHbkECx?3@a^G?D5p=?rGs5crM$4=t");
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.MINUTE, 10);
                Date date = calendar.getTime();
                token = JWT.create()
                        .withClaim("email", element.getEmail())
                        .withExpiresAt(date)
                        .sign(algorithm);
            } catch (JWTCreationException exception) {
                exception.printStackTrace();
            }
            String body = "Your account has been created successfully," +
                    "\n Please click here to activate your account: http://localhost:8080/api/students/verify/"
                    + token + "\nYour link is valid for 10 minutes,\nSincerely !";
            studentService.sendMail(element.getEmail(), "University Admission: Email Verification", body);
            message.put("message", "success");
            message.put("code", "1");
            return JSON.toJSONString(message);
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage().contains("already exists")) {
                message.put("message", "email already exists");
                return JSON.toJSONString(message);
            }
        }
        return JSON.toJSONString(message);
    }

    @PostMapping("/upload")
    public String upload(@RequestBody Student element, @RequestHeader HttpHeaders headers) {
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
        Student student = studentService.findByEmail(email);
        String name = student.getNationalCode() + "-" + student.getIdentityCode();
        byte[] bytes = Base64.getDecoder().decode(element.getDocuments().split("base64,")[1]);
        File file = new File(new File("").getAbsolutePath() + "/files/uploads/" + name + ".zip");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bytes);
            fileOutputStream.flush();
            fileOutputStream.close();
            student.setDocuments(name + ".zip");
            studentService.update(student);
            message.put("code", "1");
            message.put("message", "your documents are been uploaded successfully");
            return JSON.toJSONString(message);
        } catch (Exception e) {
            e.printStackTrace();
            message.put("code", "2");
            message.put("message", "please try again later");
            return JSON.toJSONString(message);
        }
    }

    @PostMapping("/uploadIdentityCard")
    public String uploadIdentityCard(@RequestBody Student element, @RequestHeader HttpHeaders headers) {
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
        Student student = studentService.findByEmail(email);
        String name = student.getIdentityCode();
        byte[] bytes = Base64.getDecoder().decode(element.getIdentityCardImage().split("base64,")[1]);
        File file = new File(new File("").getAbsolutePath() + "/files/identityCards/" + name + ".jpg");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bytes);
            fileOutputStream.flush();
            fileOutputStream.close();
            student.setIdentityCardImage(name + ".jpg");
            studentService.update(student);
            message.put("code", "1");
            message.put("message", "your documents are been uploaded successfully");
            return JSON.toJSONString(message);
        } catch (Exception e) {
            e.printStackTrace();
            message.put("code", "2");
            message.put("message", "please try again later");
            return JSON.toJSONString(message);
        }
    }

    @PostMapping("/receipt")
    public String generateReceipt(@RequestBody Student element, @RequestHeader HttpHeaders headers) {
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
        Student student = studentService.findByEmail(email);
        Mark mark = markService.findByStudent(student.getId());
        String schoolName = establishmentService.findById(student.getEstablishment()).getName();
        ArrayList<SemesterMark> semesterMarks = new ArrayList<>();
        String diplome = trainingService.findById(student.getTraining()).getName();
        semesterMarks.add(new SemesterMark("Semestre 1", (float) mark.getSemester1ExamMark(), mark.getSemester1Year(), diplome));
        semesterMarks.add(new SemesterMark("Semestre 2", (float) mark.getSemester2ExamMark(), mark.getSemester2Year(), diplome));
        semesterMarks.add(new SemesterMark("Semestre 3", (float) mark.getSemester3ExamMark(), mark.getSemester3Year(), diplome));
        semesterMarks.add(new SemesterMark("Semestre 4", (float) mark.getSemester4ExamMark(), mark.getSemester4Year(), diplome));
        String date = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
        String time = new SimpleDateFormat("hh:mm:ss").format(Calendar.getInstance().getTime());
        String universityYear = "";
        int month = Integer.parseInt(new SimpleDateFormat("MM").format(Calendar.getInstance().getTime()));
        if (month >= 9 && month <= 12) {
            int currentYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime()));
            universityYear = currentYear + "/" + currentYear + 1;
        }
        if (month >= 1 && month <= 8) {
            int currentYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime()));
            universityYear = (currentYear - 1) + "/" + currentYear;
        }
        try {
            Preferences preferences = Preferences.userNodeForPackage(getClass());
            String universityName = preferences.get("description", "undefined");
            String universityLogo = preferences.get("logo", "undefined");
            StringWriter writer = new StringWriter();
            VelocityContext velocityContext = new VelocityContext();
            velocityContext.put("universityLogo", universityLogo);
            velocityContext.put("universityName", universityName);
            velocityContext.put("universityYear", universityYear);
            velocityContext.put("date", date);
            velocityContext.put("time", time);
            velocityContext.put("school", schoolName);
            velocityContext.put("university", universityName);
            velocityContext.put("semesterMarks", semesterMarks);
            velocityContext.put("establishmentLogo", establishmentService.findById(student.getEstablishment()).getLogo());
            velocityContext.put("student", student);
            //velocityContext.put("marks", marks);


            VelocityEngine velocityEngine = new VelocityEngine();
            velocityEngine.setProperty("resource.loader", "class");
            velocityEngine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
            velocityEngine.init();
            Template template = velocityEngine.getTemplate("templates/template1.vm");
            StringWriter stringWriter = new StringWriter();
            template.merge(velocityContext, stringWriter);
            String html = stringWriter.toString();

            File htmlFile = new File(new File("").getAbsolutePath() + "/receipts/" + "temporary" + ".html");
            PrintWriter printWriter = new PrintWriter(htmlFile.getAbsolutePath(), "UTF-8");
            printWriter.println(html);
            printWriter.close();


            ClassLoader classLoader = getClass().getClassLoader();
            URL resource = classLoader.getResource("templates/template1.vm");
            File htmlSource = new File(resource.toURI());
            File pdfDest = new File(new File("").getAbsolutePath() + "/receipts/" + student.getNationalCode() + ".pdf");


            ConverterProperties properties = new ConverterProperties();
            FontProvider fontProvider = new DefaultFontProvider(false, false, false);
            fontProvider.addDirectory(new File("").getAbsolutePath() + "/fonts");
            properties.setFontProvider(fontProvider);
            HtmlConverter.convertToPdf(new FileInputStream(htmlFile), new FileOutputStream(pdfDest), properties);
            if (pdfDest.exists()) {
                htmlFile.delete();
            }
            message.put("message", student.getNationalCode() + ".pdf");
        } catch (Exception e) {
            e.printStackTrace();
            message.put("message", "error");
        }

        return JSON.toJSONString(message);
    }

    @PostMapping("/login")
    public String login(@RequestBody Student element) {
        HashMap<String, String> message = new HashMap<>();
        Student student = studentService.findByEmail(element.getEmail());
        if (student != null) {
            BCrypt.Result result = BCrypt.verifyer().verify(element.getPassword().toCharArray(), student.getPassword());
            if (result.verified) {
                if (student.isVerified()) {
                    String token = null;
                    try {
                        Algorithm algorithm = Algorithm.HMAC256("yTAHbkECx?3@a^G?D5p=?rGs5crM$4=t");
                        Calendar calendar = Calendar.getInstance();
                        calendar.add(Calendar.MONTH, 1);
                        Date date = calendar.getTime();
                        token = JWT.create()
                                .withClaim("type", "student")
                                .withClaim("email", student.getEmail())
                                .withExpiresAt(date)
                                .sign(algorithm);
                    } catch (JWTCreationException exception) {
                        exception.printStackTrace();
                    }
                    message.put("token", token);
                    message.put("id", String.valueOf(student.getId()));
                    message.put("message", "success");
                    message.put("code", "1");
                } else {
                    message.put("message", "you have to verify your email before login");
                    message.put("code", "2");
                }
            } else {
                message.put("message", "login failed");
                message.put("code", "3");
            }
        } else {
            message.put("message", "no student with that email address");
            message.put("code", "4");
        }
        return JSON.toJSONString(message);
    }

    @PostMapping("/forgetPassword")
    public String forgetPassword(@RequestBody ForgetPassword element) {
        HashMap<String, String> message = new HashMap<>();
        Student student = studentService.findByEmail(element.getEmail());
        if (student != null) {
            if (student.getPhoneNumber().equals(element.getPhoneNumber())) {
                Random random = new Random();
                long code = random.nextInt(99999999 + 1 - 11111111) + 11111111;
                student.setCode(code);
                studentService.update(student);
                if (element.getChoice().equals("email")) {
                    studentService.sendMail(student.getEmail(), "University Admission: Reset Password", "Hi,\nPlease use that code: " + code + " to reset your password,\nSincerely !");
                    message.put("message", "a code has been sent to your email");
                    message.put("code", "1");
                } else if (element.getChoice().equals("sms")) {
                    Preferences preferences = Preferences.userNodeForPackage(getClass());
                    String university = preferences.get("name", "undefined");
                    studentService.sendSms(student.getPhoneNumber(), university, "Hi,\nPlease use that code: " + code + " to reset your password,\nSincerely !");
                    message.put("message", "a code has been sent to your phone number");
                    message.put("code", "1");
                } else {
                    message.put("message", "please choose sms or email");
                    message.put("code", "2");
                }
            } else {
                message.put("message", "the given phone number did not match");
                message.put("code", "3");
            }
        } else {
            message.put("message", "no student with that email address");
            message.put("code", "4");
        }
        return JSON.toJSONString(message);
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestBody NewPassword element) {
        HashMap<String, String> message = new HashMap<>();
        Student student = studentService.findByEmail(element.getEmail());
        if (student != null) {
            if (student.getCode() == element.getCode()) {
                if (element.getNewPassword().equals(element.getNewPasswordRepeated())) {
                    student.setPassword(BCrypt.withDefaults().hashToString(12, element.getNewPassword().toCharArray()));
                    studentService.update(student);
                    message.put("message", "the password has been updated successfully !");
                    message.put("code", "1");
                } else {
                    message.put("message", "passwords did not match");
                    message.put("code", "2");
                }
            } else if (student.getBlockedTime() != null) {
                Date current = null, blocked = null;
                DateFormat format = new SimpleDateFormat("dd-MM-yyyy-hh:mm:ss", Locale.ENGLISH);
                try {
                    blocked = format.parse(student.getBlockedTime());
                    String date = new SimpleDateFormat("dd-MM-yyyy-hh:mm:ss").format(Calendar.getInstance().getTime());
                    current = format.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long diffInMillies = Math.abs(current.getTime() - blocked.getTime());
                long delay = TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                System.out.println(delay);
                if (delay < 3) {
                    message.put("message", "please try again later");
                    message.put("code", "2");
                } else {
                    student.setResetAttemptsNumber(1);
                    studentService.update(student);
                    if (student.getResetAttemptsNumber() < 4) {
                        message.put("message", "the given code is wrong");
                        message.put("code", "2");
                    } else {
                        String date = new SimpleDateFormat("dd-MM-yyyy-hh:mm:ss").format(Calendar.getInstance().getTime());
                        student.setBlockedTime(date);
                        studentService.update(student);
                        message.put("message", "please try again later");
                        message.put("code", "2");
                    }
                }
            } else {
                student.setResetAttemptsNumber(student.getResetAttemptsNumber() + 1);
                studentService.update(student);
                if (student.getResetAttemptsNumber() < 4) {
                    message.put("message", "the given code is wrong");
                    message.put("code", "2");
                } else {
                    String date = new SimpleDateFormat("dd-MM-yyyy-hh:mm:ss").format(Calendar.getInstance().getTime());
                    student.setBlockedTime(date);
                    studentService.update(student);
                    message.put("message", "please try again later within wait 3 hours");
                    message.put("code", "2");
                }
            }
        } else {
            message.put("message", "no student with that email address");
            message.put("code", "2");
        }
        return JSON.toJSONString(message);
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") long id) {
        HashMap<String, String> message = new HashMap<>();
        if (studentService.delete(id)) {
            message.put("message", "success");
            message.put("code", "1");
        } else {
            message.put("message", "error");
            message.put("code", "2");
        }
        return JSON.toJSONString(message);
    }

    @PutMapping("")
    public String update(@RequestBody Student element) {
        HashMap<String, String> message = new HashMap<>();
        if (element != null) {
            Student student = studentService.findById(element.getId());
            element.setPassword(student.getPassword());
            element.setBlockedTime(student.getBlockedTime());
            element.setCode(student.getCode());
            element.setDocuments(student.getDocuments());
            element.setEstablishment(student.getEstablishment());
            element.setResetAttemptsNumber(student.getResetAttemptsNumber());
            element.setTraining(student.getTraining());
            element.setVerified(student.isVerified());
            boolean updated = studentService.update(element);
            if (updated) {
                message.put("message", "success");
                message.put("code", "1");
            } else {
                message.put("message", "error");
                message.put("code", "2");
            }
        }
        return JSON.toJSONString(message);
    }

    @PutMapping("/speciality")
    public String updateSpeciality(@RequestBody Student element) {
        HashMap<String, String> message = new HashMap<>();
        if (element != null) {
            Student student = studentService.findById(element.getId());
            student.setSpeciality(element.getSpeciality());
            student.setEstablishment(element.getEstablishment());
            boolean updated = studentService.update(student);
            if (updated) {
                message.put("message", "success");
                message.put("code", "1");
            } else {
                message.put("message", "error");
                message.put("code", "2");
            }
        }
        return JSON.toJSONString(message);
    }
}

