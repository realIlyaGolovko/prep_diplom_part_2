package stellarburgers.user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import stellarburgers.common.CommonTest;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static stellarburgers.common.ConstantsForTests.CREATE_USER_UNIQUE_ERROR_MSG;
import static stellarburgers.common.ConstantsForTests.SUCCESS_MSG_FALSE;

public class UserCreatingUniqueValidationTest extends CommonTest {
String token;
@Before
//Создали нового уникального пользователя
    public void UserCreating(){
    ValidatableResponse createResponse=userClient.create(user);
    token=userClient.getPath(createResponse,"accessToken");
}
@After
//удалили тестовые данные
    public void TearDown(){
    userClient.deleteUser(user,token);
}
@Test
@DisplayName("Проверка невозможности создать пользователя, который уже зарегистрирован")
    public void NotUniqueUserCannotBeCreated(){
//Делаем запрос на создание неуникального пользователя
ValidatableResponse validatableResponse=userClient.create(user);
String actualSuccessMsg=userClient.getPath(validatableResponse,"success");
String actualMsg=userClient.getPath(validatableResponse,"message");
//Asserts
validatableResponse.assertThat().statusCode(SC_FORBIDDEN);
Assert.assertEquals(SUCCESS_MSG_FALSE,actualSuccessMsg);
Assert.assertEquals(CREATE_USER_UNIQUE_ERROR_MSG,actualMsg);
}

}
