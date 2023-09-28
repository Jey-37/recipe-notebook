package com.recipes.service;

import com.recipes.model.Recipe;
import com.recipes.model.RecipeIngredient;
import com.recipes.model.dto.RecipeForm;
import com.recipes.repo.RecipeIngredientRepository;
import com.recipes.repo.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class BDService
{
    private final RecipeRepository recipeRepo;

    private final RecipeIngredientRepository recIngRepo;

    public BDService(RecipeRepository recipeRepo, RecipeIngredientRepository recIngRepo) {
        this.recipeRepo = recipeRepo;
        this.recIngRepo = recIngRepo;
    }

    public Recipe saveRecipe(RecipeForm recipeForm) {
        Recipe recipe = new Recipe();
        recipe.setName(recipeForm.getName());
        recipe.setPortionsNumber(recipeForm.getPortionsNumber());
        recipe.setTags(recipeForm.getTags());
        recipe.setStages(recipeForm.getStages());

        recipeRepo.save(recipe);

        Set<RecipeIngredient> recipeIngredients = new HashSet<>(recipeForm.getIngredients().size());
        for (var ri : recipeForm.getIngredients()) {
            recipeIngredients.add(
                    new RecipeIngredient(
                            recipe, ri.getIngredient(), ri.getMeasure(),
                            ri.getQuantity(), ri.getNote()));
        }

        recIngRepo.saveAll(recipeIngredients);

        return recipe;
    }
}
