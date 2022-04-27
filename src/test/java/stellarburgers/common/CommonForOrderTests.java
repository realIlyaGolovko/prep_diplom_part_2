package stellarburgers.common;

import stellarburgers.order.Order;
import stellarburgers.order.OrderClient;

//базовый класс для тестов с заказом
public class CommonForOrderTests extends CommonTest {
    protected OrderClient orderClient = new OrderClient();
    protected Order order = Order.getRandomOrder();

}
