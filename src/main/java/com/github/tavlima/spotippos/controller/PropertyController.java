package com.github.tavlima.spotippos.controller;

import com.github.tavlima.spotippos.domain.MultipleProperties;
import com.github.tavlima.spotippos.domain.Property;
import com.github.tavlima.spotippos.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by thiago on 8/3/16.
 */
@RestController
@Validated
public class PropertyController {

    private final PropertyRepository repository;

    @Autowired
    public PropertyController(PropertyRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/properties", method = RequestMethod.POST)
    public Callable<Long> createProperty(@RequestBody Property property) {
        return () -> -1L;
    }

    @RequestMapping(value = "/properties", method = RequestMethod.GET)
    public Callable<MultipleProperties> getPropertyByArea(
            @RequestParam @Min(0) @Max(1200) Integer ax,
            @RequestParam @Min(0) @Max(1000) Integer ay,
            @RequestParam @Min(0) @Max(1200) Integer bx,
            @RequestParam @Min(0) @Max(1000) Integer by) {
        return () -> {
            List<Property> properties = this.repository.findAllInRegion(ax, ay, bx, by);

            return new MultipleProperties(properties.size(), properties);
        };
    }

    @RequestMapping(value = "/properties/{id}", method = RequestMethod.GET)
    public Callable<Property> getProperty(@PathVariable @Min(1) Long id) {
        return () -> {
            Property ret = this.repository.findOne(id);

            if (ret == null) {
                throw new PropertyNotFoundException();
            }

            return ret;
        };
    }
}
