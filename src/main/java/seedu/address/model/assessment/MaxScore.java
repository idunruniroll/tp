package seedu.address.model.assessment;

import static java.util.Objects.requireNonNull;
import seedu.address.commons.util.AppUtil;

public class MaxScore {
    public static final String MESSAGE_CONSTRAINTS = "Max score must be a positive integer.";

    public final int value;

    public MaxScore(String value) {
        requireNonNull(value);
        AppUtil.checkArgument(value.matches("\\d+") && Integer.parseInt(value) > 0,
                MESSAGE_CONSTRAINTS);
        this.value = Integer.parseInt(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof MaxScore
                && value == ((MaxScore) other).value);
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }
}
