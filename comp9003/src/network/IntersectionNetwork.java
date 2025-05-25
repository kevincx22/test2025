package network;
import exception.InvalidIntersectionException;
import java.util.ArrayList;
import java.util.Arrays; // For Arrays.toString()
import vehicle.Vehicle;

/**
 * Models intersections and roads where vehicles interact.
 * Each instance of this class tracks the progress and interactions of one such vehicle.
 * It does not interact directly with the main Vehicle class.
 */
public class IntersectionNetwork {

    // Shared road layout for all "vehicles"
    private static final int NUM_LANES = 3; // A 3-lane road
    private static final int NUM_INTERSECTIONS = 10; // A straight road with 10 intersections
    private static final int[][] roadLayout = new int[NUM_LANES][NUM_INTERSECTIONS];

    static {
        // Initialize road layout:
        // Intersection are on lanes 1 and 3 (because of 0-indexed, 0=Lane1, 2=Lane3).
        // A value of 1 indicates an intersection, and a 0 indicates no intersection.
        for (int i = 0; i < NUM_INTERSECTIONS; i++) {
            roadLayout[0][i] = 1; // Lane 1 has intersections
            roadLayout[1][i] = 0; // Lane 2 has no intersections
            roadLayout[2][i] = 1; // Lane 3 has intersections
        }
    }

    // Instance variables for each conceptual vehicle
    private final String vehicleId;
    private final int vehicleLane;
    private final int[] interactionPattern; // Defines how this vehicle interacts
    private int currentIntersectionIndex; // Tracks the next intersection to be processed
    private final ArrayList<String> statusLog; // Logs the activity of this vehicle

    /**
     * Constructor for IntersectionNetwork.
     * Each object is considered a vehicle for tracking intersection passage.
     * @param vehicleId A name or ID for this conceptual vehicle.
     * @param initialLane The lane (0, 1,2) this vehicle will use.
     */
    public IntersectionNetwork(String vehicleId, int initialLane) {
        this.vehicleId = vehicleId;
        if (initialLane < 0 || initialLane >= NUM_LANES) {
            throw new IllegalArgumentException("Initial lane must be between 0 and " + (NUM_LANES - 1) +
                    ". Received(invalid): " + initialLane);
        }
        this.vehicleLane = initialLane;

        // Each vehicle move through every alternate intersection.
        // Assign 1 for interaction, 0 for skip
        this.interactionPattern = new int[NUM_INTERSECTIONS];
        for (int i = 0; i < NUM_INTERSECTIONS; i++) {
            if ((i % 2) == 0) { // Intersections at index 0, 2, 4...)
                this.interactionPattern[i] = 1; // Interact this point
            } else {
                this.interactionPattern[i] = 0; // Skip
            }
        }

        this.currentIntersectionIndex = 0;
        this.statusLog = new ArrayList<>(); // Use ArrayList to manage status messages
    }

    public String getVehicleId() {
        return vehicleId;
    }
    /**
     * Simulates the vehicle attempt to move through the current intersection
     * based on its interaction pattern and the road layout.
     * Throws InvalidIntersectionException if an illegal access is attempted, like on a middle lane.
     */
    public void moveThrough() throws InvalidIntersectionException {
        if (currentIntersectionIndex >= NUM_INTERSECTIONS) {
            String endMsg = vehicleId + ": All intersections processed.";
            if (!statusLog.contains(endMsg)) statusLog.add(endMsg);
            return;
        }

        int intersectionSerial = currentIntersectionIndex + 1; // 1-based for logging
        String logPrefix = vehicleId + " at Intersection " + intersectionSerial + " (Index " + currentIntersectionIndex +") on Lane " + (vehicleLane + 1) + ": ";

        boolean intendsToUse = (interactionPattern[currentIntersectionIndex] == 1);

        if (intendsToUse) {
            // Check if an actual intersection physically exists at the vehicle's current lane?
            if (roadLayout[vehicleLane][currentIntersectionIndex] == 1) {
                statusLog.add(logPrefix + "Successfully moved through.");
            } else {
                // Trying to use an intersection That doesn't exist
                String errorMsg = logPrefix + "Attempted to move through a non-existent intersection.";
                statusLog.add(errorMsg);
                currentIntersectionIndex++; // advance past this conceptual point
                throw new InvalidIntersectionException(vehicleId + " - Access denied: Intersection " +
                        intersectionSerial + " does not exist on Lane " + (vehicleLane + 1) + ".");
            }
        } else {
            statusLog.add(logPrefix + "Skipped as per interaction pattern.");
        }
        currentIntersectionIndex++;
    }

    /**
     * Shows the current intersection status of this vehicle.
     */
    public void showIntersectionStatus() {
        System.out.println("----- Intersection Status for: " + vehicleId + " ----");
        System.out.println("Current Lane: " + (vehicleLane + 1));
        System.out.println("Interaction Pattern: " + Arrays.toString(interactionPattern));
        if (currentIntersectionIndex >= NUM_INTERSECTIONS) {
            System.out.println("Next Intersection to Process: Completed all intersections.");
        } else {
            System.out.println("Next Intersection to Process (0-indexed): " + currentIntersectionIndex +
                    " (Which is point number " + (currentIntersectionIndex + 1) + ")");
        }
        System.out.println("Activity Log:");
        if (statusLog.isEmpty()) {
            System.out.println("  No activity recorded yet.");
        } else {
            for (String log : statusLog) {
                System.out.println("  " + log);
            }
        }
        System.out.println("----------------------");
    }

    // Main method for testing this class individually
    public static void main(String[] args) {
        // Use arraylist to manage objects of this class
        ArrayList<IntersectionNetwork> simVehicles = new ArrayList<>();

        // Create three objects of this class; each object is considered a vehicle.
        // And one vehicle will be on the middle lane to test error handling.
        simVehicles.add(new IntersectionNetwork("V1", 0)); // Lane 1
        simVehicles.add(new IntersectionNetwork("V2", 1)); // Lane 2, aka Middle Lane (problematic)
        simVehicles.add(new IntersectionNetwork("V3", 2)); // Lane 3

        System.out.println("Simulating intersection passage for " + NUM_INTERSECTIONS + " intersection points...\n");

        // Loop to simulate passage through all conceptual intersection points
        for (int i = 0; i < NUM_INTERSECTIONS; i++) {
            System.out.println(">>> Processing Conceptual Intersection Point " + (i + 1) + " <<<");
            for (IntersectionNetwork tracker : simVehicles) {
                try {
                    // Ensure moveThrough is only called if there are intersections left for this vehicle
                    if (tracker.currentIntersectionIndex < NUM_INTERSECTIONS) {
                        tracker.moveThrough();
                    }
                } catch (InvalidIntersectionException e) {
                    // Robust error handling for invalid input values or illegal state changes.
                    System.err.println("CAUGHT EXCEPTION: " + e.getMessage());
                }
            }
            System.out.println(); // Newline for readability
        }

        System.out.println("\n------ Final Intersection Statuses ------");
        for (IntersectionNetwork tracker : simVehicles) {
            tracker.showIntersectionStatus(); // Show status for each tracker
        }
    }
}