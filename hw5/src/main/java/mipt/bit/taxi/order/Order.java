package mipt.bit.taxi.order;

import java.util.Objects;

public class Order {
    private final String uuid;
    private String location;
    private String destination;

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getUuid() {
        return uuid;
    }

    public String getLocation() {
        return location;
    }

    public String getDestination() {
        return destination;
    }

    public Order(String uuid, String location, String destination) {
        this.uuid = uuid;
        this.destination = destination;
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        assert uuid != null;
        return uuid.equals(order.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        return "Order{" +
                "uuid='" + uuid + '\'' +
                ", currentLocation='" + location + '\'' +
                ", destination='" + destination + '\'' +
                '}';
    }
}
