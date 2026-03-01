import java.util.*;

// This class represents an academic staff member who teaches courses.
// It extends Person and implements Reportable interface to generate reports.
public class AcademicMember extends Person implements Reportable {

    // List of courses that this academic member teaches
    private List<Course> taughtCourses;

    // Constructor initializes basic person info and empty course list
    public AcademicMember(String id, String name, String email, String department) {
        super(id, name, email, department);
        this.taughtCourses = new ArrayList<>();
    }

    // Adds a course to the list of taught courses
    public void assignCourse(Course course) {
        taughtCourses.add(course);
    }

    // Returns the list of courses taught by this academic member
    public List<Course> getTaughtCourses() {
        return taughtCourses;
    }

    // Returns the section header name for report generation
    @Override
    public String getReportSection() {
        return "ACADEMIC MEMBERS";
    }

    // Builds and returns the academic member’s report information as a string
    @Override
    public String generateReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("Faculty ID: ").append(id).append("\n");               // Print ID
        sb.append("Name: ").append(name).append("\n");                   // Print full name
        sb.append("Email: ").append(email).append("\n");                 // Print email address
        sb.append("Department: ").append(department).append("\n");       // Print academic department
        sb.append("\n"); // Add extra line for spacing between entries
        return sb.toString();
    }
}
