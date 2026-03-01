import java.util.*;
import java.util.Locale;

public class Person {
    // Fields for person's name, ID, and role
    private String name;
    private int id;
    private String role; // "Visitor" or "Personnel"

    // Constructor initializing person details
    public Person(String name, int id, String role) {
        this.name = name;
        this.id = id;
        this.role = role;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Getter for ID
    public int getId() {
        return id;
    }

    // Getter for role
    public String getRole() {
        return role;
    }

    // Handles either visit (if visitor) or cleaning (if personnel)
    public List<String> visit(Animal animal) {
        List<String> result = new ArrayList<>();

        if (role.equalsIgnoreCase("Visitor")) {
            // Visitor attempts and successfully visits the animal
            result.add(name + " tried  to register for a visit to " + animal.getName() + ".");
            result.add(name + " successfully visited " + animal.getName() + ".");
        } else if (role.equalsIgnoreCase("Personnel")) {
            // Personnel attempts and starts cleaning
            result.add(name + " attempts to clean " + animal.getName() + "'s habitat.");
            result.add(name + " started cleaning " + animal.getName() + "'s habitat.");
            result.add(animal.getCleaningMessage()); // Animal-specific cleaning message
        }

        return result;
    }

    // Handles feeding animals (only personnel allowed)
    public List<String> feed(Animal animal, int meals, FoodStock stock)
            throws UnauthorizedActionException, NotEnoughFoodException {

        List<String> result = new ArrayList<>();

        if (!role.equalsIgnoreCase("Personnel")) {
            // Visitors are not allowed to feed animals
            result.add(name + " tried to feed " + animal.getName());
            result.add("Error: Visitors do not have the authority to feed animals.");
            return result;
        }

        result.add(name + " attempts to feed " + animal.getName() + ".");

        // Get required food types and amounts per meal
        Map<String, Double> mealMap = animal.getMealAmountPerMeal();
        Map<String, Double> totalMeal = new HashMap<>();

        // Calculate total required food based on number of meals
        for (Map.Entry<String, Double> entry : mealMap.entrySet()) {
            totalMeal.put(entry.getKey(), entry.getValue() * meals);
        }

        // Try to consume food from stock (throws exception if not enough)
        stock.feedAnimal(totalMeal);

        if (totalMeal.size() == 1) {
            // If only one food type (e.g., Lion → Meat)
            for (Map.Entry<String, Double> entry : totalMeal.entrySet()) {
                String label = animal.getType().equalsIgnoreCase("penguin")
                        ? "various kinds of fish"
                        : entry.getKey().toLowerCase(); // Default to lowercase type
                result.add(animal.getName() + " has been given " +
                        String.format(Locale.US, "%.3f", entry.getValue()) +
                        " kgs of " + label);
            }
        } else {
            // For animals that eat both meat and plants (e.g., Chimpanzee)
            double val = totalMeal.values().iterator().next();
            result.add(animal.getName() + " has been given " +
                    String.format(Locale.US, "%.3f", val) + " kgs of meat and " +
                    String.format(Locale.US, "%.3f", val) + " kgs of leaves");
        }

        return result;
    }
}
