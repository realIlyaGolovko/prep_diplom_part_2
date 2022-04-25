package stellarburgers.order;

import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import java.util.List;

public class OrderCreateAuthorizationTest {
    @Test
            public void Test() {
        OrderClient orderClient = new OrderClient();
        IngredientsData ingredientsData = orderClient.getIngredients();
        List<String> ingredientsForOrder=ingredientsData.getIngredientsId();
        Order order=new Order(ingredientsForOrder);




    }
}
