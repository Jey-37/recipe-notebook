package com.recipes.converters;

import com.recipes.model.Ingredient;
import org.springframework.core.convert.converter.Converter;

public interface IngredientByIdConverter extends Converter<Integer, Ingredient> {
}
