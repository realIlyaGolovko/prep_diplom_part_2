package stellarburgers.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import stellarburgers.common.CommonRestClient;
import static io.restassured.RestAssured.given;
import static stellarburgers.common.Constants.CREATE_ORDER_PATH;
import static stellarburgers.common.Constants.GET_INGREDIENTS_PATH;

public class OrderClient extends CommonRestClient {
 @Step("Получение списка всех ингредиентов")
public IngredientsData getIngredients(){
 return given().log().all()
         .spec(getBaseSpec())
         .when()
         .get(GET_INGREDIENTS_PATH)
         .body().as(IngredientsData.class);
}
@Step("Запрос на создание заказа")
public ValidatableResponse createOrder(Order order,String token){
  return given().log().all()
          .spec(getBaseSpec())
          .header("Authorization",token)
          .body(order)
          .when()
          .post(CREATE_ORDER_PATH)
          .then().log().all();
}
}
