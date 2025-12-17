package components;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

import dataStructures.LinkedList;

public class Customer implements Serializable {
    private String name;
    private String phoneNumber;
    private boolean vip;

    public Customer(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.vip = false;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean isVIP() {
        return vip;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setVIP(boolean vip) {
        this.vip = vip;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Customer)) return false;
        Customer other = (Customer) obj;
        return this.name.equalsIgnoreCase(other.name) &&
               this.phoneNumber.equals(other.phoneNumber);
    }

    public int hashCode() {
        return (name.toLowerCase() + phoneNumber).hashCode();
    }

    public String toString() {
        return (vip ? "[VIP] " : "") + name + " (" + phoneNumber + ")";
    }
    
    public void saveToFile(String filePath) {
        try (FileWriter fw = new FileWriter(filePath, true)) {
            fw.write(name + "," + phoneNumber + "\n");
        } catch (IOException e) {
            System.out.println("Error saving customer to file: " + e.getMessage());
        }
    }

    public static LinkedList<Customer> loadFromFile(String filePath) {
        LinkedList<Customer> list = new LinkedList<>();
        File file = new File(filePath);
        if (!file.exists()) return list;

        try (Scanner s = new Scanner(file)) {
            while (s.hasNextLine()) {
                String[] parts = s.nextLine().split(",");
                if (parts.length >= 2) {
                    String name = parts[0].trim();
                    String phone = parts[1].trim();
                    list.insert(new Customer(name, phone));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading customers from file: " + e.getMessage());
        }

        return list;
    }
    
}
