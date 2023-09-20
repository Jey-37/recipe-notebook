package com.recipes.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "measures")
public class Measure
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;
}
