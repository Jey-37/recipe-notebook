package com.recipes.service;

import com.recipes.model.Recipe;
import com.recipes.model.RecipeIngredient;
import com.recipes.model.dto.RecipeForm;
import com.recipes.repo.RecipeIngredientRepository;
import com.recipes.repo.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

@Service
public class DbService
{
    private final Logger log = Logger.getLogger(DbService.class.getName());

    private final RecipeRepository recipeRepo;

    private final RecipeIngredientRepository recIngRepo;

    private final FileStorageService fileStorageService;

    public DbService(RecipeRepository recipeRepo, RecipeIngredientRepository recIngRepo,
                     FileStorageService fileStorageService) {
        this.recipeRepo = recipeRepo;
        this.recIngRepo = recIngRepo;
        this.fileStorageService = fileStorageService;
    }

    @Transactional
    public Recipe saveRecipe(RecipeForm recipeForm) {
        Recipe recipe = recipeForm.toRecipe();
        recipeRepo.save(recipe);

        recipe.getIngredients().forEach(
                ing -> ing.setId(new RecipeIngredient.Id(recipe.getId(), ing.getIngredient().getId())));
        recIngRepo.saveAll(recipe.getIngredients());

        if (recipeForm.getMainPhoto() != null) {
            updateRecipeMainPhoto(recipe, recipeForm.getMainPhoto());
        }

        return recipe;
    }

    @Transactional
    public void updateRecipe(RecipeForm recipeForm) {
        Recipe newRecipe = recipeForm.toRecipe();
        newRecipe.getIngredients().forEach(
                ing -> ing.setId(new RecipeIngredient.Id(newRecipe.getId(), ing.getIngredient().getId())));

        Recipe oldRecipe = recipeRepo.findById(newRecipe.getId()).orElseThrow(NoSuchElementException::new);
        oldRecipe.setName(newRecipe.getName());
        oldRecipe.setPortionsNumber(newRecipe.getPortionsNumber());
        oldRecipe.setStages(newRecipe.getStages());
        oldRecipe.setTags(newRecipe.getTags());

        recIngRepo.deleteAll(oldRecipe.getIngredients());
        recIngRepo.saveAll(newRecipe.getIngredients());

        if (recipeForm.getMainPhoto() != null && !recipeForm.getMainPhoto().isEmpty()) {
            updateRecipeMainPhoto(oldRecipe, recipeForm.getMainPhoto());
        }
    }

    private void updateRecipeMainPhoto(Recipe recipe, MultipartFile photo) {
        try {
            String mainPhotoFileName = fileStorageService.saveMainPhoto(recipe, photo);
            recipe.setMainPhotoName(mainPhotoFileName);
        } catch (IOException exception) {
            log.warning(exception.getMessage());
        }
    }
}
