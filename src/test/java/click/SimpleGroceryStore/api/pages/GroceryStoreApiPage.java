package click.SimpleGroceryStore.api.pages;

import com.thedeanda.lorem.LoremIpsum;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class GroceryStoreApiPage {
    private static final String BASE_URL = "https://simple-grocery-store-api.click";
    private final RequestSpecification publicApiSpec = new RequestSpecBuilder()
            .setBaseUri(BASE_URL)
            .setContentType(ContentType.JSON)
            .build();

    public Response getProducts() {
        return given()
                .spec(publicApiSpec)
                .log().uri()
                .when()
                .get("/products");
    }

    public int getFirstProductId() {
        return getProducts()
                .then()
                .statusCode(200)
                .extract().jsonPath().getInt("[0].id");
    }

    public Response getProduct(int productId) {
        return given()
                .spec(publicApiSpec)
                .log().uri()
                .when()
                .get("/products/" + productId);
    }

    public Response createCart() {
        return given()
                .spec(publicApiSpec)
                .log().uri()
                .when()
                .post("/carts");
    }

    public String createCartAndGetId() {
        return createCart()
                .then()
                .statusCode(201)
                .extract().jsonPath().getString("cartId");
    }

    public Response getCart(String cartId) {
        return given()
                .spec(publicApiSpec)
                .log().uri()
                .when()
                .get("/carts/" + cartId);
    }

    public Response addItemToCart(String cartId, int productId, int quantity) {
        Map<String, Integer> item = new HashMap<>();
        item.put("productId", productId);
        item.put("quantity", quantity);

        return given()
                .spec(publicApiSpec)
                .log().uri()
                .log().body()
                .body(item)
                .when()
                .post("/carts/" + cartId + "/items");
    }

    public Response registerApiClient(String clientName, String clientEmail) {
        Map<String, String> client = new HashMap<>();
        client.put("clientName", clientName);
        client.put("clientEmail", clientEmail);

        return given()
                .spec(publicApiSpec)
                .log().uri()
                .log().body()
                .body(client)
                .when()
                .post("/api-clients");
    }

    public Response createOrder(String cartId, String customerName, String comment) {
        Map<String, Object> order = new HashMap<>();
        order.put("cartId", cartId);
        order.put("customerName", customerName);
        order.put("comment", comment);

        return given()
                .spec(publicApiSpec)
                .header("Authorization", "Bearer " + getBearerToken())
                .log().uri()
                .body(order)
                .when()
                .post("/orders");
    }

    private String getBearerToken() {
        String clientName = LoremIpsum.getInstance().getName();
        String clientEmail = LoremIpsum.getInstance().getEmail();

        return registerApiClient(clientName, clientEmail)
                .then()
                .statusCode(201)
                .extract().jsonPath().getString("accessToken");
    }
}
