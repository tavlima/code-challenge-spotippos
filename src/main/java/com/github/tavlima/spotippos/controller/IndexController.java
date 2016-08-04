package com.github.tavlima.spotippos.controller;

import org.pegdown.PegDownProcessor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by thiago on 8/4/16.
 */
@RestController
public class IndexController {

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public String index() throws IOException {
        PegDownProcessor mdProcessor = new PegDownProcessor();

        List<String> lines = Files.lines(Paths.get("README.md"))
                .filter(s -> !s.matches("^\\[.*\\(https://heroku\\.com/deploy\\)$"))
                .collect(Collectors.toList());

        return mdProcessor.markdownToHtml(String.join("\n", lines));
    }

}
