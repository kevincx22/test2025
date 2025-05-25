package vehicle;

import exception.SimulationException;

public abstract class Vehicle {
    private String vehicleID;
    private double velocity;
    protected double currentPosition = 0.0;
    private final int[] lanes = {1, 2, 3};
    private int currentLane = 0;
    protected double timeInterval = 5.0;
    private int moveCount = 0;

    public Vehicle(String vehicleID) {
        this.vehicleID = vehicleID;
        this.velocity = calculateVelocity();
    }

    // Subclasses must provide their speed (units per interval)
    protected abstract double getSpeed();

    public double calculateVelocity() {
        // velocity = speed per time interval
        this.velocity = getSpeed() / timeInterval;
        return this.velocity;
    }

    public void move() throws SimulationException {
        moveCount++;
        calculateVelocity();
        currentPosition += velocity * timeInterval;
        // Display state on second move
        if (moveCount == 2) {
            showTrafficState();
        }
        changeLane();
    }

    public void changeLane() {
        // cycle through lanes
        currentLane = (currentLane + 1) % lanes.length;
    }

    public void showTrafficState() {
        System.out.printf(
                "Vehicle %s: lane %d, current position %.2f, velocity %.2f\n",
                vehicleID,
                lanes[currentLane],
                currentPosition,
                velocity
        );
    }

    public String getVehicleID() {
        return vehicleID;
    }

    public int getCurrentLane() {
        return lanes[currentLane];
    }

    public double getCurrentPosition() {
        return currentPosition;
    }
}