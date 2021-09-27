package mipt.bit.garage;

import java.util.*;

import static mipt.bit.utils.Utils.checkNotNull;

public class GarageImpl implements Garage {
    private final Map<String, Set<Car>> brandToCars = new HashMap<>();
    private final Map<Owner, Set<Car>> ownerToCars = new HashMap<>();

    private final Map<Long, Car> carIdToCar = new HashMap<>();
    private final TreeSet<Car> carsOrderedByPower = new TreeSet<>(Comparator.comparing(Car::getPower).thenComparing(Car::getCarId));

    private final Comparator<Car> carMaxVelocityComparator = Comparator.comparing(Car::getMaxVelocity).thenComparing(Car::getCarId);
    private final TreeSet<Car> carsOrderedByMaxVelocity = new TreeSet<>(carMaxVelocityComparator);

    @Override
    public Set<Owner> allCarsUniqueOwners() {
        return ownerToCars.keySet();
    }

    /**
     * Complexity should be less than O(n)
     */
    @Override
    public TreeSet<Car> topThreeCarsByMaxVelocity() {
        TreeSet<Car> cars = new TreeSet<>(carMaxVelocityComparator);
        Car tmp;
        for (int i = 0; i < 3; i++) {
            tmp = carsOrderedByMaxVelocity.pollLast();
            cars.add(tmp);
        }
        carsOrderedByMaxVelocity.addAll(cars);
        return cars;
    }

    /**
     * Complexity should be O(1)
     */
    @Override
    public Set<Car> allCarsOfBrand(String brand) {
        return brandToCars.get(brand);
    }

    /**
     * Complexity should be less than O(n)
     */
    @Override
    public Set<Car> carsWithPowerMoreThan(int power) {
        return carsOrderedByPower.tailSet(new Car(power));
    }

    /**
     * Complexity should be O(1)
     */
    @Override
    public Set<Car> allCarsOfOwner(Owner owner) {
        return ownerToCars.get(owner);
    }

    /**
     * @return mean value of owner age that has cars with given brand
     */
    @Override
    public int meanOwnersAgeOfCarBrand(String brand) {
        int totalAge = 0;

        Set<Car> carsWithSameBrand = brandToCars.get(brand);
        Set<Owner> allOwners = ownerToCars.keySet();
        Set<Long> ownersIds = new HashSet<>();

        if (carsWithSameBrand == null || carsWithSameBrand.isEmpty()) {
            return 0;
        }

        for (Car car : carsWithSameBrand) {
            ownersIds.add(car.getOwnerId());
        }

        for (Owner owner : allOwners) {
            if (ownersIds.contains(owner.getOwnerId())) {
                totalAge += owner.getAge();
            }
        }

        return totalAge / ownersIds.size();
    }

    /**
     * @return mean value of cars for all owners
     */
    @Override
    public int meanCarNumberForEachOwner() {
        int totalOwnersNumber = ownerToCars.keySet().size();
        if (totalOwnersNumber != 0)
            return carIdToCar.size() / totalOwnersNumber;
        return 0;
    }

    /**
     * Complexity should be less than O(n)
     * @return removed car
     */
    @Override
    public Car removeCar(long carId) {
        String carBrand = carIdToCar.get(carId).getBrand();
        long ownerId = carIdToCar.get(carId).getOwnerId();

        removeCarFromMapSet(brandToCars, carBrand, carId);
        removeCarFromMapSet(ownerToCars, new Owner(ownerId), carId);

        Car car = carIdToCar.get(carId);
        carsOrderedByPower.remove(car);
        carsOrderedByMaxVelocity.remove(car);

        return carIdToCar.remove(carId);
    }

    /**
     * Complexity should be less than O(n)
     */
    @Override
    public void addCar(Car car, Owner owner) {
        checkCar(car);
        checkCarOwner(car, owner);

        addToMapSet(brandToCars, car.getBrand(), car);
        addToMapSet(ownerToCars, owner, car);

        carIdToCar.put(car.getCarId(), car);
        carsOrderedByPower.add(car);
        carsOrderedByMaxVelocity.add(car);
    }

    public void clear() {
        brandToCars.clear();
        ownerToCars.clear();

        carIdToCar.clear();
        carsOrderedByPower.clear();
        carsOrderedByMaxVelocity.clear();
    }

    public Map<String, Set<Car>> getBrandToCars() {
        return brandToCars;
    }

    public Map<Owner, Set<Car>> getOwnerToCars() {
        return ownerToCars;
    }

    public Map<Long, Car> getCarIdToCar() {
        return carIdToCar;
    }

    private <K, V> void addToMapSet(Map<K, Set<V>> map, K key, V value) {
        Set<V> set = map.get(key);
        if (map.get(key) == null) {
            set = new HashSet<>();
            set.add(value);
            map.put(key, set);
        } else {
            set.add(value);
        }
    }

    private <K> void removeCarFromMapSet(Map<K, Set<Car>> map, K key, Long carId) {
        Set<Car> cars = map.get(key);
        cars.remove(new Car(carId));

        if (map.get(key) == null || map.get(key).isEmpty()) {
            map.remove(key);
        }
    }

    private void checkCar(Car car) {
        checkNotNull("car", car);
        String carBrand = car.getBrand();
        String carModelName = car.getModelName();

        if (carBrand == null || carBrand.isEmpty()) {
            throw new IllegalArgumentException("Provided car has wrong parameters "
                    + "carBrand: " + carBrand + " carModelName: " + carModelName);
        }
    }

    private void checkCarOwner(Car car, Owner owner) {
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
}
