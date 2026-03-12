package seedu.address.model.grade.exceptions;

public class GradeNotFoundException extends RuntimeException {
    public GradeNotFoundException() {
        super("Grade not found.");
    }
}
