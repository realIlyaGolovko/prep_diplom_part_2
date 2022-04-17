package stellarburgers;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static common.Constants.*;
import static io.restassured.RestAssured.given;

public class UserClient extends CommonRestClient {
@Step("Запрос на создание пользователя с {user} ")
public ValidatableResponse create (User user){
    return given().log().all()
            .spec(getBaseSpec())
            .body(user)
            .when()
            .post(CREATE_USER_PATH)
            .then().log().all();
}
@Step("Запрос на авторизацию пользователя c {userCredentials}")
public ValidatableResponse login(UserCredentials userCredentials){
    return given().log().all()
            .spec(getBaseSpec())
            .body(userCredentials)
            .when()
            .post(LOGIN_USER_PATH)
            .then().log().all();
}
@Step("Запрос на изменение данных о пользователе с {user} и {accessToken}")
public ValidatableResponse updateUser(User user,String accessToken){
    return given().log().all()
            .header("Authorization",accessToken)
            .spec(getBaseSpec())
            .body(user)
            .when()
            .patch(UPDATE_USER_PATH)
            .then().log().all();
}
}
