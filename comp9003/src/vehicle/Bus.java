package vehicle;

public class Bus extends Vehicle {
    public Bus(String vehicleID) {
        super(vehicleID);
    }

    @Override
    protected double getSpeed() {
        return 80.0;
    }
}