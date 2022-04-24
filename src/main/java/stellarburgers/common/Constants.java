package stellarburgers.common;

public class Constants {
    //урл стенда
    public static final String BASE_URL="https://stellarburgers.nomoreparties.site/";
    //урл для создания нового пользователя
    public static final String CREATE_USER_PATH="api/auth/register";
    //урл для авторизации
    public static final String LOGIN_USER_PATH ="api/auth/login";
    //урл для изменения информации  и удалении инфы о пользователе
    public static final String UPDATE_USER_PATH ="api/auth/user";

    //получение данных об ингредиентах
    public static final String GET_INGREDIENTS_PATH= "api/ingredients";
    //создание заказа
    public static final String CREATE_ORDER_PATH="api/orders";
}
