import java.util.ArrayList;

/**
 * Abstract class representing a user of the library.
 * Handles common user attributes and methods.
 */
public abstract class User {
    protected String id; // User ID
    protected String name; // User's full name
    protected String phone; // User's phone number
    protected ArrayList<LibraryItem> borrowedItems; // List of borrowed items
    protected double penaltyAmount; // Penalty amount in dollars

    // Constructor: sets ID, name, phone and initializes borrowed items list
    public User(String id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.penaltyAmount = 0.0;
        this.borrowedItems = new ArrayList<>();
    }

    // Returns user ID
    public String getId() {
        return id;
    }

    // Returns user name
    public String getName() {
        return name;
    }

    // Returns total penalty
    public double getPenaltyAmount() {
        return penaltyAmount;
    }

    // Pays (clears) the penalty
    public void payPenalty() {
        penaltyAmount = 0;
    }

    // Adds a penalty to the user
    public void addPenalty(double amount) {
        penaltyAmount += amount;
    }

    // Returns list of items user has borrowed
    public ArrayList<LibraryItem> getBorrowedItems() {
        return borrowedItems;
    }

    // Adds an item to the borrowed list
    public void addBorrowedItem(LibraryItem item) {
        borrowedItems.add(item);
    }

    // Removes an item from the borrowed list
    public void removeBorrowedItem(LibraryItem item) {
        borrowedItems.remove(item);
    }

    // Checks if the user has borrowed this item
    public boolean hasBorrowedItem(LibraryItem item) {
        return borrowedItems.contains(item);
    }

    // Abstract method: defines borrow rules in subclasses
    public abstract boolean canBorrow(LibraryItem item);

    // Abstract method: returns user information
    public abstract String getInfo();
}
