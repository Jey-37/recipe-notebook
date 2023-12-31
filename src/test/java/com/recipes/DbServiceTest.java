package com.recipes;

import com.recipes.model.*;
import com.recipes.repo.IngredientRepository;
import com.recipes.repo.RecipeRepository;
import com.recipes.repo.TagRepository;
import com.recipes.service.DbService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
public class DbServiceTest
{
    @Autowired
    private RecipeRepository recipeRepo;

    @Autowired
    private IngredientRepository ingredientRepo;

    @Autowired
    private TagRepository tagRepo;

    @Autowired
    private DbService dbService;

    public Recipe createRecipe() {
        Recipe recipe = new Recipe();

        recipe.setName("Test Recipe");
        recipe.setPortionsNumber(3);

        List<Tag> tags = new ArrayList<>((Collection<Tag>) tagRepo.findAll());
        Collections.shuffle(tags);
        tags.subList(0, 3).forEach(recipe.getTags()::add);

        for (int i = 0; i < 3; i++) {
            recipe.getStages().add("Stage "+i);
        }

        List<Ingredient> ingreds = new ArrayList<>((Collection<Ingredient>) ingredientRepo.findAll());
        Collections.shuffle(ingreds);
        for (int i = 0; i < 5; i++) {
            recipe.addIngredient(new RecipeIngredient(
                    ingreds.get(i), ingreds.get(i).getType().getMeasures().iterator().next(), 1, null));
        }

        return recipe;
    }

    @Test
    public void recipeSaving() {
        Recipe recipe = createRecipe();

        int ingNum = recipe.getIngredients().size();

        recipe = dbService.saveRecipe(recipe.toRecipeForm());

        Assertions.assertNotEquals(0L, recipe.getId());
        Assertions.assertNotNull(recipe.getCreateDate());

        Recipe savedRecipe = recipeRepo.findById(recipe.getId()).get();
        Assertions.assertEquals(ingNum, savedRecipe.getIngredients().size());
        int i = 0;
        for (var ri : savedRecipe.getIngredients()) {
            Assertions.assertEquals(i++, ri.getNum());
        }
    }

    @Test
    public void recipeUpdating() {
        Recipe oldRecipe = dbService.saveRecipe(createRecipe().toRecipeForm());

        Recipe newRecipe = createRecipe();
        newRecipe.setId(oldRecipe.getId());

        dbService.updateRecipe(newRecipe.toRecipeForm());

        Recipe loadedRecipe = recipeRepo.findById(newRecipe.getId()).get();

        Assertions.assertEquals(newRecipe.getName(), loadedRecipe.getName());
        Assertions.assertEquals(newRecipe.getPortionsNumber(), loadedRecipe.getPortionsNumber());
        Assertions.assertEquals(newRecipe.getTags(), loadedRecipe.getTags());
        Assertions.assertEquals(newRecipe.getStages(), loadedRecipe.getStages());
        Assertions.assertEquals(newRecipe.getIngredients().size(), loadedRecipe.getIngredients().size());
        for (Iterator<RecipeIngredient> i = newRecipe.getIngredients().iterator(),
             j = loadedRecipe.getIngredients().iterator(); i.hasNext() && j.hasNext();) {
            Assertions.assertEquals(i.next(), j.next());
        }
    }
}
