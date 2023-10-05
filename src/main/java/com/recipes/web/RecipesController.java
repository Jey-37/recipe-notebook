package com.recipes.web;

import com.recipes.model.Recipe;
import com.recipes.model.dto.RecipeForm;
import com.recipes.repo.RecipeRepository;
import com.recipes.repo.TagRepository;
import com.recipes.service.DbService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.logging.Logger;

@Controller
@RequestMapping("/recipes")
public class RecipesController
{
    private final Logger log = Logger.getLogger(RecipesController.class.getName());

    private final TagRepository tagRepo;
    private final RecipeRepository recipeRepo;

    private final DbService dbService;

    public RecipesController(TagRepository tagRepo, RecipeRepository recipeRepo, DbService dbService) {
        this.tagRepo = tagRepo;
        this.recipeRepo = recipeRepo;
        this.dbService = dbService;
    }

    @GetMapping(path = "/add")
    public String addRecipePage(Model model) {
        model.addAttribute("recipe", new RecipeForm());
        model.addAttribute("tags", tagRepo.findAll());

        return "recipeForm";
    }

    @PostMapping(path = "/add")
    public String postRecipe(RecipeForm recipeForm) {
        Recipe recipe = dbService.saveRecipe(recipeForm.toRecipe());

        log.info("Saved recipe: "+recipe.toString());

        return "redirect:/";
    }

    @GetMapping
    public String listOfRecipesPage(Model model) {
        model.addAttribute("recipes", recipeRepo.findAll());
        return "recipeList";
    }

    @GetMapping(path = "/{id}/edit")
    public String editRecipePage(@PathVariable Long id, Model model) {
        Recipe recipe = recipeRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        model.addAttribute("recipe", recipe.toRecipeForm());
        model.addAttribute("tags", tagRepo.findAll());
        model.addAttribute("editMode", true);

        return "recipeForm";
    }

    @PutMapping(path = "/{id}/edit")
    public String editRecipe(RecipeForm recipeForm) {
        dbService.updateRecipe(recipeForm.toRecipe());

        log.info("Edited recipe: "+recipeRepo.findById(recipeForm.getId()));

        return "redirect:/";
    }

}
