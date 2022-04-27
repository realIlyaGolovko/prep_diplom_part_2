package stellarburgers.user;

import com.github.javafaker.Faker;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import lombok.Builder;
import lombok.Data;

import java.util.Locale;

@Data
@Builder
//не указывал геттеры и сеттеры, т.к. использовал ломбок
public class User {
    private String email;
    private String password;
    private String name;
    private static Faker faker = new Faker(new Locale("ru"));

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Step("Инициализировали нового пользователя с рандомными данными")
    public static User getRandomUser() {
        String email = faker.internet().emailAddress();
        Allure.addAttachment("email", email);
        String password = faker.internet().password();
        Allure.addAttachment("password", password);
        String name = faker.name().firstName();
        Allure.addAttachment("name", name);
        return new User(email, password, name);
    }
}
