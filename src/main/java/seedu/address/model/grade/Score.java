package seedu.address.model.grade;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Represents a Grade score in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidScore(String)}
 */
public class Score {

    public static final String MESSAGE_CONSTRAINTS = "Score must be a number 0 or above, with at most 1 decimal place.";

    // Accepts: 0, 5, 5.0, 5.5, 10.0
    // Rejects: -1, 5.55, abc, empty
    private static final String SCORE_VALIDATION_REGEX = "^\\d+(\\.\\d)?$";

    public final String value;

    /**
     * Constructs a {@code Score}.
     *
     * @param score A valid score.
     */
    public Score(String score) {
        requireNonNull(score);
        checkArgument(isValidScore(score), MESSAGE_CONSTRAINTS);
        this.value = score;
    }

    /**
     * Returns true if a given string is a valid score.
     */
    public static boolean isValidScore(String test) {
        if (test == null) {
            return false;
        }

        if (!test.matches(SCORE_VALIDATION_REGEX)) {
            return false;
        }

        try {
            return Double.parseDouble(test) >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Returns true if the score does not exceed the given max score.
     */
    public static boolean isWithinMaxScore(String score, String maxScore) {
        if (score == null || maxScore == null
                || !isValidScore(score)
                || !seedu.address.model.assessment.MaxScore.isValidMaxScore(maxScore)) {
            return false;
        }

        try {
            return Double.parseDouble(score) <= Double.parseDouble(maxScore);
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
                || (other instanceof Score
                        && value.equals(((Score) other).value));
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
