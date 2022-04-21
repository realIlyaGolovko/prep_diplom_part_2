package stellarburgers.user;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import  static  org.apache.http.HttpStatus.*;
import static stellarburgers.common.ConstantsForTests.CREATE_USER_ERROR_MSG;
import static stellarburgers.common.ConstantsForTests.SUCCESS_MSG_FALSE;

@RunWith(Parameterized.class)
public class UserCreatingRequestValidationTest {
private static final UserClient userclient=new UserClient();
private static User user=User.getRandomUser();

public UserCreatingRequestValidationTest(User user){
    this.user=user;
}
@Parameterized.Parameters
    public static Object[][] getUserData(){
    return new Object[][]{
            {User.builder()
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .build()},//пользователь без параметра name
            {User.builder()
                    .email(user.getEmail())
                    .name(user.getName())
                    .build()},// пользователь без параметра password
            {User.builder()
                    .password(user.getPassword())
                    .name(user.getName())
                    .build()}// пользователь без параметра email
    };
}

@Test
@DisplayName("Проверка невозможности создания пользователя без одного из обязательных полей")
public void userCannotBeCreatedWithIncompleteData(){
    ValidatableResponse createResponse= userclient.create(user);
    String actualSuccessMsg=userclient.getPath(createResponse,"success");
    String actualErrorMsg=userclient.getPath(createResponse,"message");
    //Asserts
    createResponse.statusCode(SC_FORBIDDEN);
    Assert.assertEquals("Incorrect success status",actualSuccessMsg,SUCCESS_MSG_FALSE);
    Assert.assertEquals("Incorrect message",actualErrorMsg,CREATE_USER_ERROR_MSG);
    //Данные для отчета
    Allure.addAttachment("Данные клиента", String.valueOf(user));
    Allure.addAttachment("Статус ответа",actualSuccessMsg);
    Allure.addAttachment("Сообщение об ошибке",actualErrorMsg);
}
}
