package com.recipes.model.dto;

import com.recipes.model.Recipe;
import com.recipes.model.RecipeIngredient;
import com.recipes.model.Tag;
import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
@ToString(exclude = "mainPhoto")
public class RecipeForm
{
    private long id;
    private String name;
    private int portionsNumber = 1;
    private String description;
    private MultipartFile mainPhoto;
    private Set<Tag> tags = new HashSet<>();
    private List<String> stages = new ArrayList<>();
    private List<RecipeIngredient> ingredients = new ArrayList<>();

    public Recipe toRecipe() {
        Recipe recipe = new Recipe();
        recipe.setId(id);
        recipe.setName(name);
        recipe.setPortionsNumber(portionsNumber);
        recipe.setDescription(description);
        recipe.setTags(tags);
        recipe.setStages(stages);
        ingredients.forEach(recipe::addIngredient);

        return recipe;
    }
}
