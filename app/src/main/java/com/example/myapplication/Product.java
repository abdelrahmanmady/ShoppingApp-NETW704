package com.example.myapplication;

public class Product {
    private int product_id;
    private String name;
    private String description;
    private String image_url;

    public Product(int product_id, String name, String description, String image_url) {
        this.product_id = product_id;
        this.name = name;
        this.description = description;
        this.image_url = image_url;
    }

    public int getProduct_id() {
        return product_id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImage_url() {
        return image_url;
    }
}
