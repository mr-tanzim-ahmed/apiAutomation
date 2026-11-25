package click.SimpleGroceryStore.api;

import com.thedeanda.lorem.LoremIpsum;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class GroceryStoreApiTest extends BaseGroceryStoreApiTest {

    @Test
    public void getProductsShouldSucceed() {
        groceryStoreApi.getProducts()
                .then()
                .statusCode(200);
    }

    @Test
    public void getProductDetailShouldSucceed() {
        int productId = groceryStoreApi.getFirstProductId();

        groceryStoreApi.getProduct(productId)
                .then()
                .statusCode(200)
                .log().body()
                .body("id",equalTo(productId));
    }

    @Test
    public void getProductDetailShouldFail() {
        int productId = 123213;
        groceryStoreApi.getProduct(productId)
                .then()
                .statusCode(404)
                .body("error",equalTo("No product with id 123213."))
                .log().body();
    }

    @Test
    public void createCartShouldSucceed() {
        groceryStoreApi.createCart()
                .then()
                .statusCode(201)
                .body("created",equalTo(true))
                .log().body();
    }

    @Test
    public void getCartShouldSucceed() {
        String cartId = groceryStoreApi.createCartAndGetId();
        System.out.println("CartID:  "+cartId);
        groceryStoreApi.getCart(cartId)
                .then()
                .statusCode(200)
                .log().body();
    }

    @Test
    public void addItemToCartShouldSucceed() {
        int quantity = 2;
        String cartId = groceryStoreApi.createCartAndGetId();
        int productId = groceryStoreApi.getFirstProductId();
        System.out.println("ProductID:  "+productId);
        groceryStoreApi.addItemToCart(cartId, productId, quantity)
                .then()
                .log().body()
                .statusCode(201)
                .body("created", equalTo(true),
                        "itemId", notNullValue());
    }

    @Test
    public void createOrderShouldSucceed() {
        String cartId = groceryStoreApi.createCartAndGetId();

        int quantity = 2;
        groceryStoreApi.addItemToCart(cartId, groceryStoreApi.getFirstProductId(), quantity)
                .then()
                .log().body()
                .statusCode(201);

        String customerName = LoremIpsum.getInstance().getName();
        String comment = LoremIpsum.getInstance().getTitle(5);

        groceryStoreApi.createOrder(cartId, customerName, comment)
                .then()
                .statusCode(201)
                .log().body()
                .body("created",equalTo(true),
                        "orderId", notNullValue());
    }

    @Test
    public void registerNewApiClientShouldSucceed() {
        String name = LoremIpsum.getInstance().getName();
        String email = LoremIpsum.getInstance().getEmail();

        groceryStoreApi.registerApiClient(name, email)
                .then()
                .statusCode(201)
                .body("accessToken", notNullValue())
                .log().body();
    }
}
