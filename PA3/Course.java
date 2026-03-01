import java.util.*;

// This class represents a university course.
// It stores course info, enrolled students, instructor, and grades.
public class Course extends AcademicEntity implements Reportable {

    // Number of credits the course is worth
    private int credits;

    // The semester when the course is offered
    private String semester;

    // Department offering the course
    private String department;

    // Code of the program this course belongs to
    private String programCode;

    // The academic member teaching this course
    private AcademicMember instructor;

    // List of students enrolled in the course
    private List<Student> enrolledStudents;

    // Map storing each student's grade (letter) for this course
    private Map<Student, String> grades;

    // Constructor initializes basic information and creates empty grade/student lists
    public Course(String code, String name, String department, int credits, String semester, String programCode) {
        super(code, name, ""); // Description is not used here
        this.credits = credits;
        this.semester = semester;
        this.department = department;
        this.programCode = programCode;
        this.enrolledStudents = new ArrayList<>();
        this.grades = new HashMap<>();
    }

    // Sets the instructor for the course
    public void setInstructor(AcademicMember instructor) {
        this.instructor = instructor;
    }

    // Returns the instructor of the course
    public AcademicMember getInstructor() {
        return instructor;
    }

    // Adds a student to the course if not already enrolled
    public void enrollStudent(Student student) {
        if (!enrolledStudents.contains(student)) {
            enrolledStudents.add(student);
        }
    }

    // Returns the list of students enrolled in the course
    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    // Adds a grade for a student
    public void addGrade(Student student, String letterGrade) {
        grades.put(student, letterGrade);
    }

    // Computes the number of each letter grade (A1, A2, ..., F3)
    public Map<String, Integer> getGradeDistribution() {
        Map<String, Integer> distribution = new TreeMap<>();
        for (String grade : grades.values()) {
            // Increment the count for each letter grade
            distribution.put(grade, distribution.getOrDefault(grade, 0) + 1);
        }
        return distribution;
    }

    // Calculates the average grade in 4-point scale
    public double getAverageGrade() {
        double total = 0.0;
        int count = 0;
        for (String grade : grades.values()) {
            // Convert letter grade to numeric value
            double point = GradeUtil.getGradePoint(grade);
            if (point >= 0) {
                total += point;
                count++;
            }
        }
        return count == 0 ? 0.0 : total / count;
    }

    // Getter for credits
    public int getCredits() {
        return credits;
    }

    // Getter for semester
    public String getSemester() {
        return semester;
    }

    // Getter for department
    public String getDepartment() {
        return department;
    }

    // Getter for program code
    public String getProgramCode() {
        return programCode;
    }

    // Getter for student grade map
    public Map<Student, String> getGrades() {
        return grades;
    }

    // Generates a detailed report for this course
    @Override
    public String generateReport() {
        StringBuilder sb = new StringBuilder();

        // Basic course info
        sb.append("Course Code: ").append(getCode()).append("\n");
        sb.append("Name: ").append(getName()).append("\n");
        sb.append("Department: ").append(getDepartment()).append("\n");
        sb.append("Credits: ").append(getCredits()).append("\n");
        sb.append("Semester: ").append(getSemester()).append("\n\n");

        // Instructor info (if assigned)
        sb.append("Instructor: ").append(instructor != null ? instructor.getName() : "Not assigned").append("\n\n");

        // List all enrolled students
        sb.append("Enrolled Students:\n");
        for (Student s : enrolledStudents) {
            sb.append("- ").append(s.getName()).append(" (ID: ").append(s.getId()).append(")\n");
        }

        // Grade distribution
        sb.append("\nGrade Distribution:\n");
        Map<String, Integer> dist = getGradeDistribution();
        for (Map.Entry<String, Integer> entry : dist.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        // Print average grade (2 decimal places, dot as separator)
        sb.append(String.format(Locale.ENGLISH,"%nAverage Grade: %.2f%n", getAverageGrade()));

        // End of section formatting
        sb.append("\n----------------------------------------\n");
        sb.append("\n");

        return sb.toString();
    }
}
