package stellarburgers.common;

import io.restassured.response.ValidatableResponse;
import stellarburgers.user.User;
import stellarburgers.user.UserClient;
import stellarburgers.user.UserCredentials;


//базовый класс для тестов
public class CommonTest {
    protected UserClient userClient = new UserClient();
    protected User user = User.getRandomUser();
    protected String token;
    protected UserCredentials userCredentials;

    // метод для получения нужной строки из тела
    public String getPath(ValidatableResponse validatableResponse, String path) {
        return validatableResponse.extract().jsonPath().getString(path);
    }
}
