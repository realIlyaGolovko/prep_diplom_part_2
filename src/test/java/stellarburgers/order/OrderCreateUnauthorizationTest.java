package stellarburgers.order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import stellarburgers.common.CommonTest;
import stellarburgers.common.SetUp;
import stellarburgers.common.TearDown;
import stellarburgers.user.UserCredentials;

import static org.apache.http.HttpStatus.SC_OK;
import static stellarburgers.common.ConstantsForTests.SUCCESS_MSG_TRUE;

public class OrderCreateUnauthorizationTest extends CommonTest implements SetUp, TearDown {
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
        token = getPath(loginResponse, "accessToken");
    }

    @Override
    @After
    //выполнили запрос на удаление пользователя
    public void deleteUser() {
        userClient.deleteUser(user, token);
    }
    @Test
    @DisplayName("Проверка возможности создания заказа неавторизованным пользователем")
    public void OrderСanBeCreatedWithValidParametersAndWithoutAuthorization() {
        //выполнили запрос на создание заказа
        ValidatableResponse createOrderResponse = orderClient.createOrder(order, "");
        //взяли нужные тестовые данные
        String actualSuccessMsg = getPath(createOrderResponse, "success");

        //Asserts
        createOrderResponse.assertThat().statusCode(SC_OK);
        Assert.assertEquals(SUCCESS_MSG_TRUE, actualSuccessMsg);
    }
}
