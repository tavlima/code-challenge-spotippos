package com.github.tavlima.spotippos.step;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tavlima.spotippos.SpotipposApplication;
import com.github.tavlima.spotippos.domain.Property;
import com.github.tavlima.spotippos.repository.PropertyRepository;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

/**
 * Created by thiago on 8/3/16.
 */
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@ContextConfiguration(classes = SpotipposApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class PropertiesSteps {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private ResultActions lastResult;

    @Given("^that the properties database has the records:$")
    public void thatThePropertiesDatabaseHasTheRecords(List<String> records) throws Throwable {
        List<Property> properties = records.stream()
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

    @When("^a GET /property/'([^']+)' request is received$")
    public void aGETPropertyIdRequestIsReceived(String id) throws Throwable {
        this.lastResult = doAsyncRequest(get("/properties/" + id).accept(MediaType.APPLICATION_JSON_UTF8));
    }

    @Then("^it should return the property '([^']+)'$")
    public void itShouldReturnTheProperty(String propertyJson) throws Throwable {
        this.lastResult
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(propertyJson))
                .andReturn();
    }

    @Then("^it should return a (\\d+) status error$")
    public void itShouldReturnA404StatusError(int status) throws Throwable {
        this.lastResult
                .andExpect(status().is(status))
                .andReturn();
    }

    private ResultActions doAsyncRequest(RequestBuilder request) throws Exception {
        MvcResult mvcResult = this.mvc.perform(request)
                .andReturn();

        return this.mvc.perform(asyncDispatch(mvcResult));
    }
}
