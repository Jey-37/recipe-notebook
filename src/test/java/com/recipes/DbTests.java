package com.recipes;

import com.recipes.model.*;
import com.recipes.repo.IngredientRepository;
import com.recipes.repo.RecipeIngredientRepository;
import com.recipes.repo.RecipeRepository;
import com.recipes.repo.TagRepository;
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

    @Test
    public void fullSavingOfRecipe() {
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
                recIngreds.add(new RecipeIngredient(recipe, ing, ing.getType().getMeasures().iterator().next())));

        recIngRepo.saveAll(recIngreds);

        Assertions.assertEquals(recipe.getRecipeIngredients().size(), ingredsNum);

        Recipe savedRecipe = recipeRepo.findById(recipe.getId()).get();
        Assertions.assertEquals(savedRecipe.getRecipeIngredients().size(), ingredsNum);
    }
}
