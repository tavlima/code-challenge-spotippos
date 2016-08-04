package com.github.tavlima.spotippos;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import cucumber.api.junit.Cucumber;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(Cucumber.class)
@CucumberOptions(
		strict = true,
		features = {
				"classpath:feature_files/GetProperty.feature",
				"classpath:feature_files/PropertiesInRegion.feature",
				"classpath:feature_files/CreateProperty.feature"
		},
		glue = "com.github.tavlima.spotippos.test.step"
)
public class SpotipposApplicationTests {

}
