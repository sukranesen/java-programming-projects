import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class LibraryManagementSystem {
    ArrayList<LibraryItem> items = new ArrayList<>();
    ArrayList<User> users = new ArrayList<>();

    // Load all items (Book, Magazine, DVD)
    public void loadItems(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String type = parts[0];

                if (type.equals("B")) {
                    items.add(new Book(parts[1], parts[2], parts[3], parts[4], parts[5]));
                } else if (type.equals("M")) {
                    items.add(new Magazine(parts[1], parts[2], parts[3], parts[4], parts[5]));
                } else if (type.equals("D")) {
                    int runtime = Integer.parseInt(parts[5].replaceAll("[^0-9]", ""));
                    items.add(new DVD(parts[1], parts[2], parts[3], parts[4], runtime, parts[6]));
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load all users (Student, Academic, Guest)
    public void loadUsers(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] p = line.split(",");

                if (p[0].equals("S")) {
                    users.add(new Student(p[1], p[2], p[3], p[4], p[5], Integer.parseInt(p[6])));
                } else if (p[0].equals("A")) {
                    users.add(new AcademicStaff(p[1], p[2], p[3], p[4], p[5], p[6]));
                } else if (p[0].equals("G")) {
                    users.add(new GuestUser(p[1], p[2], p[3], p[4]));
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Find user by ID
    public User findUserById(String id) {
        for (User u : users) {
            if (u.getId().equals(id)) {
                return u;
            }
        }
        return null;
    }

    // Find user by name
    public User findUserByName(String name) {
        for (User u : users) {
            if (u.getName().equals(name)) return u;
        }
        return null;
    }

    // Find item by ID
    public LibraryItem findItemById(String id) {
        for (LibraryItem item : items) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    // Process commands from input file and write results to output file
    public void processCommands(String commandsFile, String outputFile) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(commandsFile));
            BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
            String line;

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date today = sdf.parse("25/03/2025");

            while ((line = br.readLine()) != null) {
                String[] cmd = line.split(",");
                String action = cmd[0];

                // Process borrow
                if (action.equals("borrow")) {
                    String userId = cmd[1];
                    String itemId = cmd[2];
                    String borrowDate = cmd[3];

                    User user = findUserById(userId);
                    LibraryItem item = findItemById(itemId);

                    if (user == null || item == null) continue;

                    // Auto-return if item is overdue
                    if (!item.isAvailable()) {
                        String borrowedDateStr = item.getBorrowedDate();
                        if (borrowedDateStr != null) {
                            Date borrowedDate = sdf.parse(borrowedDateStr);
                            Date now = sdf.parse(borrowDate);
                            long days = (now.getTime() - borrowedDate.getTime()) / (1000 * 60 * 60 * 24);

                            User prevUser = findUserByName(item.getBorrowedBy());
                            if (prevUser != null) {
                                int limit = (prevUser instanceof Student) ? 30 : (prevUser instanceof AcademicStaff) ? 15 : 7;
                                if (days > limit) {
                                    prevUser.addPenalty(2.0);
                                }
                                prevUser.removeBorrowedItem(item);
                            }

                            item.setAvailable(true);
                            item.setBorrowedBy(null);
                            item.setBorrowedDate(null);
                        }
                    }

                    if (user.getPenaltyAmount() >= 6.0) {
                        bw.write(user.getName() + " cannot borrow " + item.getTitle()
                                + ", you must first pay the penalty amount! "
                                + (int)user.getPenaltyAmount() + "$");
                    } else if (!item.isAvailable()) {
                        bw.write(user.getName() + " cannot borrow " + item.getTitle() + ", it is not available!");
                    } else if (!user.canBorrow(item)) {
                        if (user instanceof Student && item.getType().equalsIgnoreCase("reference")) {
                            bw.write(user.getName() + " cannot borrow reference item!");
                        } else if (user instanceof GuestUser &&
                                (item.getType().equalsIgnoreCase("rare") || item.getType().equalsIgnoreCase("limited"))) {
                            bw.write(user.getName() + " cannot borrow " + item.getTitle() + ", since the item is restricted!");
                        } else {
                            bw.write(user.getName() + " cannot borrow " + item.getTitle() + ", since the borrow limit has been reached!");
                        }
                    } else {
                        item.setAvailable(false);
                        item.setBorrowedBy(user.getName());
                        item.setBorrowedDate(borrowDate);
                        user.addBorrowedItem(item);
                        bw.write(user.getName() + " successfully borrowed! " + item.getTitle());
                    }

                    bw.newLine();
                }

                // Process return
                else if (action.equals("return")) {
                    String userId = cmd[1];
                    String itemId = cmd[2];

                    User user = findUserById(userId);
                    LibraryItem item = findItemById(itemId);

                    if (user == null || item == null || !user.hasBorrowedItem(item)) continue;

                    Date borrowedDate = sdf.parse(item.getBorrowedDate());
                    long days = (today.getTime() - borrowedDate.getTime()) / (1000 * 60 * 60 * 24);
                    int limit = (user instanceof Student) ? 30 : (user instanceof AcademicStaff) ? 15 : 7;

                    if (days > limit) {
                        user.addPenalty(2.0);
                    }

                    item.setAvailable(true);
                    item.setBorrowedBy(null);
                    item.setBorrowedDate(null);
                    user.removeBorrowedItem(item);

                    bw.write(user.getName() + " successfully returned " + item.getTitle());
                    bw.newLine();
                }

                // Process pay
                else if (action.equals("pay")) {
                    String userId = cmd[1];
                    User user = findUserById(userId);
                    if (user != null) {
                        user.payPenalty();
                        bw.write(user.getName() + " has paid penalty");
                        bw.newLine();
                    }
                }

                // Process displayUsers
                else if (action.equals("displayUsers")) {
                    bw.newLine(); //
                    users.sort(Comparator.comparing(User::getId));
                    for (User u : users) {
                        bw.write(u.getInfo());
                        bw.newLine();
                    }
                }

                // Process displayItems
                else if (action.equals("displayItems")) {
                    items.sort(Comparator.comparing(LibraryItem::getId));
                    for (LibraryItem i : items) {
                        bw.write(i.getInfo());
                        bw.newLine();
                    }
                }
            }

            br.close();
            bw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
