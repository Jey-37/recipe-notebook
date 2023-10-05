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

        recipe.getIngredients().forEach(
                ing -> ing.setId(new RecipeIngredient.Id(recipe.getId(), ing.getIngredient().getId())));
        recIngRepo.saveAll(recipe.getIngredients());

        return recipe;
    }

    @Transactional
    public void updateRecipe(Recipe newRecipe) {
        newRecipe.getIngredients().forEach(
                ing -> ing.setId(new RecipeIngredient.Id(newRecipe.getId(), ing.getIngredient().getId())));

        Recipe oldRecipe = recipeRepo.findById(newRecipe.getId()).orElseThrow(NoSuchElementException::new);
        oldRecipe.setName(newRecipe.getName());
        oldRecipe.setPortionsNumber(newRecipe.getPortionsNumber());
        oldRecipe.setStages(newRecipe.getStages());
        oldRecipe.setTags(newRecipe.getTags());

        recIngRepo.deleteAll(oldRecipe.getIngredients());
        recIngRepo.saveAll(newRecipe.getIngredients());
    }
}
