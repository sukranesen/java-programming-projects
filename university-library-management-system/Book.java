/**
 * Represents a book in the library.
 * Inherits common item properties from LibraryItem and adds book-specific details.
 */
public class Book extends LibraryItem {
    private String author;  // Book author
    private String genre;   // Book genre

    // Constructor: sets book-specific info and calls base constructor
    public Book(String id, String title, String author, String genre, String type) {
        super(id, title, type);
        this.author = author;
        this.genre = genre;
    }

    // Returns formatted information of the book for display
    @Override
    public String getInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("------ Item Information for ").append(id).append(" ------\n");
        sb.append("ID: ").append(id)
                .append(" Name: ").append(title)
                .append(" Status: ").append(isAvailable ? "Available" :
                        "Borrowed Borrowed Date: " + borrowedDate + " Borrowed by: " + borrowedBy)
                .append("\n");
        sb.append("Author: ").append(author).append(" Genre: ").append(genre).append("\n");
        return sb.toString();
    }
}
