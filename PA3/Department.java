// Represents a university department with a head academic member
public class Department extends AcademicEntity {

    // Head of the department
    private AcademicMember head;

    // Constructor sets code, name, and description
    public Department(String code, String name, String description) {
        super(code, name, description);
    }

    // Sets the head of the department
    public void setHead(AcademicMember head) {
        this.head = head;
    }

    // Returns the head of the department
    public AcademicMember getHead() {
        return head;
    }
}
