package stellarburgers.order;

import lombok.Builder;
import lombok.Data;
import java.util.List;
@Data
@Builder
//класс для формирования заказа
public class Order {
    private List<String> ingredients;

    public Order(){}

    public Order(List<String> ingredients)
    {this.ingredients=ingredients;}
}
