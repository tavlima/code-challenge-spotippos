package com.github.tavlima.spotippos.controller;

import com.github.tavlima.spotippos.domain.*;
import com.github.tavlima.spotippos.repository.PropertyRepository;
import com.github.tavlima.spotippos.repository.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

/**
 * Created by thiago on 8/3/16.
 */
@RestController
@RequestMapping(value = "/properties")
public class PropertyController {

    private final PropertyRepository propertyRepository;
    private final ProvinceRepository provinceRepository;

    @Autowired
    public PropertyController(PropertyRepository propertyRepository, ProvinceRepository provinceRepository) {
        this.propertyRepository = propertyRepository;
        this.provinceRepository = provinceRepository;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Callable<Property> createProperty(@RequestBody @Valid CreatePropertyPayload payload) {
        System.out.println("teste");

        return () -> {
            TreeSet<String> provincesNames = provinceRepository.findByCoord(payload.getX(), payload.getY()).stream()
                    .map(Province::getName)
                    .collect(Collectors.toCollection(TreeSet::new));

            Property newProperty = new PropertyBuilder()
                    .from(payload)
                    .provinces(provincesNames)
                    .build();

            propertyRepository.save(newProperty);

            return newProperty;
        };
    }

    @RequestMapping(method = RequestMethod.GET)
    public Callable<MultipleProperties> getPropertyByArea(@RequestParam Integer ax, @RequestParam Integer ay, @RequestParam Integer bx, @RequestParam Integer by) {
        return () -> {
            List<Property> properties = this.propertyRepository.findAllInRegion(ax, ay, bx, by);

            return new MultipleProperties(properties.size(), properties);
        };
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Callable<Property> getProperty(@PathVariable Long id) {
        return () -> {
            Property ret = this.propertyRepository.findOne(id);

            if (ret == null) {
                throw new PropertyNotFoundException();
            }

            return ret;
        };
    }
}
