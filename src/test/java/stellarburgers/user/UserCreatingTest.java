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

public class UserCreatingTest extends CommonTest implements TearDown {
    @Override
    @After
    public void deleteUser() {
        userClient.deleteUser(user, token);
    }

    @Test
    @DisplayName("Проверка создания уникального пользователя")
    public void UserСanBeCreatedWithValidParameters() {
        //выполнили запрос
        ValidatableResponse createResponse = userClient.create(user);
        //взяли нужны данные
        String actualMsgSuccess = getPath(createResponse, "success");
        String actualEmail = getPath(createResponse, "user.email");
        String actualName =getPath(createResponse, "user.name");
        token = getPath(createResponse, "accessToken");
        //Asserts
        createResponse.assertThat().statusCode(SC_OK);
        Assert.assertEquals("Incorrect creation message ", SUCCESS_MSG_TRUE, actualMsgSuccess);
        Assert.assertEquals("Incorrect email", user.getEmail(), actualEmail);
        Assert.assertEquals("Incorrect name", user.getName(), actualName);
    }
}
