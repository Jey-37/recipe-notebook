package com.recipes.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.Set;

@Data
@ToString(exclude = "measures")
@Entity
@Table(name = "ingredient_types")
public class IngredientType
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "types_measuresjt",
            joinColumns = @JoinColumn(name = "type_id"),
            inverseJoinColumns = @JoinColumn(name = "meas_id")
    )
    private Set<Measure> measures;
}
