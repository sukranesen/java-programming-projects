import java.util.*;

public class Animal {
    private String type;  // e.g., Lion, Elephant
    private String name;
    private int age;

    public Animal(String type, String name, int age) {
        this.type = type;
        this.name = name;
        this.age = age;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    // Calculates required meal amount per meal depending on type and age
    public Map<String, Double> getMealAmountPerMeal() {
        Map<String, Double> mealMap = new HashMap<>();
        switch (type.toLowerCase()) {
            case "lion":
                double lionMeal = 5.0 + (age - 5) * 0.05;
                mealMap.put("Meat", lionMeal);
                break;
            case "elephant":
                double elephantMeal = 10.0 + (age - 20) * 0.015;
                mealMap.put("Plant", elephantMeal);
                break;
            case "penguin":
                double penguinMeal = 3.0 + (age - 4) * 0.04;
                mealMap.put("Fish", penguinMeal);
                break;
            case "chimpanzee":
                double chimpMeal = 3.0 + (age - 10) * 0.025;
                mealMap.put("Meat", chimpMeal);
                mealMap.put("Plant", chimpMeal);
                break;
        }
        return mealMap;
    }

    // Returns cleaning message depending on animal type
    public String getCleaningMessage() {
        switch (type.toLowerCase()) {
            case "lion":
                return "Cleaning " + name + "'s habitat: Removing bones and refreshing sand.";
            case "elephant":
                return "Cleaning " + name + "'s habitat: Washing the water area.";
            case "penguin":
                return "Cleaning " + name + "'s habitat: Replenishing ice and scrubbing walls.";
            case "chimpanzee":
                return "Cleaning " + name + "'s habitat: Sweeping the enclosure and replacing branches.";
            default:
                return "Cleaning " + name + "'s habitat.";
        }
    }

    // Returns special food label names for output depending on animal type
    public Map<String, String> getFoodLabels() {
        Map<String, String> labels = new HashMap<>();
        switch (type.toLowerCase()) {
            case "elephant":
                labels.put("Plant", "assorted fruits and hay");
                break;
            case "penguin":
                labels.put("Fish", "various kinds of fish");
                break;
            default:
                labels.put("Meat", "meat");
                labels.put("Plant", "leaves");
                labels.put("Fish", "fish");
                break;
        }
        return labels;
    }
}
