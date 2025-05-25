package com.example.securitytest.model.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "Group_Info", schema = "Person_Info")
public class GroupInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String groupName;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "authority_id")
    private Authority authority;
}
