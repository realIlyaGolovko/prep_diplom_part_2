package stellarburgers.order;


import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;


@Data
@Builder
//класс для формирования заказа
public class Order {
    private ArrayList<String> ingredients;
    private static Faker faker = new Faker();
    //инициализровали класс для выполнения запроса
    private static OrderClient orderClient = new OrderClient();
    //выполнили запрос на получение всех доступных иннгредиентов
    private static IngredientsData ingredientsData = orderClient.getIngredients();
    //взяли только ID ингредиентов
    private static ArrayList<String> ingredientsId = ingredientsData.getIngredientsId();
    //создали число для генерации кол-ва ингредиентов
    private static int quantityOfIngredients = faker.number().numberBetween(1, ingredientsId.size());

    public Order() {
    }

    public Order(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    @Step("Инициализировали заказ со случайными ингредиентами")
    public static Order getRandomOrder() {
        //создали список для хранения итогово результата
        ArrayList<String> resultId = new ArrayList<>();
        //наполнили список случайными ингредиентами
        for (int i = 0; i < quantityOfIngredients; i++) {
            resultId.add(ingredientsId.get(i));
        }
        return new Order(resultId);
    }
    @Step("Инициализировали невалидный заказ")
    public static Order getIncorrectOrder() {
        //создали список для хранения итогово результата
        ArrayList<String> resultId = new ArrayList<>();
        for (int i = 0; i < quantityOfIngredients; i++) {
            resultId.add(ingredientsId.get(i) + "x");
        }
        return new Order(resultId);
    }
}
