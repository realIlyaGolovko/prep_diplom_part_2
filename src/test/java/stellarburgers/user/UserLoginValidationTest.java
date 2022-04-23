package stellarburgers.user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static stellarburgers.common.ConstantsForTests.*;

public class UserLoginValidationTest {
    private static final User user =User.getRandomUser();
    private  UserCredentials userCredentials;
    private static final UserClient userClient=new UserClient();
    private static String token;
    @Before
    public  void UserCreating() {
        ValidatableResponse createResponse = userClient.create(user);
        token = userClient.getPath(createResponse, "accessToken");
    }
    @After
    public void TearDown(){
        userClient.deleteUser(user,token);
    }
    @Test
    @DisplayName("Проверка валдиации при авторизации под клиентом с неверным логином и паролем")
    public void UserCannotBeLoginWithInvalidParameters(){
        userCredentials=new UserCredentials(user.getEmail()+"q",user.getPassword()+"w");
        ValidatableResponse loginResponse=userClient.login(userCredentials);
        String actualMsgSuccess=userClient.getPath(loginResponse,"success");
        String actualErrorMsg=userClient.getPath(loginResponse,"message");
        //Asserts
        loginResponse.assertThat().statusCode(SC_UNAUTHORIZED);
        Assert.assertEquals("Incorrect creation message ",SUCCESS_MSG_FALSE,actualMsgSuccess);
        Assert.assertEquals("Incorrect message",LOGIN_USER_ERROR_MSG,actualErrorMsg);
    }

}
