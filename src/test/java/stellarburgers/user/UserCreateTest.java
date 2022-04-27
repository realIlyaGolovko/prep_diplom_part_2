package stellarburgers.user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import stellarburgers.common.CommonTest;
import stellarburgers.common.TearDown;

import static org.apache.http.HttpStatus.SC_OK;
import static stellarburgers.common.ConstantsForTests.SUCCESS_MSG_TRUE;

public class UserCreateTest extends CommonTest implements TearDown {
    @Override
    @After
    //выполнили запрос на удаление пользователя
    public void deleteUser() {
        userClient.deleteUser(user, token);
    }

    @Test
    @DisplayName("Создание нового уникального пользователя")
    public void UserСanBeCreatedWithValidParameters() {
        //выполнили запрос
        ValidatableResponse createResponse = userClient.create(user);
        token = getPath(createResponse, "accessToken");
        //взяли нужны данные
        String actualMsgSuccess = getPath(createResponse, "success");
        String actualEmail = getPath(createResponse, "user.email");
        String actualName = getPath(createResponse, "user.name");

        //Asserts
        createResponse.assertThat().statusCode(SC_OK);
        Assert.assertEquals("Incorrect creation message ", SUCCESS_MSG_TRUE, actualMsgSuccess);
        Assert.assertEquals("Incorrect email", user.getEmail(), actualEmail);
        Assert.assertEquals("Incorrect name", user.getName(), actualName);
    }
}
