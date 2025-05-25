package signal;

import java.util.ArrayList;
import java.util.List;
import exception.InvalidSignalIDException;
import exception.InvalidTimerException;

public class TrafficSignal {
    private String signalID;
    private String[] states = {"red", "yellow", "green"};
    private int[] timers = {10, 2, 15};
    private int currentIndex = -1;
    private static final List<TrafficSignal> signals = new ArrayList<>();

    public TrafficSignal(String signalID) throws InvalidSignalIDException {
        if (signalID == null || signalID.isEmpty()) {
            throw new InvalidSignalIDException("Signal ID cannot be null or empty");
        }
        this.signalID = signalID;
        this.currentIndex = 0;
        signals.add(this);
    }

    /**
     * Cycle to next state, throw exception on invalid transition
     */
    public void signal() {
        // cycle through states with wrap-around
        currentIndex = (currentIndex + 1) % states.length;
    }

    /**
     * Display all signals' status
     */
    public static void showTrafficSignal() {
        for (TrafficSignal ts : signals) {
            String state = ts.currentIndex >= 0 ? ts.states[ts.currentIndex] : "none";
            int timer = ts.currentIndex >= 0 ? ts.timers[ts.currentIndex] : 0;
            System.out.println("Signal " + ts.signalID + ": " + state + " (" + timer + "s)");
        }
    }

    public String getCurrentState() {
        return currentIndex >= 0 ? states[currentIndex] : "none";
    }

    public int getCurrentTimer() {
        return currentIndex >= 0 ? timers[currentIndex] : 0;
    }

    public void setTimers(int redTime, int yellowTime, int greenTime) throws InvalidTimerException {
        if (redTime <= 0 || yellowTime <= 0 || greenTime <= 0) {
            throw new InvalidTimerException("Timer values must be positive");
        }
        this.timers[0] = redTime;
        this.timers[1] = yellowTime;
        this.timers[2] = greenTime;
    }

    public String getSignalID() {
        return signalID;
    }

    public static void main(String[] args) {
        try {
            TrafficSignal ts1 = new TrafficSignal("A");
            TrafficSignal ts2 = new TrafficSignal("B");
            TrafficSignal ts3 = new TrafficSignal("C");

            ts1.signal();
            ts2.signal();
            ts3.signal();
            ts1.signal();
            ts2.signal();
            ts3.signal();
            ts1.signal();
            ts2.signal();
            ts3.signal();
            // fourth call triggers exception
            ts1.signal();
        } catch (InvalidSignalIDException e) {
            System.err.println("Signal error: " + e.getMessage());
        }

        TrafficSignal.showTrafficSignal();
    }
}