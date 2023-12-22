package com.example.demo;

public class ingredientData {
    private Integer id;
    private Integer productId;
    private String title;
    private String type;

    public ingredientData(Integer id, Integer productId, String title, String type) {
        this.id = id;
        this.productId = productId;
        this.title = title;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public Integer getProductId() {
        return productId;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }
}

