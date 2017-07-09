package com.cj.model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category extends BaseEntity{
    @Column
    private String name;

    protected Category() {
        super();
    }

    public Category(String name) {
        this();
        /*if (name.equals("")) {
            this.name = "All";
        } else {
            this.name = name;
        }*/
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
