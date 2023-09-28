package com.recipes.converters;

import com.recipes.model.Measure;
import org.springframework.core.convert.converter.Converter;

public interface MeasureByIdConverter extends Converter<Integer, Measure> {
}
