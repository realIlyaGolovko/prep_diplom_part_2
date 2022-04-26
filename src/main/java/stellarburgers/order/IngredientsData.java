package stellarburgers.order;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
//класс для формирования списка ингредиентов
public class IngredientsData {
private boolean success;
private List<Ingredient> data;

public IngredientsData (){}

public List<String> getIngredientsId(){
    ArrayList<String> resultId= new ArrayList<>();
    for (Ingredient datum : data) {
        resultId.add(datum.get_id());
    }
 return resultId;
}
}
