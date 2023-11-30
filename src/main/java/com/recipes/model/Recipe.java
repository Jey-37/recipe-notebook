package com.recipes.model;

import com.recipes.model.dto.RecipeForm;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "recipes")
public class Recipe
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Recipe name is required")
    @Column(nullable = false)
    private String name;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createDate;

    @Positive @Max(25)
    private int portionsNumber;

    private String description;

    private String mainPhotoName;

    @Size(min = 1, message = "You must choose at least one tag")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "recipes_tagsjt",
            joinColumns = @JoinColumn(name = "rec_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    @Size(min = 1, message = "The recipe must have at least one cooking stage")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "cooking_stages",
            joinColumns = @JoinColumn(name = "rec_id")
    )
    @Column(name = "text", length = 1000, nullable = false)
    @OrderColumn(name = "stage", nullable = false)
    private List<String> stages = new ArrayList<>();

    @Setter(AccessLevel.NONE)
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "rec_id")
    @org.hibernate.annotations.OrderBy(clause = "num")
    private Set<RecipeIngredient> ingredients = new LinkedHashSet<>();

    public void addIngredient(RecipeIngredient recipeIngredient) {
        recipeIngredient.setNum(ingredients.size());
        ingredients.add(recipeIngredient);
    }

    public RecipeForm toRecipeForm() {
        RecipeForm form = new RecipeForm();
        form.setId(id);
        form.setName(name);
        form.setPortionsNumber(portionsNumber);
        form.setDescription(description);
        form.setTags(tags);
        form.setStages(stages);
        form.setIngredients(ingredients.stream().toList());

        return form;
    }
}
