package seedu.address.model.grade;

import static java.util.Objects.requireNonNull;
import seedu.address.commons.util.AppUtil;

public class Score {
    public static final String MESSAGE_CONSTRAINTS = "Score must be a non-negative integer.";

    public final int value;

    public Score(String value) {
        requireNonNull(value);
        AppUtil.checkArgument(value.matches("\\d+") && Integer.parseInt(value) >= 0,
                MESSAGE_CONSTRAINTS);
        this.value = Integer.parseInt(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof Score
                && value == ((Score) other).value);
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }
}
