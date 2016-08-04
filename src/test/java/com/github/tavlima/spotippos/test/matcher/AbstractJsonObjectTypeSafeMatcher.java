package com.github.tavlima.spotippos.test.matcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.TypeSafeMatcher;

import java.io.IOException;

/**
 * Created by thiago on 8/4/16.
 */
abstract class AbstractJsonObjectTypeSafeMatcher<T> extends TypeSafeMatcher<String> {

    private final ObjectMapper objectMapper;
    private final Class<T> targetClass;

    AbstractJsonObjectTypeSafeMatcher(ObjectMapper objectMapper, Class<T> clazz) {
        this.objectMapper = objectMapper;
        this.targetClass = clazz;
    }

    @Override
    protected boolean matchesSafely(String item) {
        try {
            return matchesObject(objectMapper.readValue(item, this.targetClass));
        } catch (IOException e) {
            return false;
        }
    }

    protected abstract boolean matchesObject(T object);

}
