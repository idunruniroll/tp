package seedu.address.model.assessment.exceptions;

/**
 * Signals that the operation will result in duplicate Assessments (Assessments are considered duplicates if they have
 * the same identity).
 */
public class DuplicateAssessmentException extends RuntimeException {
    public DuplicateAssessmentException() {
        super("Operation would result in duplicate assessments.");
    }
}
