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

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static stellarburgers.common.ConstantsForTests.CREATE_USER_UNIQUE_ERROR_MSG;
import static stellarburgers.common.ConstantsForTests.SUCCESS_MSG_FALSE;

public class UserCreateUniqueValidationTest extends CommonTest implements SetUp, TearDown {

    @Override
    @Before
    public void CreateUser() {
        //создали нового уникального пользователя
        ValidatableResponse createResponse = userClient.create(user);
        token = getPath(createResponse, "accessToken");
    }

    @Override
    @After
    public void deleteUser() {
        //удалили тестовые данные
        userClient.deleteUser(user, token);
    }

    @Test
    @DisplayName("Ошибка при попытке создать пользователя, который уже зарегистрирован")
    public void NotUniqueUserCannotBeCreated() {
//сделали запрос на создание неуникального пользователя
        ValidatableResponse validatableResponse = userClient.create(user);
//взяли  нужные данные
        String actualSuccessMsg = getPath(validatableResponse, "success");
        String actualMsg = getPath(validatableResponse, "message");
//Asserts
        validatableResponse.assertThat().statusCode(SC_FORBIDDEN);
        Assert.assertEquals(SUCCESS_MSG_FALSE, actualSuccessMsg);
        Assert.assertEquals(CREATE_USER_UNIQUE_ERROR_MSG, actualMsg);
    }
}
