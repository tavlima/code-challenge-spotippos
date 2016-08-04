package com.github.tavlima.spotippos.test.matcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tavlima.spotippos.domain.Property;
import org.hamcrest.Description;

import java.util.Set;

/**
 * Created by thiago on 8/4/16.
 */
public class SameProvinces extends AbstractJsonObjectTypeSafeMatcher<Property> {
    private final Set<String> expectedProvinces;

    public SameProvinces(ObjectMapper objectMapper, Set<String> expectedProvinces) {
        super(objectMapper, Property.class);
        this.expectedProvinces = expectedProvinces;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(expectedProvinces.toString());
    }

    @Override
    protected boolean matchesObject(Property object) {
        return object.getProvinces().equals(this.expectedProvinces);
    }

}
