package com.github.tavlima.spotippos.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * Created by thiago on 8/3/16.
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Min(0)
    @Max(1400)
    @NotNull
    private Integer x;

    @Min(0)
    @Max(1000)
    @NotNull
    private Integer y;

    @NotEmpty
    private String title;

    @Min(0)
    @NotNull
    private Integer price;

    @NotEmpty
    private String description;

    @Min(1)
    @Max(5)
    @NotNull
    private Integer beds;

    @Min(1)
    @Max(4)
    @NotNull
    private Integer baths;

    @Min(20)
    @Max(240)
    @NotNull
    private Integer squareMeters;

    /**
     * Mapped as special-charater separated string due
     * to the low update frequency (expected)
     */
    private String provinces;

    public Property() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getBeds() {
        return beds;
    }

    public void setBeds(Integer beds) {
        this.beds = beds;
    }

    public Integer getBaths() {
        return baths;
    }

    public void setBaths(Integer baths) {
        this.baths = baths;
    }

    public Integer getSquareMeters() {
        return squareMeters;
    }

    public void setSquareMeters(Integer squareMeters) {
        this.squareMeters = squareMeters;
    }

    public Set<String> getProvinces() {
        return (provinces == null || provinces.isEmpty()) ?
                Collections.emptySet() :
                new TreeSet<>(Arrays.asList(provinces.split(";")));
    }

    public void setProvinces(TreeSet<String> provinces) {
        this.provinces = String.join(";", provinces);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Property property = (Property) o;
        return Objects.equals(id, property.id) &&
                Objects.equals(x, property.x) &&
                Objects.equals(y, property.y) &&
                Objects.equals(title, property.title) &&
                Objects.equals(price, property.price) &&
                Objects.equals(description, property.description) &&
                Objects.equals(beds, property.beds) &&
                Objects.equals(baths, property.baths) &&
                Objects.equals(squareMeters, property.squareMeters) &&
                Objects.equals(provinces, property.provinces);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, x, y, title, price, description, beds, baths, squareMeters, provinces);
    }
}
