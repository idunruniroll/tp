package seedu.address.model.person;

import java.util.Objects;

public class Remark {
    public final String value;

    public Remark(String value) {
        this.value = value == null ? "" : value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Remark && value.equals(((Remark) other).value));
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
