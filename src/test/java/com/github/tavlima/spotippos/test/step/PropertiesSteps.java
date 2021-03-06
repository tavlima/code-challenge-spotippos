package com.github.tavlima.spotippos.test.step;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tavlima.spotippos.SpotipposApplication;
import com.github.tavlima.spotippos.domain.Property;
import com.github.tavlima.spotippos.domain.Province;
import com.github.tavlima.spotippos.repository.PropertyRepository;
import com.github.tavlima.spotippos.repository.ProvinceRepository;
import com.github.tavlima.spotippos.repository.SkipFromTestContextTypeExcludeFilter;
import com.github.tavlima.spotippos.test.matcher.SamePropertyId;
import com.github.tavlima.spotippos.test.matcher.SamePropertyIds;
import com.github.tavlima.spotippos.test.matcher.SameProvinces;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.filter.TypeExcludeFilters;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by thiago on 8/3/16.
 */
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@ContextConfiguration(classes = SpotipposApplication.class)
@TypeExcludeFilters(SkipFromTestContextTypeExcludeFilter.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class PropertiesSteps {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private ResultActions lastResult;

    @Given("^that the properties database has the records:$")
    public void thatThePropertiesDatabaseHasTheRecords(List<String> propertiesJson) throws Throwable {
        List<Property> properties = propertiesJson.stream()
                .map(s -> {
                    try {
                        return objectMapper.readValue(s, Property.class);
                    } catch (IOException e) {
                        logger.warn("Failed to parse record: '{}'", s);
                        return null;
                    }
                })
                .filter(obj -> obj != null)
                .collect(Collectors.toList());

        this.propertyRepository.save(properties);
    }

    @Given("^that the provinces database has the records:$")
    public void thatTheProvincesDatabaseHasTheRecords(List<String> provincesJson) throws Throwable {
        List<Province> properties = provincesJson.stream()
                .map(s -> {
                    try {
                        return objectMapper.readValue(s, Province.class);
                    } catch (IOException e) {
                        logger.warn("Failed to parse record: '{}'", s);
                        return null;
                    }
                })
                .filter(obj -> obj != null)
                .collect(Collectors.toList());

        this.provinceRepository.save(properties);
    }

    @When("^a valid GetProperty request is received for id ([^ ]+)$")
    public void aValidGetPropertyRequestIsReceivedForId(String id) throws Throwable {
        this.lastResult = doRequest(buildGetPropertyRequest(id));
    }

    @When("^an invalid GetProperty request is received for id ([^ ]+)$")
    public void anInvalidGetPropertyRequestIsReceivedForId(String id) throws Throwable {
        this.lastResult = doRequest(buildGetPropertyRequest(id));
    }

    @When("^a valid FindPropertiesInRegion request is received with parameters '([^']+)'$")
    public void aValidFindPropertiesInRegionRequestIsReceivedWithParameters(String parametersJson) throws Throwable {
        this.lastResult = doRequest(buildFindInRegionRequest(parametersJson));
    }

    @When("^an invalid FindPropertiesInRegion request is received with parameters '([^']+)'$")
    public void anInvalidFindPropertiesInRegionRequestIsReceivedWithParameters(String parametersJson) throws Throwable {
        this.lastResult = doRequest(buildFindInRegionRequest(parametersJson));
    }

    @When("^a valid CreateProperty request is received with payload '([^']+)'$")
    public void aValidCreatePropertyRequestIsReceivedWithPayload(String payloadJson) throws Throwable {
        this.lastResult = doRequest(buildCreateProperty(payloadJson));
    }

    @When("^an invalid CreateProperty request is received with payload '([^']+)'$")
    public void anInvalidCreatePropertyRequestIsReceivedWithPayload(String payloadJson) throws Throwable {
        this.lastResult = doRequest(buildCreateProperty(payloadJson));
    }

    @Then("^it should return a (\\d+) status error$")
    public void itShouldReturnAStatusError(int status) throws Throwable {
        this.lastResult
                .andExpect(status().is(status))
                .andReturn();
    }

    @Then("^it should return the property '([^']+)'$")
    public void itShouldReturnTheProperty(String propertyJson) throws Throwable {
        this.lastResult
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(propertyJson))
                .andReturn();
    }

    @Then("^it should return the properties with id '([^']*)'$")
    public void itShouldReturnThePropertiesWithId(String propertyIdsCSV) throws Throwable {
        Set<Long> expectedIds = Collections.emptySet();

        if (! propertyIdsCSV.isEmpty()) {
            expectedIds = Arrays.asList(propertyIdsCSV.split(",")).stream().map(Long::valueOf).collect(Collectors.toSet());
        }

        this.lastResult
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(samePropertyIds(expectedIds)))
                .andReturn();
    }

    @Then("^it should return the new property id (\\d+)$")
    public void itShouldReturnTheNewPropertyId(long newPropertyId) throws Throwable {
        this.lastResult
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(samePropertyId(newPropertyId)));
    }

    @Then("^this new property will belong to provinces '([^']*)'$")
    public void thisNewPropertyWillBelongToProvinces(String provincesNamesCSV) throws Throwable {
        Set<String> expectedProvinces = Collections.emptySet();

        if (! provincesNamesCSV.isEmpty()) {
            expectedProvinces = new TreeSet<>(Arrays.asList(provincesNamesCSV.split(",")));
        }

        this.lastResult
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(sameProvinces(expectedProvinces)));
    }

    private MockHttpServletRequestBuilder buildFindInRegionRequest(String parametersJson) throws IOException {
        MockHttpServletRequestBuilder builder = get("/v1/properties")
                .accept(MediaType.APPLICATION_JSON_UTF8);

        Map<String, String> parameters = objectMapper.readValue(parametersJson, new TypeReference<Map<String, String>>(){});

        for (Map.Entry<String, String> e : parameters.entrySet()) {
            builder.param(e.getKey(), e.getValue());
        }

        return builder;
    }

    private MockHttpServletRequestBuilder buildGetPropertyRequest(String id) {
        return get("/v1/properties/" + id)
                .accept(MediaType.APPLICATION_JSON_UTF8);
    }

    private MockHttpServletRequestBuilder buildCreateProperty(String payloadJson) {
        return post("/v1/properties")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(payloadJson);
    }

    private ResultActions doRequest(RequestBuilder request) throws Exception {
        ResultActions ret;

        try {
            MvcResult mvcResult = this.mvc.perform(request)
                    .andReturn();

            mvcResult.getAsyncResult();

            ret = this.mvc.perform(asyncDispatch(mvcResult));

        } catch (IllegalStateException e) {
            ret = this.mvc.perform(request);
        }

        return ret;
    }

    private SamePropertyIds samePropertyIds(Set<Long> expectedIds) {
        return new SamePropertyIds(this.objectMapper, expectedIds);
    }

    private SamePropertyId samePropertyId(long expectedId) {
        return new SamePropertyId(this.objectMapper, expectedId);
    }

    private SameProvinces sameProvinces(Set<String> expectedProvinces) {
        return new SameProvinces(this.objectMapper, expectedProvinces);
    }

}
