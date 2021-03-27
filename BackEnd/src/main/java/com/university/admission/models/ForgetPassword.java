package com.university.admission.models;

public class ForgetPassword {
    String email;
    String phoneNumber;
    String choice;

    public ForgetPassword() {
    }

    public ForgetPassword(String email, String phoneNumber, String choice) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.choice = choice;
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

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }
}
