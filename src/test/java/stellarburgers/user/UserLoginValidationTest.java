package stellarburgers.user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import stellarburgers.common.CommonTest;
import stellarburgers.common.SetUp;
import stellarburgers.common.TearDown;

import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static stellarburgers.common.ConstantsForTests.*;

public class UserLoginValidationTest extends CommonTest implements SetUp, TearDown {
    @Override
    @Before
    public void CreateUser() {
        //выполнили запрос на создание клиента
        ValidatableResponse createResponse = userClient.create(user);
        token = getPath(createResponse, "accessToken");
    }

    @Override
    @After
    public void deleteUser() {
        //почистили данные после
        userClient.deleteUser(user, token);
    }

    @Test
    @DisplayName("Ошибка при авторизации под клиентом с неверным логином и паролем")
    public void UserCannotBeLoginWithInvalidParameters() {
        //создаем невалидные данные по клиенту
        userCredentials = new UserCredentials(user.getEmail() + "q", user.getPassword() + "w");
        //выполняем запрос на авторизацию
        ValidatableResponse loginResponse = userClient.login(userCredentials);
        //берем нужные данные
        String actualMsgSuccess = getPath(loginResponse, "success");
        String actualErrorMsg = getPath(loginResponse, "message");
        //Asserts
        loginResponse.assertThat().statusCode(SC_UNAUTHORIZED);
        Assert.assertEquals("Incorrect creation message ", SUCCESS_MSG_FALSE, actualMsgSuccess);
        Assert.assertEquals("Incorrect message", LOGIN_USER_ERROR_MSG, actualErrorMsg);
    }


}
