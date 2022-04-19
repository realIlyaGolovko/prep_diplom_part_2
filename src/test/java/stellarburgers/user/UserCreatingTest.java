package stellarburgers.user;
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
        userClient.deleteUser(user,token);
    }
    @Test
    @DisplayName("Проверка создания уникального пользователя")
    public void UserСanBeCreatedWithValidParameters(){
        ValidatableResponse validatableResponse= userClient.create(user);
        String actualResult=validatableResponse.extract().jsonPath().getString("success");
        token= userClient.getAccessToken(validatableResponse);
        //Assert
        validatableResponse.assertThat().statusCode(SC_OK);
        Assert.assertEquals("Incorrect creation status ",expectedResult,actualResult);

    }

}
