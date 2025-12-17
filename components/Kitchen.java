package components;
import dataStructures.LinkedList;
import dataStructures.PriorityQueue;

import java.util.Comparator;
public class Kitchen {
	private LinkedList<Order> servedOrders = new LinkedList<>();
    private PriorityQueue<Order> orderQueue;

    public Kitchen() {
        // higher vipPriority first, then fifo (orderNumber)
    	Comparator<Order> vipComparator = new Comparator<Order>() {
    	    public int compare(Order o1, Order o2) {
    	        if (o1.getVipPriority() != o2.getVipPriority()) {
    	            return Integer.compare(o2.getVipPriority(), o1.getVipPriority()); // VIP first
    	        } else {
    	            return Integer.compare(o1.getOrderNumber(), o2.getOrderNumber()); // FIFO
    	        }
    	    }
    	};
    	
        orderQueue = new PriorityQueue<>(vipComparator);
    }
    
    public boolean hasOrders() {
        return !orderQueue.isEmpty();
    }
    
    public void addOrder(Order o) { //insert
        orderQueue.add(o);
        System.out.println("Order added: " + o.getCustomer());
    }

    public boolean removeOrder(Order order) {
    	return orderQueue.remove(order);
    }

    
    public void serveNextOrder() {
        if (orderQueue.isEmpty()) {
            System.out.println("No pending orders to serve.");
            return;
        }

        Order order = orderQueue.poll();
        order.closeOrder();
        servedOrders.insert(order);

        System.out.println("Serving order for " + order.getCustomer());
        System.out.println(order.toString());
        System.out.println("Order served.\n");
    }

    public void serveAllOrders() {
        while (!orderQueue.isEmpty()) {
            serveNextOrder();
        }
    }

    public void displayPendingOrders() {
        if (orderQueue.isEmpty()) {
            System.out.println("No pending orders.");
            return;
        }
        System.out.println("Pending Orders- Customers are hungry- tell the chefs to hurry up");
        for (Order o : orderQueue) {
            System.out.println(o.toString());
        }
    }
    
    public double getTodayRevenue(int dayOfWeek) {
        double total = 0;
        for (int i = 0; i < servedOrders.size(); i++) {
            Order o = servedOrders.get(i);
            total += o.getTotal(dayOfWeek);
        }
        return total;
    }

}
