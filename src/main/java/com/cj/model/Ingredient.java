package com.cj.model;


import javax.persistence.*;

@Entity
public class Ingredient extends BaseEntity {
    @Column
    private String name;
    @Column
    private String measure;
    @Column
    private String condition;


    public Ingredient() {
        super();
    }

    public Ingredient(String name, String measure, String condition) {
        this();
        this.name = name;
        this.measure = measure;
        this.condition = condition;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ingredient)) return false;

        Ingredient that = (Ingredient) o;

        if (!name.equals(that.name)) return false;
        if (!measure.equals(that.measure)) return false;
        return condition.equals(that.condition);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + measure.hashCode();
        result = 31 * result + condition.hashCode();
        return result;
    }
}
