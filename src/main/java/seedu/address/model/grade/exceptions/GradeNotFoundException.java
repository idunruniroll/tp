package seedu.address.model.grade.exceptions;

/**
 * Signals that the operation is unable to find the specified Grade.
 */
public class GradeNotFoundException extends RuntimeException {
    public GradeNotFoundException() {
        super("Grade not found.");
    }
}
