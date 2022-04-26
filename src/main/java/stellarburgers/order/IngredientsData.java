package stellarburgers.order;

import lombok.Data;

import java.util.ArrayList;

@Data
public class IngredientsData {
    private boolean success;
    private ArrayList<Ingredient> data;

    public IngredientsData() {
    }

    //метод для получения только ID ингредиентов
    public ArrayList<String> getIngredientsId() {
        ArrayList<String> resultListId = new ArrayList<>();
        for (Ingredient datum : data) {
            resultListId.add(datum.get_id());
        }
        return resultListId;
    }

}
