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
    private boolean isInterrupted = false;

    @Override
    public void notifyAvailable(Taxi taxi) {
        synchronized (availableTaxis) {
            availableTaxis.add(taxi);
            availableTaxis.notify();
        }
    }

    @Override
    public void run() {
        while (!isInterrupted) {
            synchronized (availableTaxis) {
                while (availableTaxis.isEmpty() || orders.isEmpty()) {
                    try {
                        availableTaxis.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        isInterrupted = true;
                    }
                }
            }

            processOrder();
        }
    }

    private void processOrder() {
        synchronized (orders) {
            synchronized (availableTaxis) {
                if (!orders.isEmpty() && !availableTaxis.isEmpty()) {
                    Taxi tmpTaxi;
                    tmpTaxi = availableTaxis.pop();
                    tmpTaxi.placeOrder(orders.pop());
                }
            }
        }
    }

    @Override
    public void addOrders(List<Order> orders) {
        synchronized (this.orders) {
            this.orders.addAll(new ArrayList<>(orders));
        }
    }

    @Override
    public void addOrder(Order order) {
        synchronized (this.orders) {
            this.orders.add(order);
        }
    }
}
