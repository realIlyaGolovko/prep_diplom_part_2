package stellarburgers.order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import stellarburgers.common.CommonForOrderTests;
import stellarburgers.common.SetUp;
import stellarburgers.common.TearDown;
import stellarburgers.user.UserCredentials;


import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;
import static org.hamcrest.Matchers.containsString;
import static stellarburgers.common.ConstantsForTests.CREATE_ORDER_ERROR_MSG;
import static stellarburgers.common.ConstantsForTests.SUCCESS_MSG_FALSE;


public class OrderCreateRequestValidationTest extends CommonForOrderTests implements SetUp, TearDown {

    @Override
    @Before
    public void CreateUser() {
        //выполнили запрос на создание пользователя
        userClient.create(user);
        userCredentials = UserCredentials.from(user);
        //выполнили запрос на авторизацию пользователя
        ValidatableResponse loginResponse = userClient.login(userCredentials);
        token = getPath(loginResponse, "accessToken");
    }

    @Override
    @After
    //выполнили запрос на удаление пользователя
    public void deleteUser() {
        userClient.deleteUser(user, token);
    }


    @Test
    @DisplayName("Ошибка при создании заказа  с null body")
    public void OrderCannotBeCreatedWithoutIngredients() {
        //инициализировали заказ с null в body
        order = new Order();
        //выполнили запрос на создание заказа
        ValidatableResponse createOrderResponse = orderClient.createOrder(order, token);
        //взяли нужные тестовые данные
        String actualSuccessMsg = getPath(createOrderResponse, "success");
        String actualMsg = getPath(createOrderResponse, "message");
        //Asserts
        createOrderResponse.assertThat().statusCode(SC_BAD_REQUEST);
        Assert.assertEquals(SUCCESS_MSG_FALSE, actualSuccessMsg);
        Assert.assertEquals(CREATE_ORDER_ERROR_MSG, actualMsg);
    }

    @Test
    @DisplayName("Ошибка при создании заказа  с невалидным body")
    public void OrderCannotBeCreatedWithIncorrectIngredients() {
        //инициализировали заказ с неверным хешем
        order = Order.getIncorrectOrder();
        //выполнили запрос на создание заказа
        ValidatableResponse createOrderResponse = orderClient.createOrder(order, token);
        //взяли нужные тестовые данные
        String actualStatus = createOrderResponse.extract().htmlPath().getString("pre");
        //Asserts
        createOrderResponse.assertThat().statusCode(SC_INTERNAL_SERVER_ERROR);
        MatcherAssert.assertThat(actualStatus, containsString("Internal Server Error"));
    }
}
