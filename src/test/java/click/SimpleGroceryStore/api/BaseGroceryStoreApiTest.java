package click.SimpleGroceryStore.api;

import com.thedeanda.lorem.LoremIpsum;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class BaseGroceryStoreApiTest {
    private static final String BASE_URL = "https://simple-grocery-store-api.click";
    public RequestSpecification requestSpecForPublicApi(){
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .build();
    }
    public RequestSpecification requestSpecForPrivateApi(){
        return new RequestSpecBuilder()
                .addRequestSpecification(requestSpecForPublicApi())
                .addHeader("Authorization","Bearer "+getBearerToken())
                .build();
    }
    public int getProductId(){
        return given()
                .spec(requestSpecForPublicApi())
                .log().uri()
                .when()
                .get("/products")
                .then()
                .statusCode(200)
                .log().body()
                .extract().jsonPath().getInt("[0].id");
    }
    public String getCartId(){
        return given()
                .spec(requestSpecForPublicApi())
                .log().uri()
                .when()
                .post("/carts")
                .then()
                .statusCode(201)
                .log().body()
                .extract().jsonPath().getString("cartId");
    }

    public Map<String, Integer> getProductJson(int productId, int quantity){
        Map<String, Integer> productJson = new HashMap<>();
        productJson.put("productId", productId);
        productJson.put("quantity",quantity);
        return productJson;
    }
    //Register a new api client,  that return a bearer token
    public Map<String, String> getApiClientsJson(String clientName, String description){
        return Map.of(
            "clientName", clientName,
                "clientEmail",description
        );
    }
    public Map<String, Object> getNewOrderJson(String cartId,String customerName,String comment){
        Map<String, Object> orderJson = new HashMap<>();
        orderJson.put("cartId",cartId);
        orderJson.put("customerName", customerName);
        orderJson.put("comment",comment);
        return orderJson;
    }
    public String getBearerToken(){

        Map<String,String> clientJson = new HashMap<>();
        clientJson.put("clientName", LoremIpsum.getInstance().getName());
        clientJson.put("clientEmail",LoremIpsum.getInstance().getEmail());

        return given()
                .spec(requestSpecForPublicApi())
                .body(clientJson)
                .log().uri()
                .log().body()
                .when()
                .post("/api-clients")
                .then()
                .statusCode(201)
                .extract().jsonPath().getString("accessToken");
    }

}
