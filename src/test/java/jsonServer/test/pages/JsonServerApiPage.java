package jsonServer.test.pages;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class JsonServerApiPage {
    private final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(3000)
            .setContentType(ContentType.JSON)
            .build();

    public Response getPosts() {
        return given()
                .spec(requestSpec)
                .log().uri()
                .when()
                .get("/posts");
    }

    public String getFirstPostId() {
        return getPosts()
                .then()
                .statusCode(200)
                .extract().jsonPath().getString("[0].id");
    }

    public Response getPost(String postId) {
        return given()
                .spec(requestSpec)
                .log().uri()
                .when()
                .get("/posts/" + postId);
    }

    public Response createPost(String title, int views) {
        Map<String, Object> post = new HashMap<>();
        post.put("title", title);
        post.put("views", views);

        return given()
                .spec(requestSpec)
                .body(post)
                .log().uri()
                .log().body()
                .when()
                .post("/posts");
    }

    public Response replacePost(String postId, String title, int views) {
        Map<String, Object> post = new HashMap<>();
        post.put("title", title);
        post.put("views", views);

        return given()
                .spec(requestSpec)
                .body(post)
                .log().uri()
                .log().body()
                .when()
                .put("/posts/" + postId);
    }

    public Response updatePostViews(String postId, int views) {
        Map<String, Integer> post = new HashMap<>();
        post.put("views", views);

        return given()
                .spec(requestSpec)
                .body(post)
                .log().uri()
                .log().body()
                .when()
                .patch("/posts/" + postId);
    }

    public Response deletePost(String postId) {
        return given()
                .spec(requestSpec)
                .log().uri()
                .when()
                .delete("/posts/" + postId);
    }
}
