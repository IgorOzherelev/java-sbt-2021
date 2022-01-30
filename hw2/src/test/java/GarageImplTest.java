import mipt.bit.garage.Car;
import mipt.bit.garage.GarageImpl;
import mipt.bit.garage.Owner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.fail;

public class GarageImplTest {
    private final GarageImpl garage = new GarageImpl();

    Owner owner1 = new Owner(1, "Ivan", "Ivanov", 18);
    Owner owner2 = new Owner(2, "Peter", "Ivanov", 22);

    Car car1 = new Car(1, "1", "2", 1, 1, 1);
    Car car2 = new Car(2, "1", "3", 2, 2, 1);
    Car car3 = new Car(3, "1", "4", 3, 3, 1);
    Car car4 = new Car(4, "1", "5", 7, 4, 2);
    Car car5 = new Car(5, "1", "6", 6, 5, 2);
    Car car6 = new Car(6, "2", "6", 6, 5, 2);
    Car car7 = new Car(7, "2", "6", 8, 6, 2);

    @AfterEach
    public void clearGarageAfterEachTest() {
        garage.clear();
    }

    @Test
    public void initWhenNullOwner() {
        Car car = new Car(1, "1", "2", 1, 1, 1);
        try {
            garage.addCar(car, null);
            fail();
        } catch (IllegalArgumentException ex) {
            Assertions.assertEquals("Provided null owner object", ex.getMessage());
        }
    }

    @Test
    public void addCarWhenNullCar() {
        try {
            garage.addCar(null, null);
            fail();
        } catch (IllegalArgumentException ex) {
            Assertions.assertEquals("Provided null car object", ex.getMessage());
        }
    }

    @Test
    public void addCarWhenWrongOwner() {
        Car car = new Car(1, "1", "2", 1, 1, 1);
        try {
            garage.addCar(car, owner2);
            fail();
        } catch (IllegalArgumentException ex) {
            Assertions.assertEquals("Provided car belongs to another" +
                    " owner car.getOwnerId(): 1 ownerId: 2", ex.getMessage());
        }
    }

