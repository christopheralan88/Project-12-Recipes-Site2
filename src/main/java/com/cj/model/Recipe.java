package com.cj.model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Recipe extends BaseEntity {
    @Column
    private String name;
    @ManyToOne
    private Category category;
    @Column
    private String image;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Ingredient> ingredients;
    @OneToMany(cascade =  CascadeType.ALL)
    private List<Instruction> instructions;
    @Column
    private Long preparationTime;
    @Column
    private Long cookTime;
    @Column
    private String description;
    @ManyToOne
    private User user;
    private boolean favorited = false;


    public Recipe() {
        super();
        category = new Category();
        ingredients = new ArrayList<>();
        instructions = new ArrayList<>();
    }

    public Recipe(String name, Category category, String image, List<Ingredient> ingredients, List<Instruction> instructions,
                  Long prepTime, Long cookTime, String description, User user) {
        this();
        this.name = name;
        this.category = category;
        this.image = image;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.preparationTime = prepTime;
        this.cookTime = cookTime;
        this.description = description;
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<Instruction> instructions) {
        this.instructions = instructions;
    }

    public Long getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(Long preparationTime) {
        this.preparationTime = preparationTime;
    }

    public Long getCookTime() {
        return cookTime;
    }

    public void setCookTime(Long cookTime) {
        this.cookTime = cookTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recipe)) return false;

        Recipe recipe = (Recipe) o;

        if (favorited != recipe.favorited) return false;
        if (name != null ? !name.equals(recipe.name) : recipe.name != null) return false;
        if (category != null ? !category.equals(recipe.category) : recipe.category != null) return false;
        if (image != null ? !image.equals(recipe.image) : recipe.image != null) return false;
        if (ingredients != null ? !ingredients.equals(recipe.ingredients) : recipe.ingredients != null) return false;
        if (instructions != null ? !instructions.equals(recipe.instructions) : recipe.instructions != null)
            return false;
        if (preparationTime != null ? !preparationTime.equals(recipe.preparationTime) : recipe.preparationTime != null)
            return false;
        if (cookTime != null ? !cookTime.equals(recipe.cookTime) : recipe.cookTime != null) return false;
        if (description != null ? !description.equals(recipe.description) : recipe.description != null) return false;
        return user != null ? user.equals(recipe.user) : recipe.user == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (ingredients != null ? ingredients.hashCode() : 0);
        result = 31 * result + (instructions != null ? instructions.hashCode() : 0);
        result = 31 * result + (preparationTime != null ? preparationTime.hashCode() : 0);
        result = 31 * result + (cookTime != null ? cookTime.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (favorited ? 1 : 0);
        return result;
    }
}
