package endpoints;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import payloads.User;

public class UserEndpoint extends BaseEndpoint {
    private static final String CREATE_USER_URL = "/user";
    private static final String GET_USER_URL = "/user/{username}";

    // Create user
    public static Response createUser(User payload) {
        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
                .when()
                .post(BASE_URI + CREATE_USER_URL);
        return response;
    }

    //Read User
    public static Response readUser(String username) {
        Response response = given()
                .pathParam("username", username)
                .when()
                .get(BASE_URI + GET_USER_URL);
        return response;
    }

    //Update user
    public static Response updateUser(String username, User payload) {
        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .pathParam("username", username)
                .body(payload)
                .when()
                .put(BASE_URI + GET_USER_URL);
        return response;
    }

    //Read User
    public static Response deleteUser(String username) {
        Response response = given()
                .pathParam("username", username)
                .when()
                .delete(BASE_URI + GET_USER_URL);
        return response;
    }

}
