package com.github.tavlima.spotippos.step.matcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tavlima.spotippos.domain.MultipleProperties;
import com.github.tavlima.spotippos.domain.Property;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by thiago on 8/4/16.
 */
public class SamePropertyId extends TypeSafeMatcher<String> {
    private final Long expectedId;
    private final ObjectMapper objectMapper;

    public SamePropertyId(ObjectMapper objectMapper, Long expectedId) {
        this.objectMapper = objectMapper;
        this.expectedId = expectedId;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(expectedId.toString());
    }

    @Override
    protected boolean matchesSafely(String item) {
        try {
            Property response = objectMapper.readValue(item, Property.class);

            return expectedId.equals(response.getId());

        } catch (IOException e) {
            return false;
        }
    }
}
