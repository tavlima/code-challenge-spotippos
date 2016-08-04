package com.github.tavlima.spotippos.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by thiago on 8/3/16.
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Province {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;
    private Integer x0;
    private Integer y0;
    private Integer x1;
    private Integer y1;

    public Province() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getX0() {
        return x0;
    }

    public void setX0(Integer x0) {
        this.x0 = x0;
    }

    public Integer getY0() {
        return y0;
    }

    public void setY0(Integer y0) {
        this.y0 = y0;
    }

    public Integer getX1() {
        return x1;
    }

    public void setX1(Integer x1) {
        this.x1 = x1;
    }

    public Integer getY1() {
        return y1;
    }

    public void setY1(Integer y1) {
        this.y1 = y1;
    }
}
