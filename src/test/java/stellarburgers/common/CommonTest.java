package stellarburgers.common;

import stellarburgers.user.User;
import stellarburgers.user.UserClient;
import stellarburgers.user.UserCredentials;

//базовый класс для тестов
public class CommonTest {
    protected  User user = User.getRandomUser();
    protected  UserClient userClient = new UserClient();
    protected String token;
    protected UserCredentials userCredentials;
}
