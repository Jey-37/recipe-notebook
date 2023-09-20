package com.recipes.web;

import com.recipes.model.Ingredient;
import com.recipes.model.Recipe;
import com.recipes.model.RecipeIngredient;
import com.recipes.model.Tag;
import com.recipes.repo.IngredientRepository;
import com.recipes.repo.RecipeIngredientRepository;
import com.recipes.repo.RecipeRepository;
import com.recipes.repo.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;
import java.util.logging.Logger;

@Controller
@RequestMapping("/recipes/add")
public class AddRecipeController
{
    private final Logger log = Logger.getLogger(AddRecipeController.class.getName());

    private final IngredientRepository ingredientRepository;

    @Autowired
    private RecipeRepository recipeRepo;

    @Autowired
    private TagRepository tagRepo;

    @Autowired
    private RecipeIngredientRepository recIngRepo;

    public AddRecipeController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    /*@GetMapping
    public String addRecipePage(Model model) {
        model.addAttribute("recipe", new Recipe());
        //model.addAttribute("ingredients", ingredientRepository.findAll());
        //log.info("The number of ingredients: "+ingredientRepository.count());
        return "addRecipe";
    }*/

    @GetMapping
    public String addRecipePage(Model model) {
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

        Set<RecipeIngredient> recIngreds = new HashSet<>();
        List<Ingredient> ingreds = new ArrayList<>((Collection<Ingredient>) ingredientRepository.findAll());
        Collections.shuffle(ingreds);
        int ingredsNum = new Random().nextInt(3, 8);
        ingreds.subList(0, ingredsNum).forEach(ing ->
                recIngreds.add(new RecipeIngredient(recipe, ing, ing.getType().getMeasures().iterator().next())));
        recIngreds.forEach(ri -> ri.setQuantity(new Random().nextInt(1, 10)));

        //recIngRepo.saveAll(recIngreds);
        //log.info(recIngreds.toString());

        recipe.setRecipeIngredients(recIngreds);

        model.addAttribute("recipe", recipe);

        return "addRecipe";
    }

    @PostMapping
    public String postRecipe(Recipe recipe) {
        return "redirect:/";
    }
}
