// This abstract class represents a base for Department, Program, and Course.
// It defines common fields and methods for all academic entities.
public abstract class AcademicEntity {

    // Unique code for the entity (e.g., department code, course code)
    protected String code;

    // Name of the academic entity
    protected String name;

    // Description of the entity (can explain purpose or details)
    protected String description;

    // Constructor sets code, name, and description for the entity
    public AcademicEntity(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    // Returns the code of the entity
    public String getCode() {
        return code;
    }

    // Returns the name of the entity
    public String getName() {
        return name;
    }

    // Returns the description of the entity
    public String getDescription() {
        return description;
    }
}
