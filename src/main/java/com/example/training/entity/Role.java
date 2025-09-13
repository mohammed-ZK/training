package com.example.training.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private RoleEnum name;

    public Long getId () {
        return id;
    }

    public RoleEnum getName () {
        return name;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public void setName (RoleEnum name) {
        this.name = name;
    }

    public Role (Long id , RoleEnum name) {
        this.id = id;
        this.name = name;
    }
}
