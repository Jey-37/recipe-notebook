package com.recipes.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "cooked_recipes")
public class CookedRecipe
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "rec_id")
    private Recipe recipe;

    private LocalDate date;
}
