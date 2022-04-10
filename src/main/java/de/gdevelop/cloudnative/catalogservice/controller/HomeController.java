package de.gdevelop.cloudnative.catalogservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Gerhard
 */
@RestController
public class HomeController {

    @GetMapping("/")
    public String getGreeting() {
        return "Welcome to the book catalog";
    }
}
