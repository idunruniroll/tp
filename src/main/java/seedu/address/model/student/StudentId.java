package seedu.address.model.student;

import static java.util.Objects.requireNonNull;

/**
 * Represents a student's ID.
 */
public class StudentId {
    public final String value;

    /**
     * Constructs a StudentId.
     *
     * @param value the student ID value
     */
    public StudentId(String value) {
        requireNonNull(value);
        this.value = value.trim();
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof StudentId
                && value.equals(((StudentId) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
