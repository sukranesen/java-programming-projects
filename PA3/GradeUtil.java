import java.util.HashMap;
import java.util.Map;

// Utility class for converting letter grades to 4-point scale
public class GradeUtil {

    // Stores letter grades and their numeric values
    private static final Map<String, Double> gradeMap = new HashMap<>();

    // Initializes the grade map with predefined values
    static {
        gradeMap.put("A1", 4.0);
        gradeMap.put("A2", 3.5);
        gradeMap.put("B1", 3.0);
        gradeMap.put("B2", 2.5);
        gradeMap.put("C1", 2.0);
        gradeMap.put("C2", 1.5);
        gradeMap.put("D1", 1.0);
        gradeMap.put("D2", 0.5);
        gradeMap.put("F3", 0.0);
    }

    // Returns the numeric value of a letter grade, or -1.0 if invalid
    public static double getGradePoint(String letter) {
        return gradeMap.getOrDefault(letter, -1.0);
    }
}
