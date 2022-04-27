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

import static org.apache.http.HttpStatus.SC_OK;
import static stellarburgers.common.ConstantsForTests.SUCCESS_MSG_TRUE;

public class UserLoginTest extends CommonTest implements SetUp, TearDown {

    @Override
    @Before
    public void CreateUser() {
// выполнили запрос на создание клиента
        userClient.create(user);
        userCredentials = UserCredentials.from(user);
    }

    @Override
    @After
//удалили тестовые данные
    public void deleteUser() {
        userClient.deleteUser(user, token);
    }

    @Test
    @DisplayName("Авторизации под созданным клиентом")
    public void UserCanBeLoginWithValidParameters() {
//выполнили запрос на авторизацию
        ValidatableResponse loginResponse = userClient.login(userCredentials);
        token = getPath(loginResponse, "accessToken");
//берем нужные данные
        String actualMsgSuccess = getPath(loginResponse, "success");
        String actualEmail = getPath(loginResponse, "user.email");
        String actualName = getPath(loginResponse, "user.name");
        //Asserts
        loginResponse.assertThat().statusCode(SC_OK);
        Assert.assertEquals("Incorrect creation message ", SUCCESS_MSG_TRUE, actualMsgSuccess);
        Assert.assertEquals("Incorrect email", user.getEmail(), actualEmail);
        Assert.assertEquals("Incorrect name", user.getName(), actualName);
    }

}
