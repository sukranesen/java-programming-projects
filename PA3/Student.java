import java.util.*;

// Represents a student who can enroll in and complete courses
public class Student extends Person implements Reportable {

    private String status; // Always set to "Active"
    private List<Course> enrolledCourses;              // Courses the student is currently enrolled in
    private Map<Course, String> completedCourses;      // Completed courses and received grades

    // Constructor initializes student info and empty course lists
    public Student(String id, String name, String email, String department) {
        super(id, name, email, department);
        this.status = "Active";
        this.enrolledCourses = new ArrayList<>();
        this.completedCourses = new LinkedHashMap<>();
    }

    // Adds a course to enrolled list if not already added
    public void enrollInCourse(Course course) {
        if (!enrolledCourses.contains(course)) {
            enrolledCourses.add(course);
        }
    }

    // Moves a course from enrolled to completed with a grade
    public void completeCourse(Course course, String grade) {
        if (!completedCourses.containsKey(course)) {
            enrolledCourses.remove(course);
            completedCourses.put(course, grade);
        }
    }

    // Getters for enrolled and completed courses
    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public Map<Course, String> getCompletedCourses() {
        return completedCourses;
    }

    // Calculates GPA using 4-point grading scale
    public double calculateGPA() {
        double total = 0.0;
        int creditSum = 0;

        for (Map.Entry<Course, String> entry : completedCourses.entrySet()) {
            double gradePoint = GradeUtil.getGradePoint(entry.getValue());
            if (gradePoint >= 0) {
                total += gradePoint * entry.getKey().getCredits();
                creditSum += entry.getKey().getCredits();
            }
        }

        return creditSum == 0 ? 0.0 : total / creditSum;
    }

    // Used for polymorphic reporting section title (not actually used in this case)
    @Override
    public String getReportSection() {
        return "STUDENT REPORT";
    }

    // Generates a short student info listing
    public String studentList() {
        StringBuilder s = new StringBuilder();
        s.append("Student ID: ").append(id).append("\n");
        s.append("Name: ").append(name).append("\n");
        s.append("Email: ").append(email).append("\n");
        s.append("Major: ").append(department).append("\n");
        s.append("Status: ").append(status).append("\n\n");
        return s.toString();
    }

    // Generates a full detailed report for the student
    @Override
    public String generateReport() {
        StringBuilder sb = new StringBuilder();

        // Basic student info
        sb.append("Student ID: ").append(id).append("\n");
        sb.append("Name: ").append(name).append("\n");
        sb.append("Email: ").append(email).append("\n");
        sb.append("Major: ").append(department).append("\n");
        sb.append("Status: ").append(status).append("\n\n\n");

        // Enrolled courses list
        sb.append("Enrolled Courses:\n");
        for (Course c : enrolledCourses) {
            sb.append("- ").append(c.getName()).append(" (").append(c.getCode()).append(")\n");
        }

        // Completed courses and grades
        sb.append("\nCompleted Courses:\n");
        for (Map.Entry<Course, String> entry : completedCourses.entrySet()) {
            sb.append("- ").append(entry.getKey().getName())
                    .append(" (").append(entry.getKey().getCode()).append("): ")
                    .append(entry.getValue()).append("\n");
        }

        // GPA calculation result
        sb.append("\nGPA: ").append(String.format(Locale.ENGLISH,"%.2f", calculateGPA())).append("\n");
        sb.append("----------------------------------------\n");

        return sb.toString();
    }
}
