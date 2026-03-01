import java.util.*;
import java.util.Locale;

public class FoodStock {
    private Map<String, Double> stock;

    public FoodStock() {
        stock = new HashMap<>();
    }

    // Adds initial food amount to stock
    public void addFood(String type, double amount) {
        stock.put(type, amount);
    }

    // Consumes a specific amount of a food type
    public void consume(String type, double amount) throws NotEnoughFoodException {
        if (!stock.containsKey(type)) {
            throw new NotEnoughFoodException("Error: Not enough " + capitalize(type));
        }
        double current = stock.get(type);
        if (current < amount) {
            throw new NotEnoughFoodException("Error: Not enough " + capitalize(type));
        }
        stock.put(type, current - amount);
    }

    // Feeds animal by consuming all required food types
    public void feedAnimal(Map<String, Double> mealMap) throws NotEnoughFoodException {
        // First verify all food is available
        for (Map.Entry<String, Double> entry : mealMap.entrySet()) {
            String type = entry.getKey();
            double required = entry.getValue();
            if (!stock.containsKey(type) || stock.get(type) < required) {
                throw new NotEnoughFoodException("Error: Not enough " + capitalize(type));
            }
        }

        // Then consume
        for (Map.Entry<String, Double> entry : mealMap.entrySet()) {
            String type = entry.getKey();
            double required = entry.getValue();
            stock.put(type, stock.get(type) - required);
        }
    }

    // Returns formatted stock list
    public String getFormattedStock() {
        StringBuilder sb = new StringBuilder();
        List<String> order = Arrays.asList("Plant", "Fish", "Meat");
        for (String food : order) {
            if (stock.containsKey(food)) {
                sb.append(food).append(": ")
                        .append(String.format(Locale.US, "%.3f", stock.get(food)))
                        .append(" kgs\n");
            }
        }
        return sb.toString();
    }

    public Map<String, Double> getStockMap() {
        return stock;
    }

    // Capitalizes first letter
    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
}
