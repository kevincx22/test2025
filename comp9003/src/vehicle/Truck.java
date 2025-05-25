package vehicle;

public class Truck extends Vehicle {
    public Truck(String vehicleID) {
        super(vehicleID);
    }

    @Override
    protected double getSpeed() {
        return 90.0;
    }
}