/**
 * Abstract class representing a general library item.
 * Extended by Book, Magazine, and DVD classes.
 */
public abstract class LibraryItem {
    protected String id;             // Unique item ID
    protected String title;          // Title of the item
    protected String type;           // Item type (normal, reference, rare, limited)
    protected boolean isAvailable;   // True if item is available to borrow
    protected String borrowedBy;     // User ID of the borrower
    protected String borrowedDate;   // Date the item was borrowed (format: dd/MM/yyyy)

    // Constructor: initializes the item with ID, title, and type
    public LibraryItem(String id, String title, String type) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.isAvailable = true;
        this.borrowedBy = null;
        this.borrowedDate = null;
    }

    // Getters and setters for all fields

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    public String getBorrowedBy() {
        return borrowedBy;
    }

    public void setBorrowedBy(String borrowedBy) {
        this.borrowedBy = borrowedBy;
    }

    public String getBorrowedDate() {
        return borrowedDate;
    }

    public void setBorrowedDate(String borrowedDate) {
        this.borrowedDate = borrowedDate;
    }

    // Abstract method to be implemented in subclasses for formatted info display
    public abstract String getInfo();
}
