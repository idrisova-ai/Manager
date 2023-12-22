package com.example.demo;

public class recipeData {
    private Integer id;
    private Integer productId;
    private Integer ingredientId;
    private Integer quantity;
    private Integer kcal;
    private Integer b;
    private Integer j;
    private Integer u;
    private String instructions;

    public recipeData(Integer id, Integer productId, Integer ingredientId, Integer quantity, Integer kcal,
                      Integer b, Integer j, Integer u) {
        this.id = id;
        this.productId = productId;
        this.ingredientId = ingredientId;
        this.quantity = quantity;
        this.kcal = kcal;
        this.b = b;
        this.j = j;
        this.u = u;
    }

    public recipeData(Integer id, Integer productId, Integer ingredientId, Integer quantity, Integer kcal,
                      Integer b, Integer j, Integer u, String instructions) {
        this.id = id;
        this.productId = productId;
        this.ingredientId = ingredientId;
        this.quantity = quantity;
        this.kcal = kcal;
        this.b = b;
        this.j = j;
        this.u = u;
        this.instructions = instructions;
    }

    public Integer getId() {
        return id;
    }

    public Integer getProductId() {
        return productId;
    }

    public Integer getIngredientId() {
        return ingredientId;
    }

    public Integer getQuantity() {
        return quantity;
    }
    public Integer getKcal() {
        return kcal;
    }

    public Integer getB(){
        return b;
    }

    public Integer getJ(){
        return j;
    }

    public Integer getU(){
        return u;
    }
    public String getInstructions(){
        return instructions;
    }
}

