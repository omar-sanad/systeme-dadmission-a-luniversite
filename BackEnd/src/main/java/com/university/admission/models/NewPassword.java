package com.university.admission.models;

public class NewPassword {
    private String email;
    private String newPassword;
    private String newPasswordRepeated;
    private long code;

    public NewPassword() {
    }

    public NewPassword(String email, String newPassword, String newPasswordRepeated, long code) {
        this.email = email;
        this.newPassword = newPassword;
        this.newPasswordRepeated = newPasswordRepeated;
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordRepeated() {
        return newPasswordRepeated;
    }

    public void setNewPasswordRepeated(String newPasswordRepeated) {
        this.newPasswordRepeated = newPasswordRepeated;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }
}
