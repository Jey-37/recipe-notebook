package com.recipes.model.dto;

import com.recipes.model.Recipe;
import com.recipes.model.RecipeIngredient;
import com.recipes.model.Tag;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class RecipeForm
{
    private long id;
    private String name;
    private int portionsNumber = 1;
    private Set<Tag> tags = new HashSet<>();
    private List<String> stages = new ArrayList<>();
    private List<RecipeIngredientDto> ingredients = new ArrayList<>();

    public Recipe toRecipe() {
        Recipe recipe = new Recipe();
        recipe.setId(id);
        recipe.setName(name);
        recipe.setPortionsNumber(portionsNumber);
        recipe.setTags(tags);
        recipe.setStages(stages);

        ingredients.forEach(ing -> recipe.addRecipeIngredient(
                new RecipeIngredient(ing.getIngredient(), ing.getMeasure(), ing.getQuantity(), ing.getNote())));

        return recipe;
    }
}
