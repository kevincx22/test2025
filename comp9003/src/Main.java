import java.util.*;

import exception.InvalidSignalIDException;
import exception.InvalidTimerException;
import exception.SimulationException;
import vehicle.Vehicle;
import vehicle.Car;   // it can be Bus or Truck
import signal.TrafficSignal;

public class Main {
    public static void main(String[] args) throws SimulationException, InvalidSignalIDException {
        Scanner sc = new Scanner(System.in);
        List<Vehicle> vehicles = new ArrayList<>();
        List<TrafficSignal> signals = new ArrayList<>();

        while (true) {
            System.out.println("\n=== Simulation Main Menu ===");
            System.out.println("1. Add Vehicle");
            System.out.println("2. List / Remove Vehicles");
            System.out.println("3. Add Traffic Signal");
            System.out.println("4. List / Set Traffic Signal Timers");
            System.out.println("5. Run 5-Minute Simulation");
            System.out.println("0. Exit");
            System.out.print("Select an option: ");

            String input = sc.nextLine().trim();
            int cmd;
            try {
                cmd = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 0 and 5.");
                continue;
            }

            switch (cmd) {
                case 1:
                    System.out.print("Enter vehicle ID: ");
                    String vid = sc.nextLine().trim();
                    vehicles.add(new Car(vid));
                    System.out.println("Vehicle \"" + vid + "\" added.");
                    break;

                case 2:
                    if (vehicles.isEmpty()) {
                        System.out.println("No vehicles available. Use option 1 to add a vehicle first.");
                        break;
                    }
                    System.out.println("Current Vehicles:");
                    for (int i = 0; i < vehicles.size(); i++) {
                        Vehicle v = vehicles.get(i);
                        System.out.printf("[%d] ID=%s, Position=%.2fm, Lane=%d%n",
                                i, v.getVehicleID(), v.getCurrentPosition(), v.getCurrentLane());
                    }
                    System.out.print("Enter index to remove (-1 to cancel): ");
                    String remLine = sc.nextLine().trim();
                    if (remLine.equals("-1")) {
                        System.out.println("Removal cancelled.");
                    } else if (remLine.matches("0|[1-9]\\d*")) {
                        int remIdx = Integer.parseInt(remLine);
                        if (remIdx < vehicles.size()) {
                            vehicles.remove(remIdx);
                            System.out.println("Vehicle removed.");
                        } else {
                            System.out.println("Index out of range.");
                        }
                    } else {
                        System.out.println("Invalid input. Please enter -1 or a valid non-negative integer without leading zeros.");
                    }
                    break;

                case 3:
                    System.out.print("Enter traffic signal ID: ");
                    String sid = sc.nextLine().trim();
                    signals.add(new TrafficSignal(sid));
                    System.out.println("Traffic signal \"" + sid + "\" added.");
                    break;

                case 4:
                    if (signals.isEmpty()) {
                        System.out.println("No traffic signals yet. Please add one first.");
                        break;
                    }
                    System.out.println("Current Traffic Signals:");
                    for (int i = 0; i < signals.size(); i++) {
                        TrafficSignal s = signals.get(i);
                        System.out.printf("[%d] ID=%s, State=%s, Remaining=%ds%n",
                                i, s.getSignalID(), s.getCurrentState(), s.getCurrentTimer());
                    }
                    System.out.print("Select index to set timers (-1 to cancel): ");
                    String idxLine = sc.nextLine().trim();
                    if (idxLine.equals("-1")) {
                        System.out.println("Cancelled.");
                        break;
                    }
                    if (!idxLine.matches("0|[1-9]\\d*")) {
                        System.out.println("Invalid input.");
                        break;
                    }
                    int idx = Integer.parseInt(idxLine);
                    if (idx < 0 || idx >= signals.size()) {
                        System.out.println("Index out of range.");
                        break;
                    }
                    System.out.print("Enter new Red(s), Yellow(s), Green(s) (e.g. 10,2,3 or 10 2 3): ");
                    String line = sc.nextLine().trim();
                    String[] parts = line.split("[,\\s]+");
                    if (parts.length == 3) {
                        try {
                            int r = Integer.parseInt(parts[0]);
                            int y = Integer.parseInt(parts[1]);
                            int g = Integer.parseInt(parts[2]);
                            signals.get(idx).setTimers(r, y, g);
                            System.out.println("Timers updated.");
                        } catch (NumberFormatException | InvalidTimerException ex) {
                            System.out.println("Invalid numbers. Please enter three integers.");
                        }
                    } else {
                        System.out.println("Invalid format. Please enter exactly three numbers.");
                    }
                    break;

                case 5:
                    runSimulation(vehicles, signals);
                    break;

                case 0:
                    System.out.println("Exiting. Goodbye!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid option, please choose between 0 and 5.");
            }
        }
    }

    private static void runSimulation(List<Vehicle> vehicles, List<TrafficSignal> signals) throws SimulationException, InvalidSignalIDException {
        final int totalSteps = 300 / 5;  // 60 steps
        for (int step = 1; step <= totalSteps; step++) {
            for (Vehicle v : vehicles) {
                v.move();
            }
            for (TrafficSignal ts : signals) {
                ts.signal();
            }
            if (step % 12 == 0) {
                System.out.println("---- One Minute Completed! ----");
            }
        }

        System.out.println("\n=== Simulation Finished: Vehicle States ===");
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles were added to the simulation.");
        } else {
            for (Vehicle v : vehicles) {
                v.showTrafficState();
            }
        }
        System.out.println("=== Simulation Finished: Traffic Signal States ===");
        if (signals.isEmpty()) {
            System.out.println("No traffic signals were added to the simulation.");
        } else {
            for (TrafficSignal s : signals) {
                System.out.printf("Signal [%s]: State=%s, Remaining=%ds%n",
                        s.getSignalID(), s.getCurrentState(), s.getCurrentTimer());
            }
        }
    }
}
