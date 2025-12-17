package components;

import java.io.*;
import java.time.LocalDate;
import java.util.Scanner;

import dataStructures.LinkedList;

public class myUtils {

    //save daily revenue to the text file
    public static void saveDailyRevenue(DailyRevenue day, String filePath) {
        try (FileWriter fw = new FileWriter(filePath, true)) {
            fw.write(day.toString() + "\n");
        } catch (IOException e) {
            System.out.println("Error writing revenue file: " + e.getMessage());
        }
    }

    //load all daily revenues from file into a linked list
    public static LinkedList<DailyRevenue> loadRevenues(String filePath) {
        LinkedList<DailyRevenue> revenues = new LinkedList<>();
        File file = new File(filePath);
        if (!file.exists()) return revenues;
        try (Scanner s = new Scanner(file)) {
            while (s.hasNextLine()) {
                String[] parts = s.nextLine().split(",");
                if (parts.length == 2) {
                    String date = parts[0].trim();
                    double revenue = Double.parseDouble(parts[1].trim());
                    revenues.insert(new DailyRevenue(date, revenue));
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading revenue file: " + e.getMessage());
        }

        return revenues;
    }

    // get total revenue for all days, best, and worst selling day
    public static void showAccountingSummary(LinkedList<DailyRevenue> revenues) {
        if (revenues.size() == 0) {
            System.out.println("No revenue data found.");
            return;
        }

        DailyRevenue bestDay = revenues.get(0); //use the algorithm to find max and min from the linked list
        DailyRevenue worstDay = revenues.get(0);
        double total = 0;

        for (int i = 0; i < revenues.size(); i++) {
            DailyRevenue day = revenues.get(i);
            total += day.getRevenue();
            if (day.getRevenue() > bestDay.getRevenue()) bestDay = day;
            if (day.getRevenue() < worstDay.getRevenue()) worstDay = day;
        }

        System.out.println("Accounting Summary");
        System.out.println("Total Revenue: $" + String.format("%.2f", total));
        System.out.println("Best Selling Day: " + bestDay.getDate() + " ($" + bestDay.getRevenue() + ")");
        System.out.println("Lowest Selling Day: " + worstDay.getDate() + " ($" + worstDay.getRevenue() + ")");
    }

    // close up current day,sum revenue, add to file and add a new day
    public static DailyRevenue closeDay(double todayRevenue, String filePath, LinkedList<DailyRevenue> revenues, LocalDate date) {
        DailyRevenue today = new DailyRevenue(date.toString(), todayRevenue);
        revenues.insert(today);
        saveDailyRevenue(today, filePath);
        return today;
    }

}
