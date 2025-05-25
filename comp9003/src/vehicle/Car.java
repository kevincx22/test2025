package vehicle;

public class Car extends Vehicle {
    public Car(String vehicleID) {
        super(vehicleID);
    }

    @Override
    protected double getSpeed() {
        return 100.0;
    }
}