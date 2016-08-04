package com.github.tavlima.spotippos.domain;

import java.util.List;

/**
 * Created by thiago on 8/3/16.
 */
public class MultipleProperties {

    private Integer foundProperties;
    private List<Property> properties;

    public MultipleProperties() {
    }

    public Integer getFoundProperties() {
        return foundProperties;
    }

    public void setFoundProperties(Integer foundProperties) {
        this.foundProperties = foundProperties;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

}
