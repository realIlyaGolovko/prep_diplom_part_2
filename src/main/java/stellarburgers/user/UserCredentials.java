package stellarburgers.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
//не указывал геттеры и сеттеры, т.к. использовал ломбок
public class UserCredentials {
    private String email;
    private String password;

    public UserCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static UserCredentials from(User user) {
        return new UserCredentials(user.getEmail(), user.getPassword());
    }
}
