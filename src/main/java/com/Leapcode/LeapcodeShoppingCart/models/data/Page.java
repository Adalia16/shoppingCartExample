package com.Leapcode.LeapcodeShoppingCart.models.data;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "pages")
@Data
public class Page {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String casing;
    private String content;
    private int sorting;
    public String getCasing() {
        return this.casing;
    }
    public void setCasing(String casing1) {
        this.casing = casing1;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String tit) {
        this.title = tit;
    }
    public String getContent(){
        return this.content;
    }
    public void setContent(String cont) {
        this.content = cont;
    }
    public int getSorting() {
        return this.sorting;
    }
    public void setSorting(int a) {
        this.sorting = a;
    }
    public int getId() {
        return this.id;
    }
}
