package stellarburgers.user;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import stellarburgers.common.CommonRestClient;
import static stellarburgers.common.Constants.*;
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
@Step("Запрос на изменение данных о пользователе с {user} и {token}")
public ValidatableResponse updateUser(User user,String token){
    return given().log().all()
            .header("Authorization",token)
            .spec(getBaseSpec())
            .body(user)
            .when()
            .patch(UPDATE_USER_PATH)
            .then().log().all();
}
//Методом тыка нашел метод для удаления пользователя, в документации не обнаружил
@Step("Запрос на удаление созданного пользователя {user} и {token}")
public ValidatableResponse deleteUser(User user,String token){
    return given().log().all()
            .header("Authorization",token)
            .spec(getBaseSpec())
            .body(user)
            .when()
            .delete(UPDATE_USER_PATH)
            .then().log().all();
}
    public String getAccessToken(ValidatableResponse validatableResponse){
        return validatableResponse.extract().jsonPath().getString("accessToken");
    }
}
