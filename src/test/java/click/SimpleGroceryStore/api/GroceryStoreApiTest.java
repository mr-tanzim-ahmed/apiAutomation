package click.SimpleGroceryStore.api;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GroceryStoreApiTest extends BaseGroceryStoreApiTest{

    @Test
    public void getProductsShouldSucceed(){
        given()
                .spec(requestSpecification())
                .log().uri()
                .when()
                .get("/products")
                .then()
                .statusCode(200);
    }
    @Test public void getProductDetailShouldSucceed(){
        Long productId = given()
                .spec(requestSpecification())
                .log().uri()
                .when()
                .get("/products/")
                .then()
                .statusCode(200)
                .log().body()
                .extract().jsonPath().getLong("[0].id");

        given()
                .spec(requestSpecification())
                .log().uri()
                .when()
                .get("/products/"+productId)
                .then()
                .statusCode(200)
                .log().body()
                .body("id",equalTo(productId));
    }
    @Test
    public void getProductDetailShouldFail(){
        long productId = 123213;
        given()
                .spec(requestSpecification())
                .log().uri()
                .when()
                .get("/products/"+productId)
                .then()
                .statusCode(404)
                .body("error",equalTo("No product with id 123213."))
                .log().body();
    }

}
