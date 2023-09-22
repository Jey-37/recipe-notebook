package com.recipes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipes.model.RecipeIngredient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class ModelTests
{
    @Test
    public void recipeIngredientsQuantityDeserializerTest() throws JsonProcessingException {
        String[] jsons = {
                "{\"quantity\":\"1.2\"}",
                "{\"quantity\":\"4\"}",
                "{\"quantity\":\"5,27\"}"
        };
        double[] expResults = {1.2, 4, 5.27};

        ObjectMapper objectMapper = new ObjectMapper();

        for (int i = 0; i < 3; i++) {
            RecipeIngredient ri = objectMapper
                    .readerFor(RecipeIngredient.class)
                    .readValue(jsons[i]);

            Assertions.assertEquals(ri.getQuantity(), expResults[i]);
        }
    }
}
