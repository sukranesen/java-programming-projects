/**
 * Represents a student user in the system.
 * Inherits common user behavior and defines student-specific rules.
 */
public class Student extends User {
    private String faculty;     // Faculty name
    private String department;  // Department name
    private int grade;          // Grade level (e.g. 1, 2, 3...)

    // Constructor: sets name, id, phone, department, faculty and grade
    public Student(String name, String id, String phone, String department, String faculty, int grade) {
        super(id, name, phone);
        this.faculty = faculty;
        this.department = department;
        this.grade = grade;
    }

    // Determines if the student is allowed to borrow the item
    @Override
    public boolean canBorrow(LibraryItem item) {
        if (penaltyAmount >= 6.0) return false;              // Can't borrow if penalty is 6$ or more
        if (borrowedItems.size() >= 5) return false;         // Can't borrow more than 5 items
        if (item.getType().equalsIgnoreCase("reference"))    // Can't borrow reference items
            return false;
        return true; // Borrow allowed
    }

    // Returns formatted student information for output
    @Override
    public String getInfo() {
        return String.format("------ User Information for %s ------\n" +
                        "Name: %s Phone: %s\nFaculty: %s Department: %s Grade: %dth\n",
                id, name, phone, faculty, department, grade);
    }
}
