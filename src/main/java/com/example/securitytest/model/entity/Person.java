package com.example.securitytest.model.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;


@Getter
@Setter
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "Person", schema = "Person_Info")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer age;

    @Column
    private String username;

    @Column
    private String surname;

    @Column
    private String password;

    @Column
    private String nationalCode;

    @ManyToOne
    private GroupInfo groupInfo;

    @Column
    @Email(flags = Pattern.Flag.UNIX_LINES, message = "Email is not fucking valid!!!!")
    private String email;

    @Column
    private Boolean enabled;
}
