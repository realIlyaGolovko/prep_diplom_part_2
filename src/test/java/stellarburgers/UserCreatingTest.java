package stellarburgers;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import stellarburgers.user.User;
import stellarburgers.user.UserClient;

import static org.apache.http.HttpStatus.SC_OK;

public class UserCreatingTest {
    String expectedResult="true";
    User user=User.getRandomUser();
    UserClient userClient;
    String token;

    @Before
    public void setUp(){
        userClient=new UserClient();
    }
    @After
    public void tearDown(){
        ValidatableResponse deleteResponse=userClient.deleteUser(user,token);
    }
    @Test
    @DisplayName("Проверка создания уникального пользователя")
    public void UserСanBeCreatedWithValidParameters(){
        ValidatableResponse createResponse= userClient.create(user);
        String actualResult=createResponse.extract().jsonPath().getString("success");
        token=createResponse.extract().jsonPath().getString("accessToken");
        //Assert
        createResponse.assertThat().statusCode(SC_OK);
        Assert.assertEquals("Incorrect creation status ",expectedResult,actualResult);

    }

}
