package mipt.bit.utils;

import mipt.bit.garage.Car;
import mipt.bit.garage.Owner;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class GarageUtils {
    public static void checkNotNull(String name, Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Provided null " + name + " object");
        }
    }

    public static void checkCar(Car car) {
        checkNotNull("car", car);
        String carBrand = car.getBrand();
        String carModelName = car.getModelName();

        if (carBrand == null || carBrand.isEmpty()) {
            throw new IllegalArgumentException("Provided car has wrong parameters "
                    + "carBrand: " + carBrand + " carModelName: " + carModelName);
        }
    }

    public static void checkCarOwner(Car car, Owner owner) {
        checkNotNull("car", car);
        checkNotNull("owner", owner);

        long carOwnerId = car.getOwnerId();
        long ownerId = owner.getOwnerId();
        if (carOwnerId != ownerId) {
            throw new IllegalArgumentException("Provided car belongs to another owner "
                    + "car.getOwnerId(): " + carOwnerId
                    + " ownerId: " + ownerId);
        }
    }

    public static <K, V> void addToMapSet(Map<K, Set<V>> map, K key, V value) {
        Set<V> set = map.get(key);
        if (map.get(key) == null) {
            set = new HashSet<>();
            set.add(value);
            map.put(key, set);
        } else {
            set.add(value);
        }
    }

    public static <K> void removeCarFromMapSet(Map<K, Set<Car>> map, K key, Long carId) {
        Set<Car> cars = map.get(key);
        cars.remove(new Car(carId));

        if (map.get(key) == null || map.get(key).isEmpty()) {
            map.remove(key);
        }
    }
}
