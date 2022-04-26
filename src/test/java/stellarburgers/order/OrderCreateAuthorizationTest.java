package stellarburgers.order;


import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import stellarburgers.common.CommonTest;
import stellarburgers.common.SetUp;
import stellarburgers.common.TearDown;
import stellarburgers.user.UserCredentials;

import static org.apache.http.HttpStatus.SC_OK;

public class OrderCreateAuthorizationTest extends CommonTest implements SetUp, TearDown {
    private static final OrderClient orderClient = new OrderClient();
    private static final Order order = Order.getRandomOrder();


    @Override
    @Before
    public void CreateUser() {
        //выполнили запрос на создание пользователя
        ValidatableResponse createResponse = userClient.create(user);
        userCredentials = UserCredentials.from(user);
        //выполнили запрос на авторизацию пользователя
        ValidatableResponse loginResponse = userClient.login(userCredentials);
        token = userClient.getPath(loginResponse, "accessToken");
    }

    @Override
    @After
    //выполнили запрос на удаление пользователя
    public void deleteUser() {
        userClient.deleteUser(user, token);
    }

    @Test
    @DisplayName("Проверка возможности создания заказа авторизованным пользователем")
    public void OrderСanBeCreatedWithValidParametersAndAuthorization() {
        //выполнили запрос на создание заказа
        ValidatableResponse createOrderResponse = orderClient.createOrder(order, token);

        //Asserts
        createOrderResponse.assertThat().statusCode(SC_OK);
    }
}

