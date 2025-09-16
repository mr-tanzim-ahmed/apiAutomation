package click.SimpleGroceryStore.api;

import com.thedeanda.lorem.LoremIpsum;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.Objects;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GroceryStoreApiTest extends BaseGroceryStoreApiTest{

    @Test
    public void getProductsShouldSucceed(){
        given()
                .spec(requestSpecForPublicApi())
                .log().uri()
                .when()
                .get("/products")
                .then()
                .statusCode(200);
    }
    @Test public void getProductDetailShouldSucceed(){
        int productId = getProductId();

        given()
                .spec(requestSpecForPublicApi())
                .log().uri()
                .when()
                .get("/products/"+productId)
                .then()
                .statusCode(200)
                .log().body()
                .body("id",equalTo(productId));
    }
    @Test
    public void getProductDetailShouldFail(){
        long productId = 123213; //Random
        given()
                .spec(requestSpecForPublicApi())
                .log().uri()
                .when()
                .get("/products/"+productId)
                .then()
                .statusCode(404)
                .body("error",equalTo("No product with id 123213."))
                .log().body();
    }
    @Test public void createCartShouldSucceed(){
        given()
                .spec(requestSpecForPublicApi())
                .log().uri()
                .when()
                .post("/carts")
                .then()
                .statusCode(201)
                .body("created",equalTo(true))
                .log().body();
    }
    @Test
    public void getCartShouldSucceed(){
        String cartId = getCartId();
        System.out.println("CartID:  "+cartId);
        given()
                .spec(requestSpecForPublicApi())
                .log().uri()
                .when()
                .get("/carts/"+cartId)
                .then()
                .statusCode(200)
                .log().body();
    }

    @Test
    public void addItemToCartShouldSucceed(){
        int quantity = 2;
        String cartId = getCartId();
        int productId = getProductId();
        System.out.println("ProductID:  "+productId);
        Map<String, Integer> productJson = getProductJson(productId, quantity);
        given()
                .spec(requestSpecForPublicApi())
                .log().uri()
                .log().body()
                .body(productJson)
                .when()
                .post("/carts/"+cartId+"/items")
                .then()
                .log().body()
                .statusCode(201)
                .body("created", equalTo(true),
                        "itemId", notNullValue());
    }


    @Test
    public void createOrderShouldSucceed(){
        String cartId = getCartId();

        //Create product list>> Add to cart >> then order
        int quantity = 2;
        given()
                .spec(requestSpecForPublicApi())
                .log().uri()
                .log().body()
                .body(getProductJson(getProductId(),quantity))
                .when()
                .post("/carts/"+cartId+"/items")
                .then()
                .log().body()
                .statusCode(201);

        String customerName = LoremIpsum.getInstance().getName();
        String comment = LoremIpsum.getInstance().getTitle(5);
        Map<String, Object> orderJson = getNewOrderJson(cartId,customerName,comment);

        given()
                .spec(requestSpecForPrivateApi())
                .body(orderJson)
                .when()
                .post("/orders")
                .then()
                .statusCode(201)
                .log().body()
                .body("created",equalTo(true),
                        "orderId", notNullValue()
                );
    }

    @Test
    public void registerNewApiClientShouldSucceed(){
        String name = LoremIpsum.getInstance().getName();
        String email = LoremIpsum.getInstance().getEmail();
        //Receiving registered bearer token
        Map<String, String>clientJson = getApiClientsJson(name,email);

        given()
                .spec(requestSpecForPublicApi())
                .log().uri()
                .log().body()
                .body(clientJson)
                .when()
                .post("/api-clients")
                .then()
                .statusCode(201)
                .body("accessToken", notNullValue())
                .log().body();

    }


}
