package com.github.tavlima.spotippos.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.tavlima.spotippos.controller.PropertyController;
import com.github.tavlima.spotippos.domain.Province;
import com.github.tavlima.spotippos.domain.request.CreatePropertyPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by thiago on 8/5/16.
 */
@Component
@SkipFromTestContext
public class DatabaseBootstrap implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ObjectMapper objectMapper;

    private final PropertyController propertyController;

    private final ProvinceRepository provinceRepository;
    private final PropertyRepository propertyRepository;

    @Autowired
    public DatabaseBootstrap(ObjectMapper objectMapper, PropertyController propertyController, ProvinceRepository provinceRepository, PropertyRepository propertyRepository) {
        this.objectMapper = objectMapper;
        this.propertyController = propertyController;
        this.provinceRepository = provinceRepository;
        this.propertyRepository = propertyRepository;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (repositoryIsEmpty(provinceRepository)) {
            bootstrapProvinces();
        }

        if (repositoryIsEmpty(propertyRepository)) {
            bootstrapProperties();
        }
    }

    private <T, ID extends Serializable> boolean repositoryIsEmpty(JpaRepository<T, ID> repository) {
        return repository.findAll(new PageRequest(0, 1)).getNumberOfElements() == 0;
    }

    private void bootstrapProvinces() throws IOException {
        JsonNode node = objectMapper.readTree(getClass().getResourceAsStream("/provinces.json"));

        List<Province> provinces = new LinkedList<>();

        if (node.isObject()) {
            ObjectNode root = (ObjectNode) node;

            for (Iterator<Map.Entry<String, JsonNode>> fieldIterator = root.fields(); fieldIterator.hasNext();) {
                Map.Entry<String, JsonNode> entry = fieldIterator.next();

                JsonNode boundaries = entry.getValue().get("boundaries");
                JsonNode upperLeft = boundaries.get("upperLeft");
                JsonNode bottomRight = boundaries.get("bottomRight");

                Province province = new Province();

                province.setName(entry.getKey());
                province.setX0(upperLeft.get("x").asInt());
                province.setY0(bottomRight.get("y").asInt());
                province.setX1(bottomRight.get("x").asInt());
                province.setY1(upperLeft.get("y").asInt());

                provinces.add(province);
            }
        }

        provinceRepository.save(provinces);

        Set<String> provincesNames = provinces.stream()
                .map(Province::getName)
                .collect(Collectors.toSet());

        logger.info("Provinces created: {}", String.join(",", provincesNames));
    }

    private void bootstrapProperties() throws IOException {
        JsonNode node = objectMapper.readTree(getClass().getResourceAsStream("/properties.json"));

        List<CreatePropertyPayload> payloads = new LinkedList<>();

        if (node.isObject()) {
            ObjectNode root = (ObjectNode) node;

            node = root.get("properties");

            if (node.isArray()) {
                for (JsonNode propertyNode : node) {
                    CreatePropertyPayload payload = objectMapper.treeToValue(propertyNode, CreatePropertyPayload.class);

                    payload.setX(propertyNode.get("lat").asInt());
                    payload.setY(propertyNode.get("long").asInt());

                    payloads.add(payload);
                }
            }
        }

        Set<String> propertiesIds = payloads.stream()
                .map(propertyController::createProperty)
                .map(c -> {
                    try {
                        return c.call().getId().toString();
                    } catch (Exception e) {
                        logger.warn("Failed to load property", e);
                        return null;
                    }
                })
                .filter(p -> p != null)
                .collect(Collectors.toSet());

        logger.info("Properties created: {}", String.join(",", propertiesIds));
    }
}
