package stellarburgers.user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import stellarburgers.common.CommonTest;
import stellarburgers.common.SetUp;
import stellarburgers.common.TearDown;
import static org.apache.http.HttpStatus.SC_OK;
import static stellarburgers.common.ConstantsForTests.*;

public class UserUpdateAuthorizationTest extends CommonTest implements SetUp, TearDown {
 //Создали еще одного пользователя
 private static final User newUser=User.getRandomUser();
    @Override
    @Before
    public void CreateUser() {
        ValidatableResponse createResponse= userClient.create(user);
        token=userClient.getPath(createResponse,"accessToken");
    }

    @Override
    @After
    public void deleteUser() {
        //почистили тестовые данные
        userClient.deleteUser(user,token);
    }
    @Test
    @DisplayName("Проверка валдиации при попытке изменить данные под авторизированным пользователем")
    public void UserAuthorizationCannotBeUpdated() {
        //выполнили запрос на изменение данных
        ValidatableResponse updateResponse = userClient.updateUser(newUser, token);
        //берем нужные данные
        String actualMsgSuccess=userClient.getPath(updateResponse,"success");
        String actualEmail=userClient.getPath(updateResponse,"user.email");
        String actualName=userClient.getPath(updateResponse,"user.name");
//Asserts
        updateResponse.assertThat().statusCode(SC_OK);
        Assert.assertEquals("Incorrect creation message ",SUCCESS_MSG_TRUE,actualMsgSuccess);
        Assert.assertEquals("Incorrect email",newUser.getEmail(),actualEmail);
        Assert.assertEquals("Incorrect name",newUser.getName(),actualName);
    }
}
