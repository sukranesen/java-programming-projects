/**
 * Represents a DVD in the library.
 * Inherits common item properties from LibraryItem and adds DVD-specific details.
 */
public class DVD extends LibraryItem {
    private String director; // Name of the director
    private String category; // Genre or category (e.g., Drama, Sci-Fi)
    private int runtime;     // Duration of the DVD in minutes

    // Constructor: sets DVD-specific info and calls base constructor
    public DVD(String id, String title, String director, String category, int runtime, String type) {
        super(id, title, type);
        this.director = director;
        this.category = category;
        this.runtime = runtime;
    }

    // Returns formatted information of the DVD for display
    @Override
    public String getInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("------ Item Information for ").append(id).append(" ------\n");
        sb.append("ID: ").append(id)
                .append(" Name: ").append(title)
                .append(" Status: ").append(isAvailable ? "Available" :
                        "Borrowed Borrowed Date: " + borrowedDate + " Borrowed by: " + borrowedBy)
                .append("\n");
        sb.append("Director: ").append(director)
                .append(" Category: ").append(category)
                .append(" Runtime: ").append(runtime).append(" min\n");
        return sb.toString();
    }
}
