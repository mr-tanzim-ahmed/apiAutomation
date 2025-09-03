package jsonServer.test;

import com.thedeanda.lorem.LoremIpsum;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Random;

import static io.restassured.RestAssured.*;
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
    public void detailPostShouldSucceed(){
        Response response = given()
                .spec(requestSpecification())
                .when()
                .get("/posts/"+getPostId());
        JsonPath jsonPath = response.jsonPath();
        String title = jsonPath.getString("title");
        System.out.println(title);
        int views = jsonPath.getInt("views");

        given()
                .spec(requestSpecification())
                .log().uri()
                .log().body()
                .when()
                .get("/posts/"+getPostId())
                .then()
                .body("title",equalTo(title))
                .body("views",equalTo(views))
                .body("id",notNullValue())
                .statusCode(200)
                .log().body();
    }
    @Test
    public void detailPostWithInvalidIdShouldFail(){
        given()
                .spec(requestSpecification())
                .log().uri()
                .log().body()
                .when()
                .get("/posts/df321ds")
                .then()
                .statusCode(404)
                .log().body();
    }

    @Test
    public void createPostShouldSucceed(){
        String title = LoremIpsum.getInstance().getTitle(3);
        int views = new Random().nextInt();
        given()
                .spec(requestSpecification())
                .body(getPostJson(title,views))
                .log().uri()
                .log().body()
                .when()
                .post("/posts")
                .then()
                .body("title",equalTo(title))
                .body("views",equalTo(views))
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
    int views = new Random().nextInt();
        given()
                .spec(requestSpecification())
                .body(getPostJson(views))
                .log().uri()
                .log().body()
                .when()
                .patch("/posts/"+getPostId())
                .then()
                .statusCode(200)
                .body("views",equalTo(views))
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
    @Test
    public void deletePostShouldFail(){
        given()
                .spec(requestSpecification())
                .log().uri()
                .log().body()
                .when()
                .delete("/posts/df24312")
                .then()
                .statusCode(404)
                .log().body();
    }

}
