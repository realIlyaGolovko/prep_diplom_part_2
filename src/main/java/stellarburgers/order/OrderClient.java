package stellarburgers.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import stellarburgers.common.CommonRestClient;

import static io.restassured.RestAssured.given;
import static stellarburgers.common.Constants.CREATE_ORDER_PATH;
import static stellarburgers.common.Constants.GET_INGREDIENTS_PATH;

public class OrderClient extends CommonRestClient {
 @Step("Получить список всех ингредиентов")
public IngredientsData getIngredients(){
 return given().log().all()
         .spec(getBaseSpec())
         .when()
         .get(GET_INGREDIENTS_PATH)
         .body().as(IngredientsData.class);
}
public ValidatableResponse createOrder(Order order){
  return given().log().all()
          .spec(getBaseSpec())
          .body(order)
          .when()
          .post(CREATE_ORDER_PATH)
          .then().log().all();
}
}
