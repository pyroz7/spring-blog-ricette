package org.springblogricette.springricetta.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


@Entity
@Table
public class Repice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    @NotEmpty
    private String title;
    @NotEmpty
    private String ingredients;
    private String photo;
    @Column
    @NotNull
    private Integer preparTime;
    @NotNull
    private Integer numberPortions;
    @NotEmpty
    @Lob
    private String text;
    @ManyToOne
    private Category category;

    public Category getCategory(){
        return category;
    }

    public void setCategory(Category category){
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle() {
        this.title= title;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients){
        this.ingredients = ingredients;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getPreparTime(){
        return preparTime;
    }

    public void setPreparTime(Integer preparTime){
        this.preparTime = preparTime;
    }

    public Integer getNumberPortions(){
        return numberPortions;
    }

    public void setNumberPortions(Integer numberPortions){
        this.numberPortions = numberPortions;
    }

    public String getText(){
        return text;
    }

    public void setText(String text){
        this.text = text;
    }
}
