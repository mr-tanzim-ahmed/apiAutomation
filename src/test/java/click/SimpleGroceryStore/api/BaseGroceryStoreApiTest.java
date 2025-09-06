package click.SimpleGroceryStore.api;

import groovyjarjarantlr4.v4.runtime.atn.PredicateTransition;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BaseGroceryStoreApiTest {
    private static final String BASE_URL = "https://simple-grocery-store-api.click";
    public RequestSpecification requestSpecification(){
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .build();
    }
    public String getProductId(){
        return given()
                .spec(requestSpecification())
                .when()
                .get("")
                .then()
                .extract().jsonPath().getString("x[0].id");

    }
}
