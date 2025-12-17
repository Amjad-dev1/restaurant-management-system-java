package src;


import java.util.Scanner;

import components.Accounting;
import components.Menu;

public class Main {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        Menu menu = new Menu();
        menu.loadFromFile("data/menu.txt");
        
        Accounting accounting = new Accounting("data/revenue.txt");

        Servicing servicing = new Servicing(menu, accounting);
        servicing.loadCustomers("data/customers.txt");
        servicing.loadVIPCustomers("data/vip.txt");
        
        
        System.out.println("~");
        System.out.println("Welcome to the Servicing Utility Management System!!");
        System.out.println("Today is: " + servicing.getDayName());
        System.out.println("~");

        boolean running = true;
        while (running) {
            System.out.println("\nMain Menu");
            System.out.println("1- Add Customer");
            System.out.println("2- Add VIP Customer");
            System.out.println("3- Display Menu");
            System.out.println("4- Add Menu Item");
            System.out.println("5- Remove Menu Item");
            System.out.println("6- Put Order");
            System.out.println("7- Serve Next Order");
            System.out.println("8- Display Pending Orders");
            System.out.println("9- Display all customers");
            System.out.println("10- Display VIP customers");
            System.out.println("11- Close Day");
            System.out.println("12- Show Accounting Summary");
            System.out.print("Enter your choice: ");

            int choice = s.nextInt();
            s.nextLine();

            switch (choice) {
            case 1:
                System.out.print("Customer name: ");
                String name = s.nextLine();
                System.out.print("Customer phone: ");
                String phone = s.nextLine();
                servicing.addCustomer(name, phone);
                break;
            case 2:
                System.out.print("VIP name: ");
                name = s.nextLine();
                System.out.print("VIP phone: ");
                phone = s.nextLine();
                System.out.print("VIP discount (%): ");
                double discount = s.nextDouble();
                s.nextLine();
                servicing.addVIPCustomer(name, phone, discount);
                break;
            case 3:
                servicing.displayMenu();
                break;
            case 4:
                System.out.print("Item name: ");
                name = s.nextLine();
                System.out.print("Price: ");
                double price = s.nextDouble();
                s.nextLine();
                System.out.print("Category: ");
                String category = s.nextLine();
                servicing.addMenuItem(name, price, category,"data/menu.txt");
                break;
            case 5:
                System.out.print("Item name to remove: ");
                name = s.nextLine();
                servicing.removeMenuItem(name,"data/menu.txt");
                break;
            case 6:
                System.out.print("Customer phone: ");
                phone = s.nextLine();
                System.out.print("Enter item names (comma separated): ");
                String[] items = s.nextLine().split(",");
                for (int i = 0; i < items.length; i++) items[i] = items[i].trim();
                servicing.placeOrder(phone, items);
                break;
            case 7:
                servicing.serveNextOrder();
                break;
            case 8:
                servicing.displayPendingOrders();
                break;
            case 9:
                servicing.displayAllCustomers();
                break;
            case 10:
                servicing.displayVIPCustomers();
                break;
            case 11:
                servicing.closeDay();
                running = false;
                break;
            case 12:
                servicing.showAccountingSummary();
                break;
            default:
                System.out.println("Invalid choice!");
                break;
            }
        }
        s.close();
    }
}
