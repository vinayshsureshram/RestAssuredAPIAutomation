package tests;

import endpoints.UserEndpoint;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import payloads.User;
import utils.DataProviders;

public class DDTests {


    @Test(priority = 1, dataProvider = "Data", dataProviderClass = DataProviders.class)
    public void testPostuser(String userID, String userName, String fname, String lname, String useremail, String pwd, String ph) {
        User userPayload = new User();

        userPayload.setId(Integer.parseInt(userID));
        userPayload.setUsername(userName);
        userPayload.setFirstName(fname);
        userPayload.setLastName(lname);
        userPayload.setEmail(useremail);
        userPayload.setPassword(pwd);
        userPayload.setPhone(ph);

        Response response = UserEndpoint.createUser(userPayload);
        Assert.assertEquals(response.getStatusCode(), 200);

    }

    @Test(priority = 2, dataProvider = "UserNames", dataProviderClass = DataProviders.class)
    public void testDeleteUserByName(String userName) {
        Response response = UserEndpoint.deleteUser(userName);
        Assert.assertEquals(response.getStatusCode(), 200);

    }


}
