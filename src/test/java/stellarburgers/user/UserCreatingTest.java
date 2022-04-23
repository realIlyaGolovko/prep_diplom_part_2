package stellarburgers.user;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import static org.apache.http.HttpStatus.SC_OK;
import static stellarburgers.common.ConstantsForTests.SUCCESS_MSG_TRUE;

public class UserCreatingTest {
    private static final User user=User.getRandomUser();
    private static final UserClient userClient=new UserClient();
    private String token;

    @After
    //удалили тестовые данные
    public void tearDown(){
        userClient.deleteUser(user,token);
    }

    @Test
    @DisplayName("Проверка создания уникального пользователя")
    public void UserСanBeCreatedWithValidParameters(){
        //выполнили запрос
        ValidatableResponse createResponse= userClient.create(user);
        //взяли нужны данные
        String actualMsgSuccess=userClient.getPath(createResponse,"success");
        String actualEmail=userClient.getPath(createResponse,"user.email");
        String actualName=userClient.getPath(createResponse,"user.name");
        token= userClient.getPath(createResponse,"accessToken");
        //Asserts
        createResponse.assertThat().statusCode(SC_OK);
        Assert.assertEquals("Incorrect creation message ",SUCCESS_MSG_TRUE,actualMsgSuccess);
        Assert.assertEquals("Incorrect email",user.getEmail(),actualEmail);
        Assert.assertEquals("Incorrect name",user.getName(),actualName);
    }

}
