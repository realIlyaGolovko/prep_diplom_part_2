package stellarburgers.order;

import io.restassured.response.ValidatableResponse;
import org.junit.Test;

public class OrderCreateAuthorizationTest {
    @Test
            public void Test() {
        OrderClient orderClient = new OrderClient();
        IngredientsData ingredientsData = orderClient.getIngredients();
        Order order=new Order();
        ValidatableResponse validatableResponse=orderClient.createOrder(order);
        validatableResponse.assertThat().statusCode(200);
    }
}
