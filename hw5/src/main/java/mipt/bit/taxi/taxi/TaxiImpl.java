package mipt.bit.taxi.taxi;

import mipt.bit.taxi.dispatcher.Dispatcher;
import mipt.bit.taxi.order.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class TaxiImpl implements Taxi {
    private final String uuid;
    private final Object lock = new Object();
    private boolean isInterrupted = false;

    private final List<Order> executedOrders = new ArrayList<>();

    private Order currentOrder = null;
    private final Dispatcher dispatcher;

    private final StringBuilder logBuilder = new StringBuilder();

    public TaxiImpl(Dispatcher dispatcher, String uuid) {
        this.uuid = uuid;
        this.dispatcher = dispatcher;
    }

    @Override
    public void run() {
        dispatcher.notifyAvailable(this); // добавляемся к диспетчеру, при первом запуске потока
        while (!isInterrupted) {
            synchronized (lock) {
                try {
                    while (currentOrder == null) {
                        lock.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    isInterrupted = true;
                }
                processOrder();
            }

            dispatcher.notifyAvailable(this);
        }
    }

    @Override
    public void placeOrder(Order order) {
        synchronized (lock) {
            currentOrder = order;
            lock.notify();
        }
    }

    @Override
    public List<Order> getExecutedOrders() {
        return executedOrders;
    }

    private void processOrder() {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 5000));
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }

        synchronized (executedOrders) {
            executedOrders.add(currentOrder);
            logBuilder
                    .append("Processed order: order uuid: ").append(currentOrder.getUuid())
                    .append(", taxi uuid: ").append(uuid)
                    .append(", threadId: ").append(Thread.currentThread().getId())
                    .append(", executedOrders: ").append(executedOrders.size());

            System.out.println(logBuilder);
            logBuilder.setLength(0);

            currentOrder = null;
        }
    }
}
