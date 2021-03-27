package com.university.admission.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "administrators")
public class Administrator {

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
    @Column(name = "email", unique = true)
    @NotEmpty()
    private String email;
    @Column(name = "password")
    @NotEmpty()
    private String password;
    @Column(name = "identity_code")
    @NotEmpty()
    private String identityCode;
    @Column(name = "establishment")
    private Long establishment;

    public Administrator() {
    }

    public Administrator(Long id, @NotEmpty() String firstName, @NotEmpty() String lastName, @NotEmpty() String email, @NotEmpty() String password, @NotEmpty() String identityCode, Long establishment) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.identityCode = identityCode;
        this.establishment = establishment;
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

    public Long getEstablishment() {
        return establishment;
    }

    public void setEstablishment(Long establishment) {
        this.establishment = establishment;
    }
}
