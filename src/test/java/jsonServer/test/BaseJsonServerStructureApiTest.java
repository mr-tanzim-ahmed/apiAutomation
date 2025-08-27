package jsonServer.test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

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
}
