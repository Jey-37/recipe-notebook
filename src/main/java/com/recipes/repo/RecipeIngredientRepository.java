package com.recipes.repo;

import com.recipes.model.RecipeIngredient;
import org.springframework.data.repository.CrudRepository;

public interface RecipeIngredientRepository extends CrudRepository<RecipeIngredient, RecipeIngredient.Id> {
}
