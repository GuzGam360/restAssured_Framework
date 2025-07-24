package org.example;


import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class Main {
    public static void main(String[] args) {

        //given - set all input details
        //when - submit the API - resource, http method
        //Then - validate the response

        //RestAssured.baseURI = "https://automationexercise.com";
        RestAssured.baseURI = "https://rahulshettyacademy.com";

        String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body(payload.AddPlace())
                .when().post("/maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200).body("status", equalTo("OK"))
                .header("server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();

        System.out.println("This is the response of the POST METHOD: "+response);
        JsonPath js = new JsonPath(response); // For parsing Json
        String placeID = js.getString("place_id");

        System.out.println("Extracted Value: "+placeID);

        //Update place
        String newAddress = "70 Summer Walk, Africa";


        given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"place_id\": \""+placeID+"\",\n" +
                        "    \"address\": \""+newAddress+"\",\n" +
                        "    \"key\": \"qaclick123\"\n" +
                        "}")
                .when().put("/maps/api/place/update/json")
                .then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));

        //Get place
        String getPlace = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeID).header("Content-Type", "application/json")
                .body("")
                .when().get("/maps/api/place/get/json")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        JsonPath js1 = new JsonPath(getPlace);

        String getAddress = js1.getString("address");
        System.out.println("the Address is: "+getAddress);

        //Add place -> Update place with new address -> Get place to validate if new address is present in response





    }
}