package com.recipes.service;

import com.recipes.model.Recipe;
import com.recipes.model.RecipeIngredient;
import com.recipes.repo.RecipeIngredientRepository;
import com.recipes.repo.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.logging.Logger;

@Service
public class DbService
{
    private final Logger log = Logger.getLogger(DbService.class.getName());

    private final RecipeRepository recipeRepo;

    private final RecipeIngredientRepository recIngRepo;

    public DbService(RecipeRepository recipeRepo, RecipeIngredientRepository recIngRepo) {
        this.recipeRepo = recipeRepo;
        this.recIngRepo = recIngRepo;
    }

    @Transactional
    public Recipe saveRecipe(Recipe recipe) {
        recipeRepo.save(recipe);

        recipe.getRecipeIngredients().forEach(
                ri -> ri.setId(new RecipeIngredient.Id(recipe.getId(), ri.getIngredient().getId())));
        recIngRepo.saveAll(recipe.getRecipeIngredients());

        return recipe;
    }

    @Transactional
    public void updateRecipe(Recipe newRecipe) {
        newRecipe.getRecipeIngredients().forEach(
                ri -> ri.setId(new RecipeIngredient.Id(newRecipe.getId(), ri.getIngredient().getId())));

        Recipe oldRecipe = recipeRepo.findById(newRecipe.getId()).orElseThrow(NoSuchElementException::new);
        oldRecipe.setName(newRecipe.getName());
        oldRecipe.setPortionsNumber(newRecipe.getPortionsNumber());
        oldRecipe.setStages(newRecipe.getStages());
        oldRecipe.setTags(newRecipe.getTags());

        recIngRepo.deleteAll(oldRecipe.getRecipeIngredients());
        recIngRepo.saveAll(newRecipe.getRecipeIngredients());
    }
}
