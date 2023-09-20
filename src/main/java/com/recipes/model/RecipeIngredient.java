package com.recipes.model;

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

    protected RecipeIngredient() {}

    public RecipeIngredient(Recipe recipe, Ingredient ingredient,
                            Measure measure) {
        if (recipe.getId() == 0) {
            throw new IllegalStateException("Recipe is transient: " + recipe);
        }
        this.id = new Id(recipe.getId(), ingredient.getId());
        this.ingredient = ingredient;
        this.measure = measure;

        recipe.addRecipeIngredient(this);
    }

    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @EqualsAndHashCode
    public static class Id implements Serializable
    {
        @Column(name = "rec_id")
        private long recId;

        @Column(name = "ing_id")
        private int ingId;
    }
}
