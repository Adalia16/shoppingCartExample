package com.Leapcode.LeapcodeShoppingCart.models.data;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="categories")
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    public int getId() {
        return id;
    }
    public void setId(int newId) {
        this.id = newId;
    }
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String newName) {
        this.name = newName;
    }
    private String casing;
    public String getCasing() {
        return casing;
    }
    public void setCasing(String newcase) {
        this.casing = newcase;
    }
    private int sorting;
    public int getSorting() {
        return sorting;
    }

    public void setSorting(int sorting) {
        this.sorting = sorting;
    }
}
