import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class PostTest {
    @Test
    public void getPostsShouldSucceed(){
        given()
                .log().uri()
                .baseUri("http://localhost:3000")
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .log().body();
    }
}
