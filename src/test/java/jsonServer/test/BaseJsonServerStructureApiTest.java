package jsonServer.test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

public class BaseJsonServerStructureApiTest {
    private static final String BASE_URL = "http://localhost";

    public RequestSpecification requestSpecification(){
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .setPort(3000)
                .build();
    }
    public Map<String, Object> getPostJson(String title, int views){
        Map<String, Object> postJson = new HashMap<>();
        postJson.put("title", title);
        postJson.put("views",views);
        return postJson;
    }
    //Previous Methods simplified version, and Overloading
    public Map<String, Object> getPostJson(String title){
        return Map.of(
                "title",title
        );
    }
    public Map<String, Object> getPostJson(int views){
        Map<String, Object> postJson = new HashMap<>();
        postJson.put("views",views);
        return postJson;
    }
    public String getPostId(){
        return given()
                .spec(requestSpecification())
                .log().uri()
                .when()
                .get("/posts/")
                .then()
                .statusCode(200)
                .extract().jsonPath().getString("[0].id");
    }
    public String getPostTitle(){
        Response response =  given()
                .spec(requestSpecification())
                .when()
                .get("/posts/"+getPostId());
        return response.jsonPath().getString("title");

    }

    public int getPostViews(){
        Response response = given()
                .spec(requestSpecification())
                .when()
                .get("/posts/"+getPostId());
        JsonPath jsonPath = response.jsonPath();
        return jsonPath.getInt("views");
    }

}
