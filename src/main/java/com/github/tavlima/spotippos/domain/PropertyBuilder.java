package com.github.tavlima.spotippos.domain;

import com.github.tavlima.spotippos.domain.request.CreatePropertyPayload;

import java.util.TreeSet;

public class PropertyBuilder {
    private Integer x;
    private Integer y;
    private String title;
    private Integer price;
    private String description;
    private Integer beds;
    private Integer baths;
    private Integer squareMeters;
    private String provinces;

    public PropertyBuilder x(Integer x) {
        this.x = x;
        return this;
    }

    public PropertyBuilder y(Integer y) {
        this.y = y;
        return this;
    }

    public PropertyBuilder title(String title) {
        this.title = title;
        return this;
    }

    public PropertyBuilder price(Integer price) {
        this.price = price;
        return this;
    }

    public PropertyBuilder description(String description) {
        this.description = description;
        return this;
    }

    public PropertyBuilder beds(Integer beds) {
        this.beds = beds;
        return this;
    }

    public PropertyBuilder baths(Integer baths) {
        this.baths = baths;
        return this;
    }

    public PropertyBuilder squareMeters(Integer squareMeters) {
        this.squareMeters = squareMeters;
        return this;
    }

    public PropertyBuilder provinces(String provinces) {
        this.provinces = provinces;
        return this;
    }

    public PropertyBuilder provinces(TreeSet<String> provinces) {
        this.provinces = String.join(";", provinces);
        return this;
    }

    public PropertyBuilder from(CreatePropertyPayload payload) {
        this.x = payload.getX();
        this.y = payload.getY();
        this.title = payload.getTitle();
        this.description = payload.getDescription();
        this.beds = payload.getBeds();
        this.baths = payload.getBaths();
        this.price = payload.getPrice();
        this.squareMeters = payload.getSquareMeters();

        return this;
    }

    public Property build() {
        return new Property(x, y, title, price, description, beds, baths, squareMeters, provinces);
    }
}