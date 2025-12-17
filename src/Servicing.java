package src;

import java.time.LocalDate;

import components.Accounting;
import components.Customer;
import components.DailyRevenue;
import components.Kitchen;
import components.Menu;
import components.MenuItem;
import components.Order;
import components.VIPCustomer;
import dataStructures.LinkedList;
import dataStructures.Stack;

public class Servicing {

    private Menu menu;
    Stack<Order> orderHistory = new Stack<>(); //in case an order is cancelled
    private Kitchen kitchen;
    private Accounting accounting;
    private LinkedList<Customer> customers;
    private int dayOfWeek; 

    public Servicing(Menu menu, Accounting accounting) {
        this.menu = menu;
        this.kitchen = new Kitchen();
        this.accounting = accounting;
        this.customers = new LinkedList<>();

        LinkedList<DailyRevenue> revenues = accounting.getRevenuesList(); //set dayofweek which is used to determine friday discount
        if (revenues.size() > 0) {
            LocalDate lastDate = LocalDate.parse(revenues.get(revenues.size() - 1).getDate());
            this.dayOfWeek = lastDate.getDayOfWeek().getValue(); // monday=1, tuesday=2 .. etc
        } else {
            this.dayOfWeek = 1; // default is monday
        }
    }

    public void addCustomer(String name, String phone) {
        Customer c = new Customer(name, phone);
        customers.insert(c);
        System.out.println("Customer added: " + c);
        c.saveToFile("data/customers.txt");
    }

    public void addVIPCustomer(String name, String phone, double discount) {
        VIPCustomer vip = new VIPCustomer(name, phone, discount);
        customers.insert(vip);
        System.out.println("VIP customer added: " + vip);
        vip.saveToFile("data/vip.txt");
    }

    public Customer findCustomer(String phone) {
        for (int i = 0; i < customers.size(); i++) {
            Customer c = customers.get(i);
            if (c.getPhoneNumber().equals(phone)) return c;
        }
        return null;
    }

    public void displayMenu() {
        menu.displayMenu();
    }

    public void addMenuItem(String name, double price, String category, String filePath) {
        MenuItem item = new MenuItem(name, price, category);
        menu.addItem(item);
        menu.saveToFile(filePath);
        System.out.println("Menu item added: " + item);
    }

    public void removeMenuItem(String name, String filePath) {
        menu.removeItem(name);
        menu.saveToFile(filePath);
        System.out.println("Menu item removed: " + name);
    }

    public void placeOrder(String customerPhone, String[] itemNames) {
        Customer c = findCustomer(customerPhone);
        if (c == null) {
            System.out.println("Customer not found!");
            return;
        }
        Order order = new Order(c);
        for (String name : itemNames) {
            MenuItem item = menu.findItem(name);
            if (item != null) order.addItem(item);
        }
        kitchen.addOrder(order);
        orderHistory.push(order);
        System.out.println("Order placed for " + c.getName());
    }

    public void cancelLastOrder() {
        if (orderHistory.isEmpty()) {
            System.out.println("No order to undo.");
            return;
        }
        Order lastOrder = orderHistory.pop();
        if(kitchen.removeOrder(lastOrder))System.out.println("Last order cancelled for " + lastOrder.getCustomer());
        else System.out.println("An error my have occured");
        
    }


    public void serveNextOrder() {
        kitchen.serveNextOrder();
    }

    public void displayPendingOrders() {
        kitchen.displayPendingOrders();
    }

    public void closeDay() {
        LocalDate lastDate;
        LinkedList<DailyRevenue> revenues = accounting.getRevenuesList();
        System.out.println("Thank you everyone for today's effort on " + getDayName() + "!");
        if (revenues.size() > 0) {
            lastDate = LocalDate.parse(revenues.get(revenues.size() - 1).getDate()).plusDays(1);
        } else {
            lastDate = LocalDate.now();
        }
        kitchen.serveAllOrders();
        accounting.closeDay(calculateTodayRevenue(), lastDate);
        dayOfWeek = lastDate.getDayOfWeek().getValue();
    }

    public void showAccountingSummary() {
        accounting.showSummary();
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public String getDayName() {
        switch(dayOfWeek) {
            case 1: return "Monday";
            case 2: return "Tuesday";
            case 3: return "Wednesday";
            case 4: return "Thursday";
            case 5: return "Friday";
            case 6: return "Saturday";
            case 7: return "Sunday";
            default: return "Unknown";
        }
    }
    
    public double calculateTodayRevenue() {
        return kitchen.getTodayRevenue(dayOfWeek);
    }
    
    public void loadCustomers(String filePath) {
        LinkedList<Customer> list = Customer.loadFromFile(filePath);
        for (int i = 0; i < list.size(); i++) {
            customers.insert(list.get(i));
        }
    }

    public void loadVIPCustomers(String filePath) {
        LinkedList<Customer> list = VIPCustomer.loadFromFile(filePath);
        for (int i = 0; i < list.size(); i++) {
            customers.insert(list.get(i));
        }
    }
    
    public void displayAllCustomers() {
        System.out.println("All Customers:");
        customers.display();
    }

    public void displayVIPCustomers() {
        System.out.println("VIP Customers:");
        for (int i = 0; i < customers.getSize(); i++) {
            Customer c = customers.get(i);
            if (c instanceof VIPCustomer) {
                System.out.println(c);
            }
        }
    }
}
