package com.github.tavlima.spotippos.test.matcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tavlima.spotippos.domain.MultipleProperties;
import com.github.tavlima.spotippos.domain.Property;
import org.hamcrest.Description;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by thiago on 8/4/16.
 */
public class SamePropertyIds extends AbstractJsonObjectTypeSafeMatcher<MultipleProperties> {
    private final Set<Long> expectedIds;

    public SamePropertyIds(ObjectMapper objectMapper, Set<Long> expectedIds) {
        super(objectMapper, MultipleProperties.class);
        this.expectedIds = expectedIds;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(expectedIds.toString());
    }

    @Override
    protected boolean matchesObject(MultipleProperties object) {
        Set<Long> foundPropertyIds = object.getProperties().stream()
                .map(Property::getId)
                .collect(Collectors.toSet());

        return foundPropertyIds.equals(expectedIds);
    }
}
