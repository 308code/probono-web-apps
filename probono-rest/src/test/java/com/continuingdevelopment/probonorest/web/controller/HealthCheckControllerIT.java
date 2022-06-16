package com.continuingdevelopment.probonorest.web.controller;

import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

//@SpringBootTest
class HealthCheckControllerIT {
    String baseURI = "http://localhost:8080/api/";
    @Test
    public void testHealthCheckController(){
        ValidatableResponse response =
                given()
                        .baseUri(baseURI)
                        .when()
                        .get("healthcheck")
                        .then();
        response.body(Matchers.hasToString("Health Check successfully reached!"));
        response.statusCode(HttpStatus.OK.value());
    }
}