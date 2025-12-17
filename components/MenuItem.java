package components;
import java.io.Serializable;

public class MenuItem implements Serializable {
    private String name;
    private double price;
    private String category;

    public MenuItem(String name, double price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof MenuItem)) return false;
        MenuItem other = (MenuItem) obj;
        return this.name.equalsIgnoreCase(other.name);
    }

    public String toString() {
        return name + " - $" + String.format("%.2f", price) + " ("+ category +")"; //formats the price to  show the first 2 decimal digits
    }
}
