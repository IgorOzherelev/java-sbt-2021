package mipt.bit.garage;

public class Car {
    private final long carId;
    private final String brand;
    private final String modelName;
    private final int maxVelocity;
    private final int power;
    private final long ownerId;

    public Car(long carId, String brand, String modelName, int maxVelocity, int power, long ownerId) {
        this.carId = carId;
        this.brand = brand;
        this.modelName = modelName;
        this.maxVelocity = maxVelocity;
        this.power = power;
        this.ownerId = ownerId;
    }

    Car(long carId) {
        this.carId = carId;
        this.brand = null;
        this.modelName = null;
        this.maxVelocity = 0;
        this.power = 0;
        this.ownerId = 0;
    }

    Car(int power) {
        this.carId = 0;
        this.brand = null;
        this.modelName = null;
        this.maxVelocity = 0;
        this.power = power;
        this.ownerId = 0;
    }

    public long getCarId() {
        return carId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModelName() {
        return modelName;
    }

    public int getMaxVelocity() {
        return maxVelocity;
    }

    public int getPower() {
        return power;
    }

    public long getOwnerId() {
        return ownerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return carId == car.carId;
    }

    @Override
    public int hashCode() {
        return String.valueOf(carId).hashCode();
    }

    @Override
    public String toString() {
        return "Car{" +
                "carId=" + carId +
                ", brand='" + brand + '\'' +
                ", modelName='" + modelName + '\'' +
                ", maxVelocity=" + maxVelocity +
                ", power=" + power +
                ", ownerId=" + ownerId +
                '}';
    }
}