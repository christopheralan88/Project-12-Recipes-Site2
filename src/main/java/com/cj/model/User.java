package com.cj.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


@Entity
public class User extends BaseEntity{
    //public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
    @NotNull
    @Size(min = 1)
    private String name;
    @NotNull
    @JsonIgnore
    private String[] roles;
    @NotNull
    @Size(min = 3)
    @JsonIgnore
    private String password;
    @JsonIgnore
    @OneToMany
    private List<Recipe> favorites;

    private User() {
        super();
    }

    public User(String name, String[] roles, String password) {
        this();
        this.name = name;
        this.roles = roles;
        //this.password = PASSWORD_ENCODER.encode(password);
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        //this.password = PASSWORD_ENCODER.encode(password);
        this.password = password;
    }

    public List<Recipe> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Recipe> favorites) {
        this.favorites = favorites;
    }
}