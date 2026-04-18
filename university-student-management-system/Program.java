import java.util.ArrayList;
import java.util.List;

// Represents an academic program in a department
public class Program extends AcademicEntity implements Reportable {

    private String department;         // Related department name
    private String degreeLevel;        // Degree level (e.g., Bachelor)
    private int requiredCredits;       // Total required credits for graduation
    private List<String> courseCodes;  // List of course codes in this program

    // Constructor to initialize program information
    public Program(String code, String name, String description, String department, String degreeLevel, int requiredCredits) {
        super(code, name, description);
        this.department = department;
        this.degreeLevel = degreeLevel;
        this.requiredCredits = requiredCredits;
        this.courseCodes = new ArrayList<>();
    }

    // Adds a course to the program if not already added
    public void addCourse(String courseCode) {
        if (!courseCodes.contains(courseCode)) {
            courseCodes.add(courseCode);
        }
    }

    // Getters for program attributes
    public List<String> getCourseCodes() {
        return courseCodes;
    }

    public String getDepartment() {
        return department;
    }

    public String getDegreeLevel() {
        return degreeLevel;
    }

    public int getRequiredCredits() {
        return requiredCredits;
    }

    // Generates report for the program
    @Override
    public String generateReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("Program Code: ").append(code).append("\n");
        sb.append("Name: ").append(name).append("\n");
        sb.append("Department: ").append(department).append("\n");
        sb.append("Degree Level: ").append(degreeLevel).append("\n");
        sb.append("Required Credits: ").append(requiredCredits).append("\n");

        sb.append("Courses: ");
        if (courseCodes.isEmpty()) {
            sb.append("-\n");
        } else {
            sb.append("{").append(String.join(",", courseCodes)).append("}\n");
        }

        sb.append("\n");
        return sb.toString();
    }
}
