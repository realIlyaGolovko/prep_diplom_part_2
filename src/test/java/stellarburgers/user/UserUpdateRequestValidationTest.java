package stellarburgers.user;

import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import stellarburgers.common.CommonTest;

public class UserUpdateRequestValidationTest extends CommonTest {
    @Test
    public void UserCanBeUpdatedWithValidParameters(){
        UserCredentials userCredentials=new UserCredentials(user.getEmail(),user.getPassword());
        User newUser=new User("testxl@yacndex.ru","12345e","iilya");
        ValidatableResponse createResponse= userClient.create(user);
        ValidatableResponse loginResponse=userClient.login(userCredentials);
        String token=loginResponse.extract().jsonPath().getString("accessToken");
        ValidatableResponse updateResponse=userClient.updateUser(newUser,"");
    }
}
