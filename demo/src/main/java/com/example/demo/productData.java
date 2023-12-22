package com.example.demo;

public class productData {
    private Integer id;
    private Integer productId;
    private String productName;
    private String type;
    private String image;
    private Integer time;
    private Double bju;
    private Integer difficulty;
    private Integer quantity;

    public productData(Integer id, Integer productId, String productName
            , String type, Integer time, Double bju, Integer difficulty, String image) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.type = type;
        this.time = time;
        this.bju = bju;
        this.difficulty = difficulty;
        this.image = image;
    }

    public productData(Integer id, Integer productId, String productName
            , String type, Integer time, Double bju, Integer difficulty, Integer quantity, String image) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.type = type;
        this.time = time;
        this.bju = bju;
        this.difficulty = difficulty;
        this.quantity = quantity;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public Integer getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getType() {
        return type;
    }

    public String getImage() {
        return image;
    }

    public Integer getTime() {
        return time;
    }

    public Double getBju() {
        return bju;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