    @Test
    public void removeCarWhenEmptyGarage() {
        Car car = new Car(1, "1", "2", 1, 1, 1);
        try {
            garage.removeCar(car.getCarId());
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void removeCarPositive() {
        Car car = new Car(1, "1", "2", 1, 1, 1);
        addCarToGarage(car, owner1);

        garage.removeCar(car.getCarId());
        Assertions.assertFalse(isCarAndOwnerInGarage(car, owner1));
    }

    @Test
    public void removeCarWhenTryingToRemoveUnexisted() {
        Car car1 = new Car(1, "1", "2", 1, 1, 1);
        Car car2 = new Car(2, "1", "2", 1, 1, 1);
        try {
            addCarToGarage(car1, owner1);
            garage.removeCar(car2.getCarId());
            fail();
        } catch (NullPointerException ignored) {}
    }

    @Test
    public void meanCarNumberForEachOwnerPositive() {
        initGarage();
        Assertions.assertEquals(3, garage.meanCarNumberForEachOwner());
    }

    @Test
    public void meanOwnersAgeOfCarBrandPositive() {
        initGarage();
        Assertions.assertEquals(20, garage.meanOwnersAgeOfCarBrand("1"));
    }

    @Test
    public void allCarsOfOwnerPositive() {
        initGarage();

        Set<Car> ownerCars = garage.allCarsOfOwner(owner1);
        Assertions.assertEquals(3, ownerCars.size());

        Assertions.assertTrue(ownerCars.contains(car1));
        Assertions.assertTrue(ownerCars.contains(car2));
        Assertions.assertTrue(ownerCars.contains(car3));
    }

    @Test
    public void allCarsOfOwnerAfterRemove() {
        initGarage();

        Set<Car> ownerCars = garage.allCarsOfOwner(owner1);
        garage.removeCar(car3.getCarId());
        Assertions.assertEquals(2, ownerCars.size());

        Assertions.assertTrue(ownerCars.contains(car1));
        Assertions.assertTrue(ownerCars.contains(car2));

        Assertions.assertFalse(isCarAndOwnerInGarage(car3, owner1));
        Assertions.assertTrue(isCarAndOwnerInGarage(car1, owner1));
        Assertions.assertTrue(isCarAndOwnerInGarage(car2, owner1));
    }

    @Test
    public void allCarsOfBrandPositive() {
        initGarage();

        Set<Car> cars = garage.allCarsOfBrand("1");
        Assertions.assertEquals(5, cars.size());

        Assertions.assertTrue(cars.contains(car1));
        Assertions.assertTrue(cars.contains(car2));
        Assertions.assertTrue(cars.contains(car3));
        Assertions.assertTrue(cars.contains(car4));
        Assertions.assertTrue(cars.contains(car5));
    }

    @Test
    public void allCarsOfBrandAfterRemove() {
        initGarage();

        garage.removeCar(car1.getCarId());
        Set<Car> cars = garage.allCarsOfBrand("1");
        Assertions.assertEquals(4, cars.size());

        Assertions.assertFalse(cars.contains(car1));

        Assertions.assertTrue(cars.contains(car2));
        Assertions.assertTrue(cars.contains(car3));
        Assertions.assertTrue(cars.contains(car4));
        Assertions.assertTrue(cars.contains(car5));
    }

    @Test
    public void allCarsUniqueOwnersPositive() {
        initGarage();

        Set<Owner> owners = garage.allCarsUniqueOwners();
        Assertions.assertEquals(2, owners.size());
        Assertions.assertTrue(owners.contains(owner1));
        Assertions.assertTrue(owners.contains(owner2));
    }

    @Test
    public void allCarsUniqueOwnersAfterRemovingAllOwnerCars() {
        initGarage();

        Set<Owner> owners = garage.allCarsUniqueOwners();
        garage.removeCar(car1.getCarId());
        garage.removeCar(car2.getCarId());
        garage.removeCar(car3.getCarId());

        Assertions.assertEquals(1, owners.size());
    }

    @Test
    public void topThreeCarsByMaxVelocityPositive() {
        initGarage();

        TreeSet<Car> cars = garage.topThreeCarsByMaxVelocity();
        Assertions.assertEquals(3, cars.size());

        Assertions.assertTrue(cars.contains(car4));
        Assertions.assertTrue(cars.contains(car6));
        Assertions.assertTrue(cars.contains(car7));
    }

    @Test
    public void topThreeCarsByMaxVelocityAfterDeletionPreviousTopThree() {
        initGarage();

        garage.removeCar(car4.getCarId());
        garage.removeCar(car6.getCarId());
        garage.removeCar(car7.getCarId());

        TreeSet<Car> cars = garage.topThreeCarsByMaxVelocity();
        Assertions.assertEquals(3, cars.size());

        Assertions.assertTrue(cars.contains(car2));
        Assertions.assertTrue(cars.contains(car3));
        Assertions.assertTrue(cars.contains(car5));
    }

    @Test
    public void carsWithPowerMoreThanPositive() {
        initGarage();

        Set<Car> cars = garage.carsWithPowerMoreThan(3);
        Assertions.assertEquals(5, cars.size());

        Assertions.assertTrue(cars.contains(car3));
        Assertions.assertTrue(cars.contains(car4));
        Assertions.assertTrue(cars.contains(car5));
        Assertions.assertTrue(cars.contains(car6));
        Assertions.assertTrue(cars.contains(car7));
    }

    @Test
    public void carsWithPowerMoreThanAfterDeletion() {
        initGarage();

        garage.removeCar(car7.getCarId());
        garage.removeCar(car4.getCarId());
        garage.removeCar(car1.getCarId());

        Set<Car> cars = garage.carsWithPowerMoreThan(3);
        Assertions.assertEquals(3, cars.size());

        Assertions.assertTrue(cars.contains(car3));
        Assertions.assertTrue(cars.contains(car5));
        Assertions.assertTrue(cars.contains(car6));
    }

    private void initGarage() {
        List<Car> carsForFirst = new ArrayList<>();
        List<Car> carsForSecond = new ArrayList<>();

        carsForFirst.add(car1);
        carsForFirst.add(car2);
        carsForFirst.add(car3);

        carsForSecond.add(car4);
        carsForSecond.add(car5);
        carsForSecond.add(car6);
        carsForSecond.add(car7);

        addCarsToGarage(owner1, carsForFirst);
        addCarsToGarage(owner2, carsForSecond);
    }

    private void addCarsToGarage(Owner owner, List<Car> cars) {
        cars.forEach(car -> addCarToGarage(car, owner));
    }

    private void addCarToGarage(Car car, Owner owner) {
        garage.addCar(car, owner);
        checkCarAndOwnerInGarage(car, owner);
    }

    private boolean isCarAndOwnerInGarage(Car car, Owner owner) {
        try {
            return garage.getCarIdToCar().get(car.getCarId()).equals(car)
                    && garage.getOwnerToCars().get(owner).contains(car)
                    && garage.getBrandToCars().get(car.getBrand()).contains(car);
        } catch (NullPointerException ignored) {
            return false;
        }
    }

    private void checkCarAndOwnerInGarage(Car car, Owner owner) {
        Assertions.assertEquals(car, garage.getCarIdToCar().get(car.getCarId()));
        Assertions.assertTrue(garage.getOwnerToCars().get(owner).contains(car));
        Assertions.assertTrue(garage.getBrandToCars().get(car.getBrand()).contains(car));
    }
}
