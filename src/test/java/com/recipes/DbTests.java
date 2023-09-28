package com.recipes;

import com.recipes.model.*;
import com.recipes.model.dto.RecipeForm;
import com.recipes.model.dto.RecipeIngredientDto;
import com.recipes.repo.IngredientRepository;
import com.recipes.repo.RecipeIngredientRepository;
import com.recipes.repo.RecipeRepository;
import com.recipes.repo.TagRepository;
import com.recipes.service.BDService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
public class DbTests
{
    @Autowired
    private RecipeRepository recipeRepo;

    @Autowired
    private IngredientRepository ingredientRepo;

    @Autowired
    private TagRepository tagRepo;

    @Autowired
    private RecipeIngredientRepository recIngRepo;

    @Autowired
    private BDService bdService;

    @Test
    public void recipeSave() {
        Recipe recipe = new Recipe();
        recipe.setName("Test Recipe");
        recipe.setPortionsNumber(9);

        List<Tag> tags = new ArrayList<>((Collection<Tag>) tagRepo.findAll());
        Collections.shuffle(tags);
        tags.subList(0, 3).forEach(recipe::addTag);

        for (int i = 0; i < 3; i++) {
            recipe.addCookingStage("Stage "+i);
        }

        recipeRepo.save(recipe);

        Assertions.assertNotEquals(recipe.getId(), 0L);
        Assertions.assertNotNull(recipe.getCreateDate());

        Set<RecipeIngredient> recIngreds = new HashSet<>();
        List<Ingredient> ingreds = new ArrayList<>((Collection<Ingredient>) ingredientRepo.findAll());
        Collections.shuffle(ingreds);
        int ingredsNum = new Random().nextInt(3, 8);
        ingreds.subList(0, ingredsNum).forEach(ing ->
                recIngreds.add(
                        new RecipeIngredient(
                                recipe, ing, ing.getType().getMeasures().iterator().next(), 1, null)));

        recIngRepo.saveAll(recIngreds);

        Assertions.assertEquals(recipe.getRecipeIngredients().size(), ingredsNum);

        Recipe savedRecipe = recipeRepo.findById(recipe.getId()).get();
        Assertions.assertEquals(savedRecipe.getRecipeIngredients().size(), ingredsNum);
    }

    @Test
    public void recipeFormSaving() {
        RecipeForm recipeForm = new RecipeForm();
        recipeForm.setName("Test Recipe");
        recipeForm.setPortionsNumber(3);

        List<Tag> tags = new ArrayList<>((Collection<Tag>) tagRepo.findAll());
        recipeForm.setTags(new HashSet<>(tags.subList(0, 3)));

        List<String> stages = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            stages.add("Stage "+i);
        }
        recipeForm.setStages(stages);

        List<RecipeIngredientDto> recIngreds = new ArrayList<>();
        List<Ingredient> ingreds = new ArrayList<>((Collection<Ingredient>) ingredientRepo.findAll());
        Collections.shuffle(ingreds);
        int ingredsNum = new Random().nextInt(3, 8);
        ingreds.subList(0, ingredsNum).forEach(ing -> {
            RecipeIngredientDto ridto = new RecipeIngredientDto();
            ridto.setIngredient(ing);
            ridto.setMeasure(ing.getType().getMeasures().iterator().next());
            ridto.setQuantity(1.0);
            recIngreds.add(ridto);
        });

        recipeForm.setIngredients(recIngreds);

        Recipe savedRecipe = bdService.saveRecipe(recipeForm);

        Assertions.assertNotEquals(savedRecipe.getId(), 0L);
        Assertions.assertNotNull(savedRecipe.getCreateDate());
        Assertions.assertEquals(savedRecipe.getRecipeIngredients().size(), ingredsNum);
    }
}
