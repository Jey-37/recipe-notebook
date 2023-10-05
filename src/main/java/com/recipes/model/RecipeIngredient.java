package com.recipes.model;

import com.recipes.model.dto.RecipeIngredientDto;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@Entity
@Table(name = "recipes_ingredients")
public class RecipeIngredient
{
    @EmbeddedId
    private Id id;

    @ManyToOne
    @JoinColumn(
            name = "ing_id",
            insertable = false, updatable = false
    )
    private Ingredient ingredient;

    @ManyToOne
    @JoinColumn(name = "meas_id")
    private Measure measure;

    private double quantity;

    private String note;

    @Column(nullable = false)
    private int num;

    protected RecipeIngredient() {}

    public RecipeIngredient(Ingredient ingredient, Measure measure,
                            double quantity, String note) {
        this.ingredient = ingredient;
        this.measure = measure;
        this.quantity = quantity;
        this.note = note;
    }

    public RecipeIngredientDto toDto() {
        return new RecipeIngredientDto(ingredient, measure, quantity, note);
    }

    @Embeddable
    @Data
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Id implements Serializable
    {
        @Column(name = "rec_id")
        private long recId;

        @Column(name = "ing_id")
        private int ingId;
    }
}
