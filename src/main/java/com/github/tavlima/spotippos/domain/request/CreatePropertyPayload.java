package com.github.tavlima.spotippos.domain.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by thiago on 8/4/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreatePropertyPayload {

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

    public CreatePropertyPayload() {
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
}
