package stellarburgers;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Test;
import static org.apache.http.HttpStatus.SC_OK;

public class UserCreatingTest {
    @Test
    @DisplayName("Проверка создания уникального пользователя")
    public void UserСanBeCreatedWithValidParameters(){
        //Arrange
        User user=User.getRandomUser();
        UserClient userClient=new UserClient();
        String expectedResult="true";
        //Act
        ValidatableResponse createResponse= userClient.create(user);
        String actualResult=createResponse.extract().jsonPath().getString("success");

        //Assert
        createResponse.assertThat().statusCode(SC_OK);
        Assert.assertEquals("Incorrect creation status ",expectedResult,actualResult);

    }

}
