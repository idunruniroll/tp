package seedu.address.model.student;

import static java.util.Objects.requireNonNull;

public class StudentId {
    public final String value;

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
