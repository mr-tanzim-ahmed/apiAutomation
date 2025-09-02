package jsonServer.test;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class JsonServerTest {

    @Test
    public void getPostTest(){
        given()
                .log().uri() //Print req type
                .log().body() //Print body
                .when()
                .get("http://localhost:3000/posts")
                .then()
                .statusCode(200)
                .log().body();  //print response
    }
    @Test
    public void createPostTest(){
        String json = "{\n" +
                "    \"title\": \"update\",\n" +
                "    \"views\": 200,\n" +
                "    \"id\": \"2\"\n" +
                "  }";
        given()
                .header("Content-Type", "application/json")
                .body(json)
                .log().uri()
                .log().body()
                .when()
                .post("http://localhost:3000/posts")
                .then()
                .statusCode(201 )
                .log().body();
    }
    @Test
    public void putPostTest(){
        String jsonBody =  "{\n" +
                "    \"title\": \"update\",\n" +
                "    \"views\": 200\n" +
                "  }";
        given()
                .header("Content-Type","application/json")
                .body(jsonBody)
                .log().uri()
                .log().body()
                .when()
                .put("http://localhost:3000/posts/2")
                .then()
                .statusCode(200)
                .log().body();
    }
    @Test
    public void patchPostShouldSucceed(){
        String json =  "{\n" +
                "    \"title\": \"update\"\n"+
                "  }";
        given()
                .header("Content-Type","application/json")
                .body(json)
                .log().uri()
                .log().body()
                .when()
                .patch("http://localhost:3000/posts/2")
                .then()
                .statusCode(200)
                .log().body();
    }
    @Test
    public void deletePostShouldSucceed(){
        given()
                .log().uri()
                .log().body()
                .when()
                .delete("http://localhost:3000/posts/2")
                .then()
                .statusCode(200)
                .log().body();
    }

}
