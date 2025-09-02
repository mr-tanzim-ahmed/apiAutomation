package jsonServer.test;

import com.thedeanda.lorem.LoremIpsum;
import org.testng.annotations.Test;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


public class JsonServerStructureApiTest extends BaseJsonServerStructureApiTest {

    @Test
    public void getPostShouldSucceed(){
        given()
                .spec(requestSpecification())
                .log().uri()
                .log().body()
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .log().body();
    }
    @Test
    public void createPostShouldSucceed(){
        given()
                .spec(requestSpecification())
                .body(getPostJson(LoremIpsum.getInstance().getTitle(3),150))
                .log().uri()
                .log().body()
                .when()
                .post("/posts")
                .then()
                .statusCode(201)
                .log().body();
    }
    @Test
    public void putPostShouldSucceed(){
        String title = LoremIpsum.getInstance().getTitle(3);
        int views = new Random().nextInt();

        given()
                .spec(requestSpecification())
                .body(getPostJson(title,views))
                .log().uri()
                .log().body()
                .when()
                .put("/posts/"+getPostId())
                .then()
                .statusCode(200)
                .body("title",equalTo(title))
                .body("views",equalTo(views))
                .body("id", notNullValue())
                .log().body();
    }
    @Test
    public void patchPostShouldSucceed(){

        given()
                .spec(requestSpecification())
                .body(getPostJson(400))
                .log().uri()
                .log().body()
                .when()
                .patch("/posts/"+getPostId())
                .then()
                .statusCode(200)
                .body("views",equalTo((Integer)400))
                .log().body();
    }
    @Test
    public void deletePostsShouldSucceed(){
        given()
                .spec(requestSpecification())
                .log().uri()
                .log().body()
                .when()
                .delete("/posts/"+getPostId())
                .then()
                .statusCode(200)
                .log().body();
    }

}
