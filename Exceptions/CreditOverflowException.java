package Exceptions;

/**
 * Thrown when a student tries to register for a course
 * that would push their total semester credits above 21.
 *
 * Business rule: "Students can't have more than 21 credits."
 */
public class CreditOverflowException extends Exception {

    private static final long serialVersionUID = 1L;

    public CreditOverflowException(String message) {
        super(message);
    }

    public CreditOverflowException(String message, Throwable cause) {
        super(message, cause);
    }
}
