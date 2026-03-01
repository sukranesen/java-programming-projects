// Abstract class for people in the system
public abstract class Person {

    // Common attributes of all people
    protected String id;
    protected String name;
    protected String email;
    protected String department;

    // Constructor to initialize person information
    public Person(String id, String name, String email, String department) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.department = department;
    }

    // Getters for person attributes
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDepartment() {
        return department;
    }

    // Used for report generation by subclasses
    public abstract String getReportSection();
}
