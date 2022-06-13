package com.continuingdevelopment.probonorest.web.controller;

import org.hamcrest.Matchers;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;


class WellControllerIT {
    String baseURI = "http://localhost:8080/api/";
    @Test
    public void testGetAllWells(){
        ValidatableResponse response = given().baseUri(baseURI).when().get("wells").then();
        response.body(Matchers.containsString("Holmes"));
    }
}