package stellarburgers;

import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import stellarburgers.user.User;
import stellarburgers.user.UserClient;
import stellarburgers.user.UserCredentials;

import static org.apache.http.HttpStatus.SC_OK;

public class UserLoginRequestValidationTest {
    @Test
    public void UserCanBeLoggedWithvalidParameters(){
        User user=User.getRandomUser();
        UserCredentials userCredentials=new UserCredentials(user.getEmail(),user.getPassword());
        UserClient userClient=new UserClient();
        ValidatableResponse createResponse= userClient.create(user);
        ValidatableResponse loginResponse=userClient.login(userCredentials);
        loginResponse.assertThat().statusCode(SC_OK);
    }
}
