package com.university.admission.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "trainings")
public class Training {
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
    @Column(name = "establishment")
    private Long establishment;
    @Column(name = "student_number_principal_list")
    private int studentNumberPrincipalList;

    public Training() {
    }

    public Training(Long id, @NotEmpty() String name, @NotEmpty() String description, Long establishment, int studentNumberPrincipalList) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.establishment = establishment;
        this.studentNumberPrincipalList = studentNumberPrincipalList;
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

    public Long getEstablishment() {
        return establishment;
    }

    public void setEstablishment(Long establishment) {
        this.establishment = establishment;
    }

    public int getStudentNumberPrincipalList() {
        return studentNumberPrincipalList;
    }

    public void setStudentNumberPrincipalList(int studentNumberPrincipalList) {
        this.studentNumberPrincipalList = studentNumberPrincipalList;
    }
}
