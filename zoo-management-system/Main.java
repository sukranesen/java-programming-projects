import java.io.*;

public class Main {
    public static void main(String[] args) {
        // Expecting 5 arguments: animals.txt persons.txt foods.txt commands.txt output.txt
        if (args.length != 5) {
            System.out.println("Usage: java Main animals.txt persons.txt foods.txt commands.txt output.txt");
            return;
        }

        String animalsFile = args[0];
        String personsFile = args[1];
        String foodsFile = args[2];
        String commandsFile = args[3];
        String outputFile = args[4];

        try {
            // Create output file writer
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

            // Initialize and run manager
            Manager manager = new Manager();
            manager.loadAnimals(animalsFile, writer);
            manager.loadPersons(personsFile, writer);
            manager.loadFoods(foodsFile, writer);
            manager.executeCommands(commandsFile, writer);

            writer.close();

        } catch (IOException e) {
            System.out.println("I/O Error: " + e.getMessage());
        }
    }
}
