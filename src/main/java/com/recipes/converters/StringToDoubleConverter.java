package com.recipes.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToDoubleConverter implements Converter<String, Double>
{
    @Override
    public Double convert(String source) {
        String qty = source.trim().replace(',', '.');

        try {
            return Double.valueOf(qty);
        } catch (NumberFormatException ex) {
            throw new NumberFormatException("RecipeIngredient's 'quantity' field can't be parsed to double");
        }
    }
}
