package Exceptions;

/**
 * Thrown when a student fails the same course for the 3rd time,
 * triggering academic dismissal.
 *
 * Business rule: "Students can't fail more than 3 times."
 */
public class AcademicDismissalException extends Exception {

    private static final long serialVersionUID = 1L;

    public AcademicDismissalException(String message) {
        super(message);
    }

    public AcademicDismissalException(String message, Throwable cause) {
        super(message, cause);
    }
}
