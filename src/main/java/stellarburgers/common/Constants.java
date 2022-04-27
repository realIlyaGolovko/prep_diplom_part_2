package stellarburgers.common;

// константы с урл окружения и эндпоинтов
public class Constants {
    //урл стенда
    public static final String BASE_URL = "https://stellarburgers.nomoreparties.site/";
    //урл для создания нового пользователя
    public static final String CREATE_USER_PATH = "api/auth/register";
    //урл для авторизации
    public static final String LOGIN_USER_PATH = "api/auth/login";
    //урл для изменения информации  и удалении инфы о пользователе
    public static final String UPDATE_USER_PATH = "api/auth/user";

    //урл для получения данных об ингредиентах
    public static final String GET_INGREDIENTS_PATH = "api/ingredients";
    //урл для создания заказа
    public static final String CREATE_ORDER_PATH = "api/orders";
    //урл для получения заказов конкретного пользователя
    public static final String GET_USER_ORDERS_PATH = "api/orders";
}
