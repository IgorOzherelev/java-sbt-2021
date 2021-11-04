package mipt.bit.taxi.dispatcher;

import mipt.bit.taxi.order.Order;
import mipt.bit.taxi.taxi.Taxi;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class DispatcherImpl implements Dispatcher {
    private final Deque<Taxi> availableTaxis = new ArrayDeque<>();
    private final Deque<Order> orders = new ArrayDeque<>();

    @Override
    public void notifyAvailable(Taxi taxi) {
        synchronized (availableTaxis) {
            availableTaxis.add(taxi);
            availableTaxis.notify();
        }
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (availableTaxis) {
                if (availableTaxis.isEmpty() || orders.isEmpty()) {
                    try {
                        availableTaxis.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                }
            }

            processOrder();
        }
    }

    private void processOrder() {
        // можно не делать синхронизированным, тк гарантированно используется в одном потоке
        if (!orders.isEmpty() && !availableTaxis.isEmpty()) {
            Taxi tmpTaxi;
            tmpTaxi = availableTaxis.pop();
            tmpTaxi.placeOrder(orders.pop());
        }
    }

    @Override
    public void addOrders(List<Order> orders) {
        this.orders.addAll(new ArrayList<>(orders));
    }

    @Override
    public void addOrder(Order order) {
        this.orders.add(order);
    }
}
