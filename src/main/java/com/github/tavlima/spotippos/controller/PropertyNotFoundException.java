package com.github.tavlima.spotippos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by thiago on 8/3/16.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such Property")
public class PropertyNotFoundException extends RuntimeException {

}
