package com.example.myapplication;

import android.location.Location;
import java.util.Comparator;

public class Shop {
    private int id;
    private String name;
    private int price;
    private String specialOffers;
    private double latitude;
    private double longitude;
    private double distance;

    public Shop(int id, String name, int price, String specialOffers, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.specialOffers = specialOffers;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public static Comparator<Shop> shopPriceComparator=new Comparator<Shop>() {
        @Override
        public int compare(Shop s1, Shop s2) {
            return s1.getPrice() - s2.getPrice();
        }
    };
    public static Comparator<Shop> shopDistanceComparator=new Comparator<Shop>() {
        @Override
        public int compare(Shop s1, Shop s2) {
            int s1Int=(int)s1.getDistance();
            int s2Int=(int)s2.getDistance();

            return s1Int - s2Int;
        }
    };


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getSpecialOffers() {
        return specialOffers;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
