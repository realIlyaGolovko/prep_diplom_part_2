package stellarburgers.user;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import stellarburgers.common.CommonTest;
import stellarburgers.common.SetUp;
import stellarburgers.common.TearDown;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static stellarburgers.common.ConstantsForTests.SUCCESS_MSG_FALSE;
import static stellarburgers.common.ConstantsForTests.UPDATE_USER_ERROR_MSG;

@RunWith(Parameterized.class)
public class UserUpdateUnauthorizationTest extends CommonTest implements SetUp, TearDown {
    //создали еще одного пользователя
 private static final User newUser = User.getRandomUser();

    @Override
    @Before
    public void CreateUser() {
        // выполнили запрос на создание нового пользователя
ValidatableResponse createResponse= userClient.create(user);
token=userClient.getPath(createResponse,"accessToken");
    }

    @Override
    @After
    public void deleteUser() {
//почистили тестовые данные
 userClient.deleteUser(user,token);
    }

public UserUpdateUnauthorizationTest(User user){
        this.user=user;
}
//создаем тестовые данные
    @Parameterized.Parameters
    public static Object[][] getUserDataForUpdate(){
        return new Object[][]{
                {User.builder()
                        .email(newUser.getEmail())
                        .password(user.getPassword())
                        .name(user.getName())
                        .build()},//пользователь c измененным email
                {User.builder()
                        .email(user.getEmail())
                        .password(newUser.getPassword())
                        .name(user.getName())
                        .build()},// пользователь с измененным password
                {User.builder()
                        .email(user.getEmail())
                        .password(user.getPassword())
                        .name(newUser.getName())
                        .build()}// пользователь с измененным name
        };
    }
    @Test
    @DisplayName("Проверка валдиации при попытке изменить данные под неавторизированным пользователем")
    public void UserUnauthorizationCannotBeUpdated() {
        //выполнили запрос на изменение данных без указания токена
        ValidatableResponse updateResponse = userClient.updateUser(newUser, "");
        //берем нужные данные
        String actualSuccessMsg=userClient.getPath(updateResponse,"success");
        String actualErrorMsg=userClient.getPath(updateResponse,"message");
//Asserts
        updateResponse.assertThat().statusCode(SC_UNAUTHORIZED);
        Assert.assertEquals(SUCCESS_MSG_FALSE,actualSuccessMsg);
        Assert.assertEquals(UPDATE_USER_ERROR_MSG,actualErrorMsg);
    }
}
