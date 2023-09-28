package com.recipes.config;

import com.recipes.converters.IngredientByIdConverter;
import com.recipes.converters.MeasureByIdConverter;
import com.recipes.converters.StringToDoubleConverter;
import com.recipes.repo.IngredientRepository;
import com.recipes.repo.MeasureRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig
{
    @Bean
    public IngredientByIdConverter ingredientByIdConverter(IngredientRepository ingredientRepo) {
        return id -> ingredientRepo.findById(id).orElse(null);
    }

    @Bean
    public MeasureByIdConverter measureByIdConverter(MeasureRepository measureRepo) {
        return id -> measureRepo.findById(id).orElse(null);
    }

    @Bean
    public StringToDoubleConverter stringToDoubleConverter() {
        return str -> Double.valueOf(str.trim().replace(',', '.'));
    }
}
