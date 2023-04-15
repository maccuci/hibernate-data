package com.maccuci.data.database.account;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Account")
@Getter
@Setter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name, lastName;
    private String role;
    private double salary;

    public Account(String name, String lastName, String role, double salary) {
        this.name = name;
        this.lastName = lastName;
        this.role = role;
        this.salary = salary;
    }

    public Account() {}
}
