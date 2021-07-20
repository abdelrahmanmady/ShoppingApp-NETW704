package com.example.myapplication.units;

public class Favourite {
        private final String shop;
        private final String product;
        private final int price;
        private final String specialOffers;
        private final String image_url;
        private final double latitude;
    private final double longitude;



    private final int product_shop_id;

    public Favourite(int product_shop_id,String shop, String product, int price, String specialOffers, String image_url,double latitude,double longitude) {
        this.product_shop_id=product_shop_id;
        this.shop = shop;
        this.product = product;
        this.price = price;
        this.specialOffers = specialOffers;
        this.image_url = image_url;
        this.latitude=latitude;
        this.longitude=longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getShop() {
        return shop;
    }

    public String getProduct() {
        return product;
    }

    public int getPrice() {
        return price;
    }

    public String getSpecialOffers() {
        return specialOffers;
    }

    public String getImage_url() {
        return image_url;
    }
    public int getProduct_shop_id() {
        return product_shop_id;
    }
}
