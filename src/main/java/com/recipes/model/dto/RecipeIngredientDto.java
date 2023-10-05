package com.recipes.model.dto;

import com.recipes.model.Ingredient;
import com.recipes.model.Measure;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeIngredientDto
{
    private Ingredient ingredient;
    private Measure measure;
    private double quantity;
    private String note;
}
