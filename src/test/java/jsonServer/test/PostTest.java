package jsonServer.test;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class PostTest {
    @Test
    public void localGetPostsShouldSucceed(){
        given()
                .log().uri()
                .baseUri("http://localhost:3000")
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .log().body();
    }
    @Test
    public void reqresGetPostsShouldSucceed(){

        given()
                .log().uri()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(401)
                .log().body();
    }

}
