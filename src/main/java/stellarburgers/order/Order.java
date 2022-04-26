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
    private static Faker faker=new Faker();

    public Order() {
    }

    public Order(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }
    @Step("Сформировли заказ со случайными ингредиентами")
    public static Order getRandomOrder(){
        //инициализровали класс для выполнения запроса
        OrderClient orderClient=new OrderClient();
        //выполнили запрос на получение всех доступных иннгредиентов
        IngredientsData ingredientsData=orderClient.getIngredients();
        //взяли только ID ингредиентов
        ArrayList<String> ingredientsId=ingredientsData.getIngredientsId();
        //создали число для генерации кол-ва ингредиентов
        int quantityOfIngredients=faker.number().numberBetween(1,ingredientsId.size());
        //создали список для хранения итогово результата
        ArrayList<String> resultId= new ArrayList<>();
        //наполнили список случайными ингредиентами
        for (int i=0;i<quantityOfIngredients;i++){
            resultId.add(ingredientsId.get(i));
        }
        return new Order(resultId);

    }
}
