package tests;

import com.github.javafaker.Faker;
import endpoints.UserEndpoint;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import payloads.User;

public class UserTests {

    Faker faker;
    User userPayload;

    public Logger logger;

    @BeforeClass
    public void setUp() {
        faker = new Faker();
        userPayload = new User();
        userPayload.setId(faker.idNumber().hashCode());
        userPayload.setUsername(faker.name().username());
        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setEmail(faker.internet().safeEmailAddress());
        userPayload.setPassword(faker.internet().password(5,10));
        userPayload.setPhone(faker.phoneNumber().cellPhone());

        //logs
        logger = LogManager.getLogger(this.getClass());

        logger.debug("debugging...");
    }

    @Test (priority = 1)
    public void testPostUser() {
        logger.info("******* Creating user *******");

        Response response = UserEndpoint.createUser(userPayload);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);

        logger.info("******* User is created *******");
    }

    @Test(priority = 2)
    public void
    testGetUserByName() {

        logger.info("******* Reading user info *******");

    Response response = UserEndpoint.readUser(this.userPayload.getUsername());
    response.then().log().all();

    Assert.assertEquals(response.getStatusCode(), 200);

        logger.info("******* User info is displayed *******");
    }

    @Test(priority = 3)
    public void testUpdateUserByName() {
        //update data
        logger.info("******* Updating user *******");

        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setEmail(faker.internet().safeEmailAddress());

        Response response = UserEndpoint.updateUser(this.userPayload.getUsername(), userPayload);
        response.then().log().body();

        Assert.assertEquals(response.getStatusCode(), 200);

        //Checking data

        Response responseAfterUpdate = UserEndpoint.readUser(this.userPayload.getUsername());
        Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);

        logger.info("******* User is updated *******");

    }

    @Test(priority = 4)
    public void testDeleteUserByName() {

        logger.info("******* Deleting user *******");

        Response response = UserEndpoint.deleteUser(this.userPayload.getUsername());
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);

        logger.info("******* User deleted *******");

    }
}
