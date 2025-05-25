package exception;
/**
 * Custom exception for errors related to intersection operations.
 * For example, trying to access a non-existent intersection.
 */
public class InvalidIntersectionException extends Exception {

    /**
     * Constructs a new InvalidIntersectionException with the specified detail message.
     * @param message the detail message.
     */
    public InvalidIntersectionException(String message) {
        super(message);
    }
}