package com.university.admission.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "establishments")
public class Establishment {
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
    @Column(name = "type")
    @NotEmpty()
    private String type;
    @Column(name = "logo")
    @NotEmpty()
    private String logo;

    public Establishment() {
    }

    public Establishment(Long id, @NotEmpty() String name, @NotEmpty() String description, @NotEmpty() String type, @NotEmpty() String logo) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.logo = logo;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
