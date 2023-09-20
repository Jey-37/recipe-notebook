package com.recipes.web.rest;

import com.recipes.model.Ingredient;
import com.recipes.repo.IngredientRepository;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/data/ingredients")
public class IngredientsController
{
    private final IngredientRepository repo;

    public IngredientsController(IngredientRepository ingredientRepository) {
        this.repo = ingredientRepository;
    }

    @GetMapping
    public Iterable<Ingredient> getIngredients(@RequestParam(name = "query") String searchQuery) {
        return StreamSupport.stream(repo.findAll().spliterator(), false)
                .filter(ing -> ingredientFilter(ing, searchQuery)).collect(Collectors.toList());
    }

    private boolean ingredientFilter(Ingredient ing, String searchQuery) {
        searchQuery = searchQuery.toLowerCase();
        String iName = ing.getName().toLowerCase();

        return iName.startsWith(searchQuery) || iName.contains(" "+searchQuery);
    }
}
