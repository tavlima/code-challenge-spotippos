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
public class SamePropertyIds extends TypeSafeMatcher<String> {
    private final Set<Long> expectedIds;
    private final ObjectMapper objectMapper;

    public SamePropertyIds(ObjectMapper objectMapper, Set<Long> expectedIds) {
        this.objectMapper = objectMapper;
        this.expectedIds = expectedIds;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(expectedIds.toString());
    }

    @Override
    protected boolean matchesSafely(String item) {
        try {
            MultipleProperties response = objectMapper.readValue(item, MultipleProperties.class);

            Set<Long> foundPropertyIds = response.getProperties().stream()
                    .map(Property::getId)
                    .collect(Collectors.toSet());

            return foundPropertyIds.equals(expectedIds);

        } catch (IOException e) {
            return false;
        }
    }
}
