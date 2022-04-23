package stellarburgers.user;

import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import stellarburgers.user.User;
import stellarburgers.user.UserClient;
import stellarburgers.user.UserCredentials;

public class UserUpdateRequestValidationTes {
    @Test
    public void UserCanBeUpdatedWithValidParameters(){
        User user=User.getRandomUser();
        UserCredentials userCredentials=new UserCredentials(user.getEmail(),user.getPassword());
        UserClient userClient=new UserClient();
        User newUser=new User("testxl@yacndex.ru","12345e","iilya");
        ValidatableResponse createResponse= userClient.create(user);
        ValidatableResponse loginResponse=userClient.login(userCredentials);
        String token=loginResponse.extract().jsonPath().getString("accessToken");
        ValidatableResponse updateResponse=userClient.updateUser(newUser,"");
    }
}
