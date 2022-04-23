package stellarburgers.user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static stellarburgers.common.ConstantsForTests.CREATE_USER_UNIQUE_MSG;
import static stellarburgers.common.ConstantsForTests.SUCCESS_MSG_FALSE;

public class UserCreatingUniqueValidationTest {
private static final User user=User.getRandomUser();
private static final UserClient userclient=new UserClient();
String token;
@Before
//Создали нового уникального пользователя
    public void UserCreating(){
    ValidatableResponse createResponse=userclient.create(user);
    token=userclient.getPath(createResponse,"accessToken");
}
@After
//удалили тестовые данные
    public void TearDown(){
    userclient.deleteUser(user,token);
}
@Test
@DisplayName("Проверка невозможности создать пользователя, который уже зарегистрирован")
    public void NotUniqueUserCannotBeCreated(){
//Делаем запрос на создание неуникального пользователя
ValidatableResponse validatableResponse=userclient.create(user);
String actualSuccessMsg=userclient.getPath(validatableResponse,"success");
String actualMsg=userclient.getPath(validatableResponse,"message");
//Asserts
validatableResponse.assertThat().statusCode(SC_FORBIDDEN);
Assert.assertEquals(SUCCESS_MSG_FALSE,actualSuccessMsg);
Assert.assertEquals(CREATE_USER_UNIQUE_MSG,actualMsg);
}

}
