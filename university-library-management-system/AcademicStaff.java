/**
 * Represents an academic staff user in the system.
 * Inherits from User and applies academic-specific borrowing rules.
 */
public class AcademicStaff extends User {
    private String faculty;     // Faculty name
    private String department;  // Department name
    private String title;       // Title (e.g., Professor, Associate Professor)

    // Constructor: sets academic info along with base user attributes
    public AcademicStaff(String name, String id, String phone, String department, String faculty, String title) {
        super(id, name, phone);
        this.faculty = faculty;
        this.department = department;
        this.title = title;
    }

    // Determines if academic staff can borrow the item
    @Override
    public boolean canBorrow(LibraryItem item) {
        if (penaltyAmount >= 6.0) return false;      // Can't borrow if penalty is 6$ or more
        if (borrowedItems.size() >= 3) return false; // Limit: max 3 borrowed items
        return true; // No restrictions on item types
    }

    // Returns formatted academic staff information for output
    @Override
    public String getInfo() {
        return String.format("------ User Information for %s ------\n" +
                        "Name: %s %s Phone: %s\nFaculty: %s Department: %s\n",
                id, title, name, phone, faculty, department);
    }
}
