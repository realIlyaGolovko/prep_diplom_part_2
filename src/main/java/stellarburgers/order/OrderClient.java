package stellarburgers.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import stellarburgers.common.CommonRestClient;

import static io.restassured.RestAssured.given;
import static stellarburgers.common.Constants.*;

public class OrderClient extends CommonRestClient {
    @Step("Запрос на получение списка всех ингредиентов")
    public IngredientsData getIngredients() {
        return given().log().all()
                .spec(getBaseSpec())
                .when()
                .get(GET_INGREDIENTS_PATH)
                .body().as(IngredientsData.class);
    }

    @Step("Запрос на создание заказа")
    public ValidatableResponse createOrder(Order order, String token) {
        return given().log().all()
                .spec(getBaseSpec())
                .header("Authorization", token)
                .body(order)
                .when()
                .post(CREATE_ORDER_PATH)
                .then().log().all();
    }

    @Step("Запрос на получение заказов конкретного пользователя")
    public ValidatableResponse getUserOrders(String token) {
        return given().log().all()
                .spec(getBaseSpec())
                .header("Authorization", token)
                .when()
                .get(GET_USER_ORDERS_PATH)
                .then().log().all();
    }
}
