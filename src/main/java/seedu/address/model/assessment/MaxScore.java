package seedu.address.model.assessment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Represents an Assessment's maximum score.
 */
public class MaxScore {
    public static final String MESSAGE_CONSTRAINTS = "Max score must be greater than 0 and at most 999, "
            + "with at most 1 decimal place.";
    private static final String MAX_SCORE_VALIDATION_REGEX = "^\\d{1,3}(\\.\\d)?$";
    private static final double MAX_ALLOWED_SCORE = 999.0;

    public final String value;

    /**
     * Constructs a MaxScore.
     * @param maxScore the maximum score value as a string
     */
    public MaxScore(String maxScore) {
        requireNonNull(maxScore);
        checkArgument(isValidMaxScore(maxScore), MESSAGE_CONSTRAINTS);
        this.value = maxScore;
    }

    /**
     * Returns true if a given string is a valid max score.
     */
    public static boolean isValidMaxScore(String test) {
        if (test == null) {
            return false;
        }

        if (!test.matches(MAX_SCORE_VALIDATION_REGEX)) {
            return false;
        }

        try {
            double parsedValue = Double.parseDouble(test);
            return parsedValue > 0 && parsedValue <= MAX_ALLOWED_SCORE;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public double toDouble() {
        return Double.parseDouble(value);
    }

    @Override
    public String toString() {
        return String.format("%.1f", Double.parseDouble(value));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof MaxScore
                        && value.equals(((MaxScore) other).value));
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
