package com.api.imageinterpretor.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;


@Entity
@ToString
@Getter
@Setter
@Table(name = "utilizatori")
public class User {

    @Id
    @Email(message = "Email should be valid")
    @Column(unique = true)
    private String email;

    @NotNull(message = "Name cannot be null")
    private String name;

    @NotNull(message = "Password cannot be null")
    private String password;

    private int enabled;

    private String activationCode;

    private int offence;

    @Column(name = "disable_date")
    private Timestamp disableDate;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Flow> flow;
}



