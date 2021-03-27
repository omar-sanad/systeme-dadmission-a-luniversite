package com.university.admission.models;

public class University {
    String name,description,logo;

    public University() {
    }

    public University(String name, String description, String logo) {
        this.name = name;
        this.description = description;
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
