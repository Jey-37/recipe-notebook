package com.recipes.model.dto;

import com.recipes.model.Tag;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class RecipeForm
{
    private String name;
    private int portionsNumber = 1;
    private Set<Tag> tags = new HashSet<>();
    private List<String> stages = new ArrayList<>();
    private List<RecipeIngredientDto> ingredients = new ArrayList<>();
}
