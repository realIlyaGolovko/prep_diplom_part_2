package stellarburgers.order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import stellarburgers.common.CommonForOrderTests;
import stellarburgers.common.SetUp;
import stellarburgers.common.TearDown;
import stellarburgers.user.UserCredentials;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static stellarburgers.common.ConstantsForTests.*;

public class GetUserOrdersTest extends CommonForOrderTests implements SetUp, TearDown {


    @Override
    @Before
    public void CreateUser() {
        //выполнили запрос на создание пользователя
        userClient.create(user);
        userCredentials = UserCredentials.from(user);
        //выполнили запрос на авторизацию пользователя
        ValidatableResponse loginResponse = userClient.login(userCredentials);
        token = getPath(loginResponse, "accessToken");
        orderClient.createOrder(order, token);
    }

    @Override
    @After
    //выполнили запрос на удаление пользователя
    public void deleteUser() {
        userClient.deleteUser(user, token);
    }

    @Test
    @DisplayName("Ошибка при попытке получить список заказов неавторизованного пользователя")
    public void ordersUserInfoCannotBeGetNonAuthUser() {
        //выполнили запрос на получение заказов
        ValidatableResponse getOrderResponse = orderClient.getUserOrders("");
        //взяли нужные тестовые данные
        String actualSuccessMsg = getPath(getOrderResponse, "success");
        String actualMsg = getPath(getOrderResponse, "message");
        //Asserts
        getOrderResponse.assertThat().statusCode(SC_UNAUTHORIZED);
        Assert.assertEquals(SUCCESS_MSG_FALSE, actualSuccessMsg);
        Assert.assertEquals(AUTHORIZATION_USER_ERROR_MSG, actualMsg);
    }

    @Test
    @DisplayName("Получение списка заказов под авторизованным пользователем")
    public void ordersUserInfoCanBeGetAuthUser() {
        //выполнили запрос на получение заказов
        ValidatableResponse getOrderResponse = orderClient.getUserOrders(token);
        //взяли нужные тестовые данные
        String actualSuccessMsg = getPath(getOrderResponse, "success");
        String actualStatus = getPath(getOrderResponse, "orders.status");
        //Asserts
        getOrderResponse.assertThat().statusCode(SC_OK);
        Assert.assertEquals(SUCCESS_MSG_TRUE, actualSuccessMsg);
        Assert.assertEquals("[done]", actualStatus);
    }
}
