package stellarburgers.user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import stellarburgers.common.CommonTest;
import static org.apache.http.HttpStatus.SC_OK;
import static stellarburgers.common.ConstantsForTests.SUCCESS_MSG_TRUE;

public class UserLoginTest extends CommonTest {
 private static final UserCredentials userCredentials =UserCredentials.from(user);
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
@DisplayName("Проверка авторизации под созданным клиентом")
    public void UserCanBeLoginWithValidParameters(){
ValidatableResponse loginResponse=userClient.login(userCredentials);
    String actualMsgSuccess=userClient.getPath(loginResponse,"success");
    String actualEmail=userClient.getPath(loginResponse,"user.email");
    String actualName=userClient.getPath(loginResponse,"user.name");
    //Asserts
    loginResponse.assertThat().statusCode(SC_OK);
    Assert.assertEquals("Incorrect creation message ",SUCCESS_MSG_TRUE,actualMsgSuccess);
    Assert.assertEquals("Incorrect email", user.getEmail(),actualEmail);
    Assert.assertEquals("Incorrect name", user.getName(),actualName);
    }
}
