package jsonServer.test;

import com.thedeanda.lorem.LoremIpsum;
import org.testng.annotations.Test;

import java.util.Random;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class JsonServerStructureApiTest extends BaseJsonServerStructureApiTest {

    @Test
    public void getPostShouldSucceed() {
        jsonServerApi.getPosts()
                .then()
                .statusCode(200)
                .log().body();
    }

    @Test
    public void detailPostShouldSucceed() {
        String postId = jsonServerApi.getFirstPostId();
        jsonServerApi.getPost(postId)
                .then()
                .body("title", notNullValue())
                .body("views", notNullValue())
                .body("id", notNullValue())
                .statusCode(200)
                .log().body();
    }

    @Test
    public void detailPostWithInvalidIdShouldFail() {
        String id = "df321ds";
        jsonServerApi.getPost(id)
                .then()
                .statusCode(404)
                .log().body();
    }

    @Test
    public void createPostShouldSucceed() {
        String title = LoremIpsum.getInstance().getTitle(3);
        int views = new Random().nextInt();
        jsonServerApi.createPost(title, views)
                .then()
                .body("title", equalTo(title))
                .body("views", equalTo(views))
                .statusCode(201)
                .log().body();
    }

    @Test
    public void putPostShouldSucceed() {
        String title = LoremIpsum.getInstance().getTitle(3);
        int views = new Random().nextInt();

        String postId = jsonServerApi.getFirstPostId();
        jsonServerApi.replacePost(postId, title, views)
                .then()
                .statusCode(200)
                .body("title", equalTo(title))
                .body("views", equalTo(views))
                .body("id", notNullValue())
                .log().body();
    }

    @Test
    public void patchPostShouldSucceed() {
        int views = new Random().nextInt();
        String postId = jsonServerApi.getFirstPostId();
        jsonServerApi.updatePostViews(postId, views)
                .then()
                .statusCode(200)
                .body("views", equalTo(views))
                .log().body();
    }

    @Test
    public void deletePostsShouldSucceed() {
        String postId = jsonServerApi.getFirstPostId();
        jsonServerApi.deletePost(postId)
                .then()
                .statusCode(200)
                .body("id", equalTo(postId))
                .log().body();
    }

    @Test
    public void deletePostShouldFail() {
        jsonServerApi.deletePost("df24312")
                .then()
                .statusCode(404)
                .log().body();
    }
}
