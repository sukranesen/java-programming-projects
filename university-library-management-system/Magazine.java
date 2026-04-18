/**
 * Represents a magazine in the library.
 * Inherits common item properties from LibraryItem and adds magazine-specific details.
 */
public class Magazine extends LibraryItem {
    private String publisher; // Magazine publisher
    private String category;  // Magazine category (e.g. Science, Fashion)

    // Constructor: sets magazine-specific info and calls base constructor
    public Magazine(String id, String title, String publisher, String category, String type) {
        super(id, title, type);
        this.publisher = publisher;
        this.category = category;
    }

    // Returns formatted information of the magazine for display
    @Override
    public String getInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("------ Item Information for ").append(id).append(" ------\n");
        sb.append("ID: ").append(id)
                .append(" Name: ").append(title)
                .append(" Status: ").append(isAvailable ? "Available" :
                        "Borrowed Borrowed Date: " + borrowedDate + " Borrowed by: " + borrowedBy)
                .append("\n");
        sb.append("Publisher: ").append(publisher).append(" Category: ").append(category).append("\n");
        return sb.toString();
    }
}
