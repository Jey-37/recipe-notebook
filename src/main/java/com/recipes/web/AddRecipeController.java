package com.recipes.web;

import com.recipes.model.Ingredient;
import com.recipes.model.Recipe;
import com.recipes.model.RecipeIngredient;
import com.recipes.model.Tag;
import com.recipes.model.dto.RecipeForm;
import com.recipes.model.dto.RecipeIngredientDto;
import com.recipes.repo.IngredientRepository;
import com.recipes.repo.RecipeIngredientRepository;
import com.recipes.repo.RecipeRepository;
import com.recipes.repo.TagRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;
import java.util.logging.Logger;

@Controller
@RequestMapping("/recipes/add")
public class AddRecipeController
{
    private final Logger log = Logger.getLogger(AddRecipeController.class.getName());

    private final IngredientRepository ingredientRepo;

    private final RecipeRepository recipeRepo;

    private final TagRepository tagRepo;

    private final RecipeIngredientRepository recIngRepo;

    public AddRecipeController(RecipeRepository recipeRepo, IngredientRepository ingredientRepo,
                               TagRepository tagRepo, RecipeIngredientRepository recIngRepo) {
        this.recipeRepo = recipeRepo;
        this.ingredientRepo = ingredientRepo;
        this.tagRepo = tagRepo;
        this.recIngRepo = recIngRepo;
    }

    /*@GetMapping
    public String addRecipePage(Model model) {
        model.addAttribute("recipe", new RecipeForm());
        model.addAttribute("tags", tagRepo.findAll());

        return "addRecipe";
    }*/

    @GetMapping
    public String addRecipePage(Model model) {
        RecipeForm recipe = new RecipeForm();
        recipe.setName("Test Recipe");
        recipe.setPortionsNumber(5);

        List<Tag> tags = new ArrayList<>((Collection<Tag>) tagRepo.findAll());
        Collections.shuffle(tags);
        tags.subList(0, 3).forEach(recipe::addTag);

        for (int i = 0; i < 3; i++) {
            recipe.addCookingStage("Stage "+i);
        }

        List<RecipeIngredientDto> recIngreds = new ArrayList<>();
        List<Ingredient> ingreds = new ArrayList<>((Collection<Ingredient>) ingredientRepo.findAll());
        Collections.shuffle(ingreds);
        int ingredsNum = new Random().nextInt(3, 8);
        ingreds.subList(0, ingredsNum).forEach(ing ->
                recIngreds.add(new RecipeIngredientDto(ing, ing.getType().getMeasures().iterator().next().getId())));
        recIngreds.forEach(ri -> ri.setQuantity(new Random().nextInt(1, 10)));

        recipe.setIngredients(recIngreds);

        model.addAttribute("recipe", recipe);
        model.addAttribute("tags", tags);

        return "addRecipe";
    }

    @PostMapping
    public String postRecipe(@ModelAttribute RecipeForm recipe) {
        log.info(recipe.toString());
        return "redirect:/";
    }
}
