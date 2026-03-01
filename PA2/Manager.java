// Imports for collections, IO handling, and locale-specific formatting
import java.util.*;
import java.io.*;
import java.util.Locale;

public class Manager {
    // Maps to store animals and persons, and an object for food stock management
    private Map<String, Animal> animals;
    private Map<Integer, Person> persons;
    private FoodStock stock;

    // Constructor initializing data structures
    public Manager() {
        animals = new LinkedHashMap<>();
        persons = new LinkedHashMap<>();
        stock = new FoodStock();
    }

    // Loads animal data from file and prints initialization messages
    public void loadAnimals(String fileName, BufferedWriter out) throws IOException {
        out.write("***********************************\n");
        out.write("***Initializing Animal information***\n");

        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            String type = parts[0];
            String name = parts[1];
            int age = Integer.parseInt(parts[2]);

            Animal animal = new Animal(type, name, age);
            animals.put(name, animal);
            out.write("Added new " + type + " with name " + name + " aged " + age + ".\n");
        }

        reader.close();
    }

    // Loads person data (visitors and personnel) from file
    public void loadPersons(String fileName, BufferedWriter out) throws IOException {
        out.write("***********************************\n");
        out.write("***Initializing Visitor and Personnel information***\n");

        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            String role = parts[0];
            String name = parts[1];
            int id = Integer.parseInt(parts[2]);

            Person person = new Person(name, id, role);
            persons.put(id, person);
            out.write("Added new " + role + " with id " + id + " and name " + name + ".\n");
        }

        reader.close();
    }

    // Loads food stock data from file and initializes stock levels
    public void loadFoods(String fileName, BufferedWriter out) throws IOException {
        out.write("***********************************\n");
        out.write("***Initializing Food Stock***\n");

        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            String type = parts[0];
            double amount = Double.parseDouble(parts[1]);

            stock.addFood(type, amount);
            out.write("There are " + String.format(Locale.US, "%.3f", amount) + " kg of " + type + " in stock\n");
        }

        reader.close();
    }

    // Reads and processes commands one by one
    public void executeCommands(String fileName, BufferedWriter out) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;

        while ((line = reader.readLine()) != null) {
            out.write("***********************************\n");
            out.write("***Processing new Command***\n");

            try {
                processCommand(line.trim(), out);
            } catch (Exception e) {
                // Writes error message for command-related exceptions
                out.write(e.getMessage() + "\n");
            }
        }

        reader.close();
    }

    // Handles command parsing and execution
    private void processCommand(String command, BufferedWriter out)
            throws IOException, NoSuchAnimalException, NoSuchPersonException,
            UnauthorizedActionException, NotEnoughFoodException {

        // If it's a stock listing command
        if (command.equalsIgnoreCase("List Food Stock")) {
            out.write("Listing available Food Stock:\n");
            out.write(stock.getFormattedStock());
            return;
        }

        // Parse command details
        String[] parts = command.split(",");
        String action = parts[0];
        int id = Integer.parseInt(parts[1]);
        String animalName = parts[2];

        // Retrieve person by ID
        Person person = persons.get(id);
        if (person == null) {
            throw new NoSuchPersonException("There are no visitors or personnel with the id " + id + ".");
        }

        // Print initial command-related action
        if (action.equalsIgnoreCase("Animal Visitation")) {
            out.write(person.getName() + " tried  to register for a visit to " + animalName + ".\n");
        } else if (action.equalsIgnoreCase("Feed Animal")) {
            out.write(person.getName() + " attempts to feed " + animalName + ".\n");
        } else if (action.equalsIgnoreCase("Clean Habitat")) {
            out.write(person.getName() + " attempts to clean " + animalName + "'s habitat.\n");
        }

        // Retrieve animal by name
        Animal animal = animals.get(animalName);
        if (animal == null) {
            throw new NoSuchAnimalException("There are no animals with the name " + animalName + ".");
        }

        // Handle visit command
        if (action.equalsIgnoreCase("Animal Visitation")) {
            List<String> lines = person.visit(animal);
            lines.remove(0); // skip the line already printed
            for (String s : lines) out.write(s + "\n");

            // Handle feeding command
        } else if (action.equalsIgnoreCase("Feed Animal")) {
            int meals = Integer.parseInt(parts[3]);
            List<String> lines = person.feed(animal, meals, stock);
            if (!lines.isEmpty() && lines.get(0).equals(person.getName() + " attempts to feed " + animal.getName() + ".")) {
                lines.remove(0); // already printed
            }
            for (String s : lines) out.write(s + "\n");

            // Handle cleaning command (currently using visit again)
        } else if (action.equalsIgnoreCase("Clean Habitat")) {
            List<String> lines = person.visit(animal);
            lines.remove(0); // skip repeated line
            for (String s : lines) out.write(s + "\n");

        } else {
            // Invalid command format
            throw new IllegalArgumentException("Invalid command: " + command);
        }
    }
}
