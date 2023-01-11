package com.api.imageinterpretor.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.checkerframework.common.aliasing.qual.Unique;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
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

    @OneToMany(mappedBy = "user")
    private List<Flow> flow;
}



