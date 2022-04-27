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
import static stellarburgers.common.ConstantsForTests.SUCCESS_MSG_TRUE;

public class OrderCreateAuthTest extends CommonForOrderTests implements SetUp, TearDown {
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
    @DisplayName("Создание заказа авторизованным пользователем")
    public void OrderСanBeCreatedWithValidParametersAndAuthorization() {
        //выполнили запрос на создание заказа
        ValidatableResponse createOrderResponse = orderClient.createOrder(order, token);
        //взяли нужные тестовые данные
        String actualSuccessMsg = getPath(createOrderResponse, "success");
        String actualStatus = getPath(createOrderResponse, "order.status");
        String actualName = getPath(createOrderResponse, "order.owner.name");
        String actualEmail = getPath(createOrderResponse, "order.owner.email");
        //Asserts
        createOrderResponse.assertThat().statusCode(SC_OK);
        Assert.assertEquals(SUCCESS_MSG_TRUE, actualSuccessMsg);
        Assert.assertEquals("done", actualStatus);
        Assert.assertEquals(user.getName(), actualName);
        Assert.assertEquals(user.getEmail(), actualEmail);
    }
}

