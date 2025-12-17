package components;
import java.time.LocalDate;


import dataStructures.LinkedList;

public class Accounting {

    private LinkedList<DailyRevenue> revenues;
    private String revenueFile;

    public Accounting(String revenueFile) {
        this.revenueFile = revenueFile;
        this.revenues = myUtils.loadRevenues(revenueFile);
    }

    public void closeDay(double todayRevenue, LocalDate date) {
        DailyRevenue today = myUtils.closeDay(todayRevenue, revenueFile, revenues, date);
        System.out.println("Day closed: " + today.getDate() + ", Revenue: $" + today.getRevenue());
    }

    public void showSummary() {
        myUtils.showAccountingSummary(revenues);
    }

    public double getRevenueByDate(String date) {
        for (int i = 0; i < revenues.size(); i++) {
            DailyRevenue day = revenues.get(i);
            if (day.getDate().equals(date)) {
                return day.getRevenue();
            }
        }
        return 0;
    }

    public double getTotalRevenue() {
        double total = 0;
        for (int i = 0; i < revenues.size(); i++) {
            total += revenues.get(i).getRevenue();
        }
        return total;
    }
    
    public LinkedList<DailyRevenue> getRevenuesList() {
        return revenues;
    }
}
