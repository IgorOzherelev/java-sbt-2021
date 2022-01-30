package mipt.bit;

import mipt.bit.garage.Car;
import mipt.bit.garage.Garage;
import mipt.bit.garage.GarageImpl;
import mipt.bit.garage.Owner;

import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {
        Garage garage = new GarageImpl();

        System.out.println(garage.meanOwnersAgeOfCarBrand("1"));
        System.out.println(garage.meanCarNumberForEachOwner());

        Owner owner = new Owner(1, "Ivan", "Ivanov", 18);
        Owner owner1 = new Owner(2, "Igor", "Ivanov", 22);

        Car car1 = new Car(1, "1", "2", 1, 1, 1);
        Car car2 = new Car(2, "1", "3", 2, 2, 1);
        Car car3 = new Car(3, "1", "4", 3, 3, 1);
        Car car4 = new Car(4, "1", "5", 7, 4, 2);
        Car car5 = new Car(5, "1", "6", 6, 5, 2);
        Car car7 = new Car(7, "2", "6", 8, 6, 2);
        Car car6 = new Car(6, "2", "6", 6, 5, 2);

        garage.addCar(car2, owner);
        garage.addCar(car3, owner);
        garage.addCar(car1, owner);
        garage.addCar(car4, owner1);
        garage.addCar(car5, owner1);
        garage.addCar(car6, owner1);
        garage.addCar(car7, owner1);

        System.out.println("garage.allCarsOfBrand(1)");
        System.out.println(garage.allCarsOfBrand("1"));
        System.out.println("garage.topThreeCarsByMaxVelocity()");
        System.out.println(garage.topThreeCarsByMaxVelocity());
        System.out.println("garage.allCarsUniqueOwners()");
        System.out.println(garage.allCarsUniqueOwners());
        System.out.println("garage.allCarsOfOwner(owner)");
        System.out.println(garage.allCarsOfOwner(owner));
        System.out.println("garage.carsWithPowerMoreThan(3)");
        System.out.println(garage.carsWithPowerMoreThan(3));

        System.out.println(garage.meanCarNumberForEachOwner());
        System.out.println(garage.meanOwnersAgeOfCarBrand("1"));

        garage.removeCar(car4.getCarId());
        garage.removeCar(car5.getCarId());
        garage.removeCar(car7.getCarId());
        garage.removeCar(car6.getCarId());

        TreeSet<Car> carSet = (TreeSet<Car>) garage.topThreeCarsByMaxVelocity();
        System.out.println("END");
    }
}
