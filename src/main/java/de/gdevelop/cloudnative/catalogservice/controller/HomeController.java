package de.gdevelop.cloudnative.catalogservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Gerhard
 */
@RestController
public class HomeController {

    @Value("${polar.greeting}:Hello fallback")
    String greeting;

    @GetMapping("/")
    public String getGreeting() {
        return greeting;
    }
}
