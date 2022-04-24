package stellarburgers.order;

import lombok.Data;

import java.util.List;
@Data
public class IngredientsData {
private boolean success;
private List<Ingredient> data;
public IngredientsData (){};


}
