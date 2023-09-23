package com.recipes.model.dto;

import com.recipes.model.Ingredient;
import lombok.Data;

@Data
public class RecipeIngredientDto
{
    private Ingredient ingredient;
    private int measureId;
    private double quantity;
    private String note;

    protected RecipeIngredientDto() {}

    public RecipeIngredientDto(Ingredient ingredient, int measureId) {
        this.ingredient = ingredient;
        this.measureId = measureId;
    }
}
