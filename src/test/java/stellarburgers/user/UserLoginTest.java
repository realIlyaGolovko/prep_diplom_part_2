package stellarburgers.user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static stellarburgers.common.ConstantsForTests.SUCCESS_MSG_TRUE;

public class UserLoginTest {
private static final User uniqueUser=User.getRandomUser();
 private static final UserCredentials uniqueUserCredentials=UserCredentials.from(uniqueUser);
 private static final UserClient userClient=new UserClient();
 private static String token;
 @Before
 public  void UserCreating() {
     ValidatableResponse createResponse = userClient.create(uniqueUser);
     token = userClient.getPath(createResponse, "accessToken");
 }
@After
public void TearDown(){
     userClient.deleteUser(uniqueUser,token);
}
@Test
@DisplayName("Проверка возможности авторизация под созданным клиентом")
    public void UserCanBeLoginWithValidParameters(){
ValidatableResponse loginResponse=userClient.login(uniqueUserCredentials);
    String actualMsgSuccess=userClient.getPath(loginResponse,"success");
    String actualEmail=userClient.getPath(loginResponse,"user.email");
    String actualName=userClient.getPath(loginResponse,"user.name");
    //Asserts
    loginResponse.assertThat().statusCode(SC_OK);
    Assert.assertEquals("Incorrect creation message ",SUCCESS_MSG_TRUE,actualMsgSuccess);
    Assert.assertEquals("Incorrect email",uniqueUser.getEmail(),actualEmail);
    Assert.assertEquals("Incorrect name",uniqueUser.getName(),actualName);
    }
}
