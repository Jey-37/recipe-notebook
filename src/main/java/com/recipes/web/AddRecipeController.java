package com.recipes.web;

import com.recipes.model.Recipe;
import com.recipes.model.dto.RecipeForm;
import com.recipes.repo.TagRepository;
import com.recipes.service.BDService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.logging.Logger;

@Controller
@RequestMapping("/recipes/add")
public class AddRecipeController
{
    private final Logger log = Logger.getLogger(AddRecipeController.class.getName());

    private final TagRepository tagRepo;

    private final BDService bdService;

    public AddRecipeController(TagRepository tagRepo, BDService bdService) {
        this.tagRepo = tagRepo;
        this.bdService = bdService;
    }

    @GetMapping
    public String addRecipePage(Model model) {
        model.addAttribute("recipe", new RecipeForm());
        model.addAttribute("tags", tagRepo.findAll());

        return "addRecipe";
    }

    @PostMapping
    public String postRecipe(RecipeForm recipeForm) {
        Recipe recipe = bdService.saveRecipe(recipeForm);

        log.info("Saved recipe: "+recipe.toString());

        return "redirect:/";
    }
}
