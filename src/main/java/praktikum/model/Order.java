package praktikum.model;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order {
    private String number;
    private String name;

    public Order(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    private List<String> ingredients;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String  getIngredientsListAsJson() {
        Map<String, List<String>> data = new HashMap<>();
        data.put("ingredients", this.ingredients);
        return new JSONObject(data).toString();
    }

}
