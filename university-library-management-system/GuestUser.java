/**
 * Represents a guest user in the system.
 * Guest users have the strictest borrowing limits and restrictions.
 */
public class GuestUser extends User {
    private String occupation; // Guest user's occupation (e.g., Writer, Researcher)

    // Constructor: sets name, id, phone, and occupation
    public GuestUser(String name, String id, String phone, String occupation) {
        super(id, name, phone);
        this.occupation = occupation;
    }

    // Determines if guest user can borrow the item
    @Override
    public boolean canBorrow(LibraryItem item) {
        if (penaltyAmount >= 6.0) return false;      // Cannot borrow if penalty is 6$ or more
        if (borrowedItems.size() >= 1) return false; // Can only borrow 1 item max
        if (item.getType().equalsIgnoreCase("rare") || item.getType().equalsIgnoreCase("limited"))
            return false; // Cannot borrow rare or limited items
        return true; // Borrow allowed
    }

    // Returns formatted guest user information for output
    @Override
    public String getInfo() {
        return String.format("------ User Information for %s ------\n" +
                        "Name: %s Phone: %s\nOccupation: %s\n",
                id, name, phone, occupation);
    }
}
