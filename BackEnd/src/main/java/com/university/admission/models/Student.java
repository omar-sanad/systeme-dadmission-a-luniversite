package com.university.admission.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "first_name")
    @NotEmpty()
    private String firstName;
    @Column(name = "last_name")
    @NotEmpty()
    private String lastName;
    @Column(name = "email")
    @NotEmpty()
    private String email;
    @Column(name = "phone_number")
    @NotEmpty()
    private String phoneNumber;
    @Column(name = "second_phone_number")
    @NotEmpty()
    private String secondPhoneNumber;
    @Column(name = "password")
    @NotEmpty()
    private String password;
    @Column(name = "identity_code")
    @NotEmpty()
    private String identityCode;
    @Column(name = "national_code")
    @NotEmpty()
    private String nationalCode;
    @Column(name = "type")
    @NotEmpty()
    private String type;
    @Column(name = "gender")
    @NotEmpty()
    private String gender;
    @Column(name = "nationality")
    @NotEmpty()
    private String nationality;
    @Column(name = "birth_date")
    @NotEmpty()
    private String birthDate;
    @Column(name = "address")
    @NotEmpty()
    private String address;
    @Column(name = "city")
    @NotEmpty()
    private String city;
    @Column(name = "verified")
    private boolean verified;
    @Column(name = "documents")
    private String documents;
    @Column(name = "identity_card_image")
    private String identityCardImage;
    @Column(name = "establishment")
    private long establishment;
    @Column(name = "training")
    private long training;
    @Column(name = "lastDiplome")
    private String lastDiplome;
    @Column(name = "code")
    private long code;
    @Column(name = "reset_attempts_number")
    private int resetAttemptsNumber;
    @Column(name = "blocked_time")
    private String blockedTime;
    @Column(name = "speciality")
    private long speciality;
    public Student() {
    }

    public Student(Long id, @NotEmpty() String firstName, @NotEmpty() String lastName, @NotEmpty() String email, @NotEmpty() String phoneNumber, @NotEmpty() String secondPhoneNumber, @NotEmpty() String password, @NotEmpty() String identityCode, @NotEmpty() String nationalCode, @NotEmpty() String type, @NotEmpty() String gender, @NotEmpty() String nationality, @NotEmpty() String birthDate, @NotEmpty() String address, @NotEmpty() String city, boolean verified, String documents, String identityCardImage, long establishment, long training, String lastDiplome, long code, int resetAttemptsNumber, String blockedTime) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.secondPhoneNumber = secondPhoneNumber;
        this.password = password;
        this.identityCode = identityCode;
        this.nationalCode = nationalCode;
        this.type = type;
        this.gender = gender;
        this.nationality = nationality;
        this.birthDate = birthDate;
        this.address = address;
        this.city = city;
        this.verified = verified;
        this.documents = documents;
        this.identityCardImage = identityCardImage;
        this.establishment = establishment;
        this.training = training;
        this.lastDiplome = lastDiplome;
        this.code = code;
        this.resetAttemptsNumber = resetAttemptsNumber;
        this.blockedTime = blockedTime;
    }

    public Student(Long id, @NotEmpty() String firstName, @NotEmpty() String lastName, @NotEmpty() String email, @NotEmpty() String phoneNumber, @NotEmpty() String secondPhoneNumber, @NotEmpty() String password, @NotEmpty() String identityCode, @NotEmpty() String nationalCode, @NotEmpty() String type, @NotEmpty() String gender, @NotEmpty() String nationality, @NotEmpty() String birthDate, @NotEmpty() String address, @NotEmpty() String city, boolean verified, String documents, String identityCardImage, long establishment, long training, String lastDiplome, long code, int resetAttemptsNumber, String blockedTime, long speciality) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.secondPhoneNumber = secondPhoneNumber;
        this.password = password;
        this.identityCode = identityCode;
        this.nationalCode = nationalCode;
        this.type = type;
        this.gender = gender;
        this.nationality = nationality;
        this.birthDate = birthDate;
        this.address = address;
        this.city = city;
        this.verified = verified;
        this.documents = documents;
        this.identityCardImage = identityCardImage;
        this.establishment = establishment;
        this.training = training;
        this.lastDiplome = lastDiplome;
        this.code = code;
        this.resetAttemptsNumber = resetAttemptsNumber;
        this.blockedTime = blockedTime;
        this.speciality = speciality;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdentityCode() {
        return identityCode;
    }

    public void setIdentityCode(String identityCode) {
        this.identityCode = identityCode;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getDocuments() {
        return documents;
    }

    public void setDocuments(String documents) {
        this.documents = documents;
    }

    public long getEstablishment() {
        return establishment;
    }

    public void setEstablishment(long establishment) {
        this.establishment = establishment;
    }

    public String getSecondPhoneNumber() {
        return secondPhoneNumber;
    }

    public void setSecondPhoneNumber(String secondPhoneNumber) {
        this.secondPhoneNumber = secondPhoneNumber;
    }

    public long getTraining() {
        return training;
    }

    public void setTraining(long training) {
        this.training = training;
    }

    public String getLastDiplome() {
        return lastDiplome;
    }

    public void setLastDiplome(String lastDiplome) {
        this.lastDiplome = lastDiplome;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public int getResetAttemptsNumber() {
        return resetAttemptsNumber;
    }

    public void setResetAttemptsNumber(int resetAttemptsNumber) {
        this.resetAttemptsNumber = resetAttemptsNumber;
    }

    public String getBlockedTime() {
        return blockedTime;
    }

    public void setBlockedTime(String blockedTime) {
        this.blockedTime = blockedTime;
    }

    public String getIdentityCardImage() {
        return identityCardImage;
    }

    public void setIdentityCardImage(String identityCardImage) {
        this.identityCardImage = identityCardImage;
    }

    public long getSpeciality() {
        return speciality;
    }

    public void setSpeciality(long speciality) {
        this.speciality = speciality;
    }
}
