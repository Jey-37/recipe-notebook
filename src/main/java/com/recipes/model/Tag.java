package com.recipes.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tags")
public class Tag
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;
}
