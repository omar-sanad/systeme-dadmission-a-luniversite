package com.university.admission.services;

import com.nexmo.client.NexmoClient;
import com.nexmo.client.sms.SmsSubmissionResponse;
import com.nexmo.client.sms.SmsSubmissionResponseMessage;
import com.nexmo.client.sms.messages.TextMessage;
import com.university.admission.models.Student;
import com.university.admission.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImplementation implements StudentService {
    @Autowired
    private StudentRepository repository;

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public List<Student> findAll() {
        return (List<Student>) repository.findAll();
    }

    @Override
    public List<Student> findAllByEstablishment(long id) {
        List<Student> all = (List<Student>) repository.findAll();
        ArrayList<Student> returned = new ArrayList<>();
        for(Student element:all){
            if(element.getEstablishment() == id)
                returned.add(element);
        }
        return returned;
    }

    @Override
    public Student findById(long id) {
        Optional<Student> result = repository.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            return null;
        }
    }

    @Override
    public Student findByEmail(String email) {
        List<Student> list = (List<Student>) repository.findAll();
        for (Student element : list) {
            if (element.getEmail().equals(email))
                return element;
        }
        return null;
    }

    @Override
    public Student insert(Student element) {
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
    public boolean update(Student element) {
        try {
            repository.save(element);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean sendMail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            emailSender.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean sendSms(String to, String from, String body) {
        try {
            NexmoClient client = new NexmoClient.Builder()
                    .apiKey("b404de2f")
                    .apiSecret("MexjmY2aHcdnwmze")
                    .build();
            TextMessage message = new TextMessage(from, "212" + to.substring(1), body);
            SmsSubmissionResponse response = client.getSmsClient().submitMessage(message);
            for (SmsSubmissionResponseMessage responseMessage : response.getMessages()) {
                System.out.println(responseMessage);
            }
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }
}
