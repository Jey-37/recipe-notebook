package com.recipes.repo;

import com.recipes.model.Measure;
import org.springframework.data.repository.CrudRepository;

public interface MeasureRepository extends CrudRepository<Measure, Integer> {
}
