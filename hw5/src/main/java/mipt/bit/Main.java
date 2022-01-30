package mipt.bit;

import mipt.bit.taxi.dispatcher.Dispatcher;
import mipt.bit.taxi.dispatcher.DispatcherImpl;
import mipt.bit.taxi.order.Order;
import mipt.bit.taxi.taxi.Taxi;
import mipt.bit.taxi.taxi.TaxiImpl;

import java.util.ArrayList;
import java.util.List;


public class Main {
    private final static int TAXIS_NUM = 100;
    private final static int ORDERS_NUM = 300;

    public static void main(String[] args) {
        Dispatcher dispatcher = new DispatcherImpl();
        dispatcher.addOrders(createOrders());

        startTaxis(createTaxis(dispatcher));

        Thread threadDispatcher = new Thread(dispatcher::run);
        threadDispatcher.start();
    }

    private static List<Order> createOrders() {
        List<Order> orders = new ArrayList<>();

        for (int i = 0; i < ORDERS_NUM; i++) {
            orders.add(new Order(
               String.valueOf(i),
               "location" + i,
               "destination" + i
            ));
        }

        return orders;
    }

    private static List<Taxi> createTaxis(Dispatcher dispatcher) {
        Taxi taxi;
        List<Taxi> taxis = new ArrayList<>();
        for (int i = 0; i < TAXIS_NUM; i++) {
            taxi = new TaxiImpl(dispatcher, String.valueOf(i));
            taxis.add(taxi);
        }

        return taxis;
    }

    private static void startTaxis(List<Taxi> taxis) {
        Thread[] taxiThreads = new Thread[TAXIS_NUM];
        for (int i = 0; i < TAXIS_NUM; i++) {
            taxiThreads[i] = new Thread(taxis.get(i)::run);
            taxiThreads[i].start();
        }
    }
}
