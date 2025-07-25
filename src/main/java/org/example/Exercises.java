package org.example;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import jdk.security.jarsigner.JarSigner;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

/**
 * These exercises were extracted from:
 * </br>
 * <a href="https://www.automationexercise.com/api_list">...</a>
 */
public class Exercises {

    @BeforeClass
    public static void setup(){
        RestAssured.baseURI = "https://automationexercise.com";
    }

    @Test
    public static void API_Exercise_01(){

        String endpoint =  "/api/productsList";

        try{

        given().body("")
                .when().get(endpoint)
                .then().log().all().assertThat().statusCode(200);

        }catch (Exception e){
            String method = new Object() {}.getClass().getEnclosingMethod().getName();
            System.out.println("There was an error in the TEST "+method);
        }

    }

    @Test
    public static void API_Exercise_02(){

        String endpoint = "/api/productsList";

        try{

            String response = given().body("")
                    .when().post(endpoint)
                    .then().log().all().assertThat().statusCode(200).extract().response().asString();

            JsonPath js = new JsonPath(response);

            String responseCode = js.getString("responseCode");
            String responseMessage = js.getString("message");

            Assert.assertEquals(responseCode, "405", "El codigo NO corresponde con lo esperado");
            Assert.assertEquals(responseMessage, "This request method is not supported.", "El mensaje NO corresponde con lo esperado");

        }catch (Exception e){
            String method = new Object() {}.getClass().getEnclosingMethod().getName();
            System.out.println("There was an error in the TEST "+method);
        }

    }

