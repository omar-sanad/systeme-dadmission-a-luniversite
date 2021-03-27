package com.university.admission.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "specialties")
public class Specialty {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    @NotEmpty()
    private String name;
    @Column(name = "description")
    @NotEmpty()
    private String description;
    @Column(name = "training")
    private Long training;

    public Specialty() {
    }

    public Specialty(Long id, @NotEmpty() String name, @NotEmpty() String description, @NotEmpty() Long training) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.training = training;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTraining() {
        return training;
    }

    public void setTraining(Long training) {
        this.training = training;
    }
}
