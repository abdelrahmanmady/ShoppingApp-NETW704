package com.example.myapplication.units;

public class Product {
    private final int product_id;
    private final String name;
    private final String description;
    private final String image_url;

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
