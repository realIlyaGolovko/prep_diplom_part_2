package stellarburgers.user;

import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import stellarburgers.common.CommonTest;

import static org.apache.http.HttpStatus.*;
import static stellarburgers.common.ConstantsForTests.CREATE_USER_ERROR_MSG;
import static stellarburgers.common.ConstantsForTests.SUCCESS_MSG_FALSE;

@RunWith(Parameterized.class)
public class UserCreateRequestValidationTest extends CommonTest {
    private static final User newUser = User.getRandomUser();

    public UserCreateRequestValidationTest(User newUser) {
        this.user = newUser;
    }

    //создаем тестовые данные
    @Parameterized.Parameters
    public static Object[][] getUserData() {
        return new Object[][]{
                {User.builder()
                        .email(newUser.getEmail())
                        .password(newUser.getPassword())
                        .build()},//пользователь без параметра name
                {User.builder()
                        .email(newUser.getEmail())
                        .name(newUser.getName())
                        .build()},// пользователь без параметра password
                {User.builder()
                        .password(newUser.getPassword())
                        .name(newUser.getName())
                        .build()}// пользователь без параметра email
        };
    }

    @Test
    @DisplayName("Ошибка при создании пользователя без одного из обязательных полей")
    public void userCannotBeCreatedWithIncompleteData() {
        // выполнили запрос на создание пользователя
        ValidatableResponse createResponse = userClient.create(user);
        //взяли нужные данные
        String actualSuccessMsg = getPath(createResponse, "success");
        String actualErrorMsg = getPath(createResponse, "message");
        //Asserts
        createResponse.assertThat().statusCode(SC_FORBIDDEN);
        Assert.assertEquals("Incorrect success status", SUCCESS_MSG_FALSE, actualSuccessMsg);
        Assert.assertEquals("Incorrect message", CREATE_USER_ERROR_MSG, actualErrorMsg);
        //Данные для отчета
        Allure.addAttachment("Данные клиента", String.valueOf(user));
        Allure.addAttachment("Статус ответа", actualSuccessMsg);
        Allure.addAttachment("Сообщение об ошибке", actualErrorMsg);
    }
}
