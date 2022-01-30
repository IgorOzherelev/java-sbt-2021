package mipt.bit.taxi.taxi;

import mipt.bit.taxi.order.Order;

import java.util.List;

public interface Taxi {
    void run();

    void placeOrder(Order order);

    List<Order> getExecutedOrders();
}
