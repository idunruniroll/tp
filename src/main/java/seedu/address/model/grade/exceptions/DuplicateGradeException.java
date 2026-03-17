package seedu.address.model.grade.exceptions;

public class DuplicateGradeException extends RuntimeException {
    public DuplicateGradeException() {
        super("Operation would result in duplicate grades.");
    }
}
