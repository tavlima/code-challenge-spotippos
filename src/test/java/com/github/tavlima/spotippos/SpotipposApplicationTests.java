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
		features = "classpath:feature_files/Properties.feature",
		glue = "com.github.tavlima.spotippos.step"
)
public class SpotipposApplicationTests {

}
