package jsonServer.test;

import com.thedeanda.lorem.LoremIpsum;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;


public class JsonServerStructureApiTest extends BaseJsonServerStructureApiTest {

    @Test
    public void getPostShouldSucceed(){
        given()
                .spec(requestSpecification())
                .log().uri()
                .log().body()
                .when()
                .get("posts")
                .then()
                .statusCode(200)
                .log().body();
    }
    @Test
    public void createPostShouldSucceed(){
        given()
                .spec(requestSpecification())
                .when()
                .get("posts")
                        .then()
                                .statusCode(200);

        given()
                .spec(requestSpecification())
                .body(getPostJson(LoremIpsum.getInstance().getTitle(3),150))
                .log().uri()
                .log().body()
                .when()
                .post("posts")
                .then()
                .statusCode(201)
                .log().body();
    }
    @Test
    public void putPostShouldSucceed(){
        given()
                .spec(requestSpecification())
                .body(getPostJson(200))
                .log().uri()
                .log().body()
                .when()
                .put("posts/5bac")
                .then()
                .statusCode(200)
                .log().body();
    }

}
