package com.github.tavlima.spotippos.test.matcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tavlima.spotippos.domain.Property;
import org.hamcrest.Description;

/**
 * Created by thiago on 8/4/16.
 */
public class SamePropertyId extends AbstractJsonObjectTypeSafeMatcher<Property> {
    private final Long expectedId;

    public SamePropertyId(ObjectMapper objectMapper, Long expectedId) {
        super(objectMapper, Property.class);
        this.expectedId = expectedId;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(expectedId.toString());
    }

    @Override
    protected boolean matchesObject(Property object) {
        return expectedId.equals(object.getId());
    }
}
