package mipt.bit.taxi.dispatcher;

import mipt.bit.taxi.order.Order;
import mipt.bit.taxi.taxi.Taxi;

import java.util.List;

public interface Dispatcher {
    void notifyAvailable(Taxi taxi);

    void run();

    void addOrders(List<Order> orders);

    void addOrder(Order order);
}
