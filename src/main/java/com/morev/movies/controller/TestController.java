package com.morev.movies.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String test() {
        String id = new String("645d97aa4682561856ea81d3");
        return id;
    }
}
