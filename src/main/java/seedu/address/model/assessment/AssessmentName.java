package seedu.address.model.assessment;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.AppUtil;

/**
 * Represents an assessment's name.
 */
public class AssessmentName {
    public static final String MESSAGE_CONSTRAINTS = "Assessment names should not be blank.";

    public final String value;

    /**
     * Constructs an AssessmentName.
     *
     * @param value the assessment name value
     */
    public AssessmentName(String value) {
        requireNonNull(value);
        AppUtil.checkArgument(!value.trim().isEmpty(), MESSAGE_CONSTRAINTS);
        this.value = value.trim();
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof AssessmentName
                && value.equals(((AssessmentName) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
