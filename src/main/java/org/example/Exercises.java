package org.example;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import jdk.security.jarsigner.JarSigner;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;

public class Exercises {

    @BeforeClass
    public static void setup(){
        RestAssured.baseURI = "https://automationexercise.com";
    }

    @Test
    public static void API_Exercise_01(){

        try{

        given().body("")
                .when().get("/api/productsList")
                .then().log().all().assertThat().statusCode(200);

        }catch (Exception e){
            String method = new Object() {}.getClass().getEnclosingMethod().getName();
            System.out.println("There was an error in the TEST "+method);
        }

    }

    @Test
    public static void API_Exercise_02(){

        try{

            String response = given().body("")
                    .when().post("/api/productsList")
                    .then().log().all().assertThat().statusCode(200).extract().response().asString();

            JsonPath js = new JsonPath(response);

            String responseCode = js.getString("responseCode");
            String responseMessage = js.getString("message");
            Assert.assertEquals(responseCode, "405");
            Assert.assertEquals(responseMessage, "This request method is not supported.");

        }catch (Exception e){
            String method = new Object() {}.getClass().getEnclosingMethod().getName();
            System.out.println("There was an error in the TEST "+method);
        }

    }

    @Test
    public static void API_Exercise_03(){

        String expectedResponse = "{\"responseCode\": 200, \"brands\": [{\"id\": 1, \"brand\": \"Polo\"}, {\"id\": 2, \"brand\": \"H&M\"}, {\"id\": 3, \"brand\": \"Madame\"}, {\"id\": 4, \"brand\": \"Madame\"}, {\"id\": 5, \"brand\": \"Mast & Harbour\"}, {\"id\": 6, \"brand\": \"H&M\"}, {\"id\": 7, \"brand\": \"Madame\"}, {\"id\": 8, \"brand\": \"Polo\"}, {\"id\": 11, \"brand\": \"Babyhug\"}, {\"id\": 12, \"brand\": \"Babyhug\"}, {\"id\": 13, \"brand\": \"Allen Solly Junior\"}, {\"id\": 14, \"brand\": \"Kookie Kids\"}, {\"id\": 15, \"brand\": \"Babyhug\"}, {\"id\": 16, \"brand\": \"Babyhug\"}, {\"id\": 18, \"brand\": \"Kookie Kids\"}, {\"id\": 19, \"brand\": \"Allen Solly Junior\"}, {\"id\": 20, \"brand\": \"Kookie Kids\"}, {\"id\": 21, \"brand\": \"Biba\"}, {\"id\": 22, \"brand\": \"Biba\"}, {\"id\": 23, \"brand\": \"Biba\"}, {\"id\": 24, \"brand\": \"Allen Solly Junior\"}, {\"id\": 28, \"brand\": \"H&M\"}, {\"id\": 29, \"brand\": \"Polo\"}, {\"id\": 30, \"brand\": \"Polo\"}, {\"id\": 31, \"brand\": \"H&M\"}, {\"id\": 33, \"brand\": \"Polo\"}, {\"id\": 35, \"brand\": \"H&M\"}, {\"id\": 37, \"brand\": \"Polo\"}, {\"id\": 38, \"brand\": \"Madame\"}, {\"id\": 39, \"brand\": \"Biba\"}, {\"id\": 40, \"brand\": \"Biba\"}, {\"id\": 41, \"brand\": \"Madame\"}, {\"id\": 42, \"brand\": \"Mast & Harbour\"}, {\"id\": 43, \"brand\": \"Mast & Harbour\"}]}";

        try{

           Response response = given().body("")
                    .when().get("/api/brandsList");

           Assert.assertEquals(response.asString(), expectedResponse);

           JsonPath js = new JsonPath(response.asString());
           Assert.assertEquals(js.getString("responseCode"), "200");


        }catch (Exception e){
            String method = new Object() {}.getClass().getEnclosingMethod().getName();
            System.out.println("There was an error in the TEST "+method);
        }

    }

    @Test
    public static void API_Exercise_04(){

        try {
            String response = given().when()
                    .put("/api/brandsList")
                    .then().log().all().assertThat().statusCode(200).extract().response().asString();

            JsonPath js = new JsonPath(response);
            String responseMessage = js.getString("message");

            Assert.assertEquals(responseMessage, "This request method is not supported.");

        }catch (Exception e){
            String method = new Object() {}.getClass().getEnclosingMethod().getName();
            System.out.println("There was an error in the TEST "+method);
        }

    }

}
