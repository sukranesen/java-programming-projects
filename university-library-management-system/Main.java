/**
 * Entry point of the Library Management System.
 * Loads input files and processes all commands.
 */
public class Main {
    public static void main(String[] args) {
        // Check for correct number of command-line arguments
        if (args.length != 4) {
            System.out.println("Usage: java Main <items.txt> <users.txt> <commands.txt> <output.txt>");
            return;
        }

        // Get file paths from arguments
        String itemsFile = args[0];
        String usersFile = args[1];
        String commandsFile = args[2];
        String outputFile = args[3];

        // Create and set up the system
        LibraryManagementSystem lms = new LibraryManagementSystem();

        // Load data from input files
        lms.loadItems(itemsFile);
        lms.loadUsers(usersFile);

        // Process all commands and write output
        lms.processCommands(commandsFile, outputFile);
    }
}
