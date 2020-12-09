package com.mealwith.Entity;

public class Origin {
    private int id;
    private String country;
    private String region;

    public Origin(int id, String country, String region) {
        this.id = id;
        this.country = country;
        this.region = region;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "Origin{" +
                "country='" + country + '\'' +
                '}';
    }
}
