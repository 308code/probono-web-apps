package com.continuingdevelopment.probonorest.web.controller;

import com.continuingdevelopment.probonorest.web.aspects.LogMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/healthcheck")
public class HealthCheckController {

    @LogMethod(level = "INFO")
    @GetMapping
    public ResponseEntity<String> getHealthCheck(){
        return new ResponseEntity<>("Health Check successfully reached!", HttpStatus.OK);
    }
}
