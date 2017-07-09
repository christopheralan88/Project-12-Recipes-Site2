package com.cj.model;

import javax.persistence.*;

@Entity
public class Instruction extends BaseEntity {
    @Column
    private Long step;
    @Column
    private String text;


    public Instruction() {
        super();
    }

    public Instruction(Long step, String text) {
        this();
        this.step = step;
        this.text = text;
    }

    public Long getStep() {
        return step;
    }

    public void setStep(Long step) {
        this.step = step;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Instruction)) return false;

        Instruction that = (Instruction) o;

        if (!step.equals(that.step)) return false;
        return text.equals(that.text);
    }

    @Override
    public int hashCode() {
        int result = step.hashCode();
        result = 31 * result + text.hashCode();
        return result;
    }
}