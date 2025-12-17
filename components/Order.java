package components;
import dataStructures.LinkedList;

public class Order {
    private Customer customer;
    private LinkedList<MenuItem> items;
    private int vipPriority;
    private int orderNumber;
    private static int counter = 0;
    private double totalPrice;
    private boolean isClosed;

    public Order(Customer customer) {
        this.customer = customer;
        this.items = new LinkedList<>();
        this.totalPrice = 0;
        this.isClosed = false;
        this.vipPriority = customer.isVIP() ? 1 : 0;
        this.orderNumber = ++counter;
    }

    public void addItem(MenuItem item) {
        items.insert(item);
        totalPrice += item.getPrice();
    }

    public boolean removeItem(MenuItem item) {
        boolean removed = items.delete(item);
        if (removed) {
            totalPrice -= item.getPrice();
        }
        return removed;
    }
    
    public double getTotal(int dayOfWeek) {
        double vipDisc = 0;
        double fridayDisc = 0;

        if (customer instanceof VIPCustomer) {
            VIPCustomer vip = (VIPCustomer) customer;
            vipDisc = vip.getDiscountRate();
        }

        if (dayOfWeek == 5) {
            fridayDisc = 20;
        }

        double maxDiscount = Math.max(vipDisc, fridayDisc);

        return totalPrice * (1 - maxDiscount / 100.0);
    }

    public Customer getCustomer() {
        return customer;
    }

    public void closeOrder() {
        isClosed = true;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public int getVipPriority() {
		return vipPriority;
	}

	public void setVipPriority(int vipPriority) {
		this.vipPriority = vipPriority;
	}

	public int getOrderNumber() {
		return orderNumber;
	}


	public void setClosed(boolean isClosed) {
		this.isClosed = isClosed;
	}

	public String toString(int dayOfWeek) {
        String s = "Order by " + customer + ":\n";
        for (int i = 0; i < items.size(); i++) {
            s += " - " + items.get(i) + "\n";
        }
        s += "Total: $" + String.format("%.2f", getTotal(dayOfWeek));
        return s;
    }
	
	public String toString() {
        String s = "Order by " + customer + ":\n";
        for (int i = 0; i < items.size(); i++) {
            s += " - " + items.get(i) + "\n";
        }
        return s;
    }
}
