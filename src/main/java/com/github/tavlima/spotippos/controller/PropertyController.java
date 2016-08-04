package com.github.tavlima.spotippos.controller;

import com.github.tavlima.spotippos.domain.MultipleProperties;
import com.github.tavlima.spotippos.domain.Property;
import com.github.tavlima.spotippos.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by thiago on 8/3/16.
 */
@RestController
@RequestMapping(value = "/properties")
public class PropertyController {

    private final PropertyRepository repository;

    @Autowired
    public PropertyController(PropertyRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Callable<Property> createProperty(
            @RequestBody @Valid Property property) {
        return () -> property;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Callable<MultipleProperties> getPropertyByArea(
            @RequestParam Integer ax,
            @RequestParam Integer ay,
            @RequestParam Integer bx,
            @RequestParam Integer by) {
        return () -> {
            List<Property> properties = this.repository.findAllInRegion(ax, ay, bx, by);

            return new MultipleProperties(properties.size(), properties);
        };
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Callable<Property> getProperty(
            @PathVariable Long id) {
        return () -> {
            Property ret = this.repository.findOne(id);

            if (ret == null) {
                throw new PropertyNotFoundException();
            }

            return ret;
        };
    }
}
