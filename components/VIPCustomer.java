package components;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import dataStructures.LinkedList;

public class VIPCustomer extends Customer {
    private double discountRate;

    public VIPCustomer(String name,String phoneNumber, double discountRate) {
        super(name,phoneNumber);
        setVIP(true);
        this.discountRate = discountRate;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }

    public String toString() {
        return "[VIP " + getName() + " (" + getPhoneNumber() + ")"+ " - " + discountRate + "% off]";
    }
    
    

    public void saveToFile(String filePath) {
        try (FileWriter fw = new FileWriter(filePath, true)) {
            fw.write(getName() + "," + getPhoneNumber() + "," + discountRate + "\n");
        } catch (IOException e) {
            System.out.println("Error writing VIP file: " + e.getMessage());
        }
    }

    public static LinkedList<Customer> loadFromFile(String filePath) {
        LinkedList<Customer> list = new LinkedList<>();
        File file = new File(filePath);
        if (!file.exists()) return list;

        try (Scanner s = new Scanner(file)) {
            while (s.hasNextLine()) {
                String line = s.nextLine().trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0].trim();
                    String phone = parts[1].trim();
                    double discount = Double.parseDouble(parts[2].trim());
                    VIPCustomer vip = new VIPCustomer(name, phone, discount);
                    list.insert(vip);
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading VIP file: " + e.getMessage());
        }

        return list;
    }
    
    
    
}
