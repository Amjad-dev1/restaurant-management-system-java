package components;
import dataStructures.LinkedList;
import java.io.*;
import java.util.Scanner;

public class Menu {
    private LinkedList<MenuItem> items = new LinkedList<>();

    public void addItem(MenuItem item) {
        items.insert(item);
    }

    public void removeItem(String name) {
        MenuItem rtool = new MenuItem(name, 0, "");
        items.delete(rtool);
    }

    public MenuItem findItem(String name) {
        MenuItem rtool = new MenuItem(name, 0, "");
        int index = items.search(rtool);
        if (index != -1) {
            return items.get(index);
        }
        return null;
    }


    public void displayMenu() {
        System.out.println("Our delicious MENU");
        items.display();
    }

    //this section deals with saving the menuItems in a file and retrieving them, both operations dealing with items as object(serialization)
    public void loadFromFile(String path) {
        File file = new File(path);
        if (!file.exists()) return;

        try (Scanner s = new Scanner(file)) {
            while (s.hasNextLine()) {
                String line = s.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0].trim();
                    double price = Double.parseDouble(parts[1].trim());
                    String category = parts[2].trim();
                    MenuItem q =new MenuItem(name, price, category);
                    addItem(q);
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading menu file: " + e.getMessage());
        }
    }

    public void saveToFile(String path) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(path))) {
            for (int i = 0; i < items.size(); i++) {
                MenuItem item = items.get(i);
                writer.println(item.getName() + "," + item.getPrice() + "," + item.getCategory());
            }
        } catch (IOException e) {
            System.out.println("Error saving menu: " + e.getMessage());
        }
    }
}