    @Test
    public static void API_Exercise_03(){

        String expectedResponse = "{\"responseCode\": 200, \"brands\": [{\"id\": 1, \"brand\": \"Polo\"}, {\"id\": 2, \"brand\": \"H&M\"}, {\"id\": 3, \"brand\": \"Madame\"}, {\"id\": 4, \"brand\": \"Madame\"}, {\"id\": 5, \"brand\": \"Mast & Harbour\"}, {\"id\": 6, \"brand\": \"H&M\"}, {\"id\": 7, \"brand\": \"Madame\"}, {\"id\": 8, \"brand\": \"Polo\"}, {\"id\": 11, \"brand\": \"Babyhug\"}, {\"id\": 12, \"brand\": \"Babyhug\"}, {\"id\": 13, \"brand\": \"Allen Solly Junior\"}, {\"id\": 14, \"brand\": \"Kookie Kids\"}, {\"id\": 15, \"brand\": \"Babyhug\"}, {\"id\": 16, \"brand\": \"Babyhug\"}, {\"id\": 18, \"brand\": \"Kookie Kids\"}, {\"id\": 19, \"brand\": \"Allen Solly Junior\"}, {\"id\": 20, \"brand\": \"Kookie Kids\"}, {\"id\": 21, \"brand\": \"Biba\"}, {\"id\": 22, \"brand\": \"Biba\"}, {\"id\": 23, \"brand\": \"Biba\"}, {\"id\": 24, \"brand\": \"Allen Solly Junior\"}, {\"id\": 28, \"brand\": \"H&M\"}, {\"id\": 29, \"brand\": \"Polo\"}, {\"id\": 30, \"brand\": \"Polo\"}, {\"id\": 31, \"brand\": \"H&M\"}, {\"id\": 33, \"brand\": \"Polo\"}, {\"id\": 35, \"brand\": \"H&M\"}, {\"id\": 37, \"brand\": \"Polo\"}, {\"id\": 38, \"brand\": \"Madame\"}, {\"id\": 39, \"brand\": \"Biba\"}, {\"id\": 40, \"brand\": \"Biba\"}, {\"id\": 41, \"brand\": \"Madame\"}, {\"id\": 42, \"brand\": \"Mast & Harbour\"}, {\"id\": 43, \"brand\": \"Mast & Harbour\"}]}";
        String endpoint = "/api/brandsList";

        try{

           Response response = given().body("")
                    .when().get(endpoint);

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

        String endpoint = "/api/brandsList";

        try {
            String response = given().when()
                    .put(endpoint)
                    .then().log().all().assertThat().statusCode(200).extract().response().asString();

            JsonPath js = new JsonPath(response);
            String responseMessage = js.getString("message");

            Assert.assertEquals(responseMessage, "This request method is not supported.");

        }catch (Exception e){
            String method = new Object() {}.getClass().getEnclosingMethod().getName();
            System.out.println("There was an error in the TEST "+method);
        }

    }

    @Test
    public static void API_Exercise_05(){

        String expectedCategory = "jeans";
        String endpoint = "/api/searchProduct";

        try {

            String response = given()
                    .contentType("application/x-www-form-urlencoded")
                    .formParam("search_product", expectedCategory)
                    .when().post(endpoint)
                    .then().log().all().assertThat().statusCode(200).extract().asString();

            JsonPath js = new JsonPath(response);
            String category = js.getString("products.category.category");


            System.out.println("Categoria extraida: "+category);
            category = category.replaceAll("[\\[\\]]", "");
            String[] categoriesSplit = category.toLowerCase().split(",");
            String categoryComparator;

            for(int i = 0; i<categoriesSplit.length; i++){

                    categoryComparator = categoriesSplit[i].trim();
                    Assert.assertEquals(categoryComparator, expectedCategory, "La categoria NO COINCIDE");

            }



        }catch (Exception e){
            String method = new Object() {}.getClass().getEnclosingMethod().getName();
            System.out.println("There was an error in the TEST "+method);
        }

    }

    @Test
    public static void API_Exercise_06(){

        String endpoint = "/api/searchProduct";

        try {

            String response = given()
                    .when().post(endpoint)
                    .then().log().all().assertThat().statusCode(200).extract().response().asString();

            JsonPath js = new JsonPath(response);

            Assert.assertEquals(js.getString("responseCode"), "400");
            Assert.assertEquals(js.getString("message"), "Bad request, search_product parameter is missing in POST request.");


        }catch (Exception e){
            String method = new Object() {}.getClass().getEnclosingMethod().getName();
            System.out.println("There was an error in the TEST "+method);
        }

    }

    /**
     * This test canno't be tested properly because I haven't registered into the website and I wont
     * </br>
     *
     * So I just left the logic of how it is supposed to be tested.
     */
    @Test
    public static void API_Exercise_07(){

        String endpoint = "/api/verifyLogin";
        String email = "email@email.com";
        String password = "password";

        try {

            String response = given()
                    .contentType("application/x-www-form-urlencoded")
                    .formParam("email", email)
                    .formParam("password", password)
                    .when().post(endpoint)
                    .then().log().all().assertThat().statusCode(200).extract().response().asString();

            JsonPath js = new JsonPath(response);
            String responseCode = js.getString("responseCode");
            String message = js.getString("message");


            Assert.assertEquals(responseCode, "200", "el codigo NO Coincide");
            Assert.assertEquals(message,"User exists!", "el mensaje NO Coincide");

        }catch (Exception e){
            String method = new Object() {}.getClass().getEnclosingMethod().getName();
            System.out.println("There was an error in the TEST "+method);
        }

    }

    @Test
    public static void API_Exercise_08(){

        String endpoint = "/api/verifyLogin";
        String password = "password";

        try {

            String response = given()
                    .contentType("application/x-www-form-urlencoded")
                    .formParam("password", password)
                    .when().post(endpoint)
                    .then().log().all().extract().response().asString();

            JsonPath js = new JsonPath(response);

            String responseCode = js.getString("responseCode");
            String message = js.getString("message");

            Assert.assertEquals(responseCode, "400", "el response code NO Corresponde al esperado");
            Assert.assertEquals(message, "Bad request, email or password parameter is missing in POST request.", "el response code NO Corresponde al esperado");

        }catch (Exception e){
            String method = new Object() {}.getClass().getEnclosingMethod().getName();
            System.out.println("There was an error in the TEST "+method);
        }

    }

    @Test
    public static void API_Exercise_09(){

        String endpoint = "/api/verifyLogin";

        try {

            String response = given()
                    .when().delete(endpoint)
                    .then().log().all().extract().response().asString();

            JsonPath js = new JsonPath(response);

            String responseCode = js.getString("responseCode");
            String message = js.getString("message");

            Assert.assertEquals(responseCode, "405", "el responsecode NO Correspone al esperado");
            Assert.assertEquals(message, "This request method is not supported.", "el mensaje NO Correspone al esperado");


        }catch (Exception e){
            String method = new Object() {}.getClass().getEnclosingMethod().getName();
            System.out.println("There was an error in the TEST "+method);
        }

    }


}
