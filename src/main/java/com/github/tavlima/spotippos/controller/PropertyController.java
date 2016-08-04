package com.github.tavlima.spotippos.controller;

import com.github.tavlima.spotippos.domain.*;
import com.github.tavlima.spotippos.domain.request.CreatePropertyPayload;
import com.github.tavlima.spotippos.domain.request.FindByRegionPayload;
import com.github.tavlima.spotippos.repository.PropertyRepository;
import com.github.tavlima.spotippos.repository.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Method;
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
        return () -> {
            Property ret = null;

            TreeSet<String> provincesNames = provinceRepository.findByCoord(payload.getX(), payload.getY()).stream()
                    .map(Province::getName)
                    .collect(Collectors.toCollection(TreeSet::new));

            ret = new PropertyBuilder()
                    .from(payload)
                    .provinces(provincesNames)
                    .build();

            propertyRepository.save(ret);

            return ret;
        };
    }

    @RequestMapping(method = RequestMethod.GET)
    public Callable<MultipleProperties> getPropertyByArea(@Valid FindByRegionPayload payload) {
        return () -> {
            List<Property> properties = this.propertyRepository.findAllInRegion(payload.getAx(), payload.getAy(), payload.getBx(), payload.getBy());

            return new MultipleProperties(properties.size(), properties);
        };
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Callable<Property> getProperty(@PathVariable Long id) throws MethodArgumentNotValidException {
        if (id < 1) {
            throw new GenericBadRequestException();
        }

        return () -> {
            Property ret = this.propertyRepository.findOne(id);

            if (ret == null) {
                throw new PropertyNotFoundException();
            }

            return ret;
        };
    }
}
