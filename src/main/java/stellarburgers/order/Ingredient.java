package stellarburgers.order;

import lombok.Data;

@Data
//Вспомогательный класс ддя парсинга ингредиентов
public class Ingredient {
    private String _id;
    private String name;
    private String type;
    private int proteins;
    private int fat;
    private int carbohydrates;
    private int calories;
    private int price;
    private String image;
    private String image_mobile;
    private String image_large;
    private int __v;

    public Ingredient() {
    }
}
