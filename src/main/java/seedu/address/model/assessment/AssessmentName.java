package seedu.address.model.assessment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an assessment's name.
 */
public class AssessmentName {
    public static final int MAX_LENGTH = 50;
    public static final String MESSAGE_CONSTRAINTS = "Assessment names should not be blank and should be at most "
            + MAX_LENGTH + " characters long.";

    public final String value;

    /**
     * Constructs an AssessmentName.
     *
     * @param value the assessment name value
     */
    public AssessmentName(String assessmentName) {
        requireNonNull(assessmentName);
        String normalizedAssessmentName = normalizeAssessmentName(assessmentName);
        checkArgument(!normalizedAssessmentName.isEmpty()
                && normalizedAssessmentName.length() <= MAX_LENGTH, MESSAGE_CONSTRAINTS);
        value = normalizedAssessmentName;
    }

    private static String normalizeAssessmentName(String assessmentName) {
        String trimmedName = assessmentName.trim().replaceAll("\\s+", " ");
        String[] words = trimmedName.split(" ");
        StringBuilder formattedName = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            String word = words[i];

            if (!word.isEmpty()) {
                formattedName.append(formatWord(word));
            }

            if (i < words.length - 1) {
                formattedName.append(" ");
            }
        }

        return formattedName.toString();
    }

    public String getNormalizedName() {
        return value.toLowerCase();
    }

    private static String formatWord(String word) {
        if (shouldPreserveUppercase(word)) {
            return word.toUpperCase();
        }

        if (Character.isLetter(word.charAt(0))) {
            return Character.toUpperCase(word.charAt(0))
                    + word.substring(1).toLowerCase();
        }

        return word.toLowerCase();
    }

    private static boolean shouldPreserveUppercase(String word) {
        return word.matches("[A-Za-z]*\\d+[A-Za-z\\d]*")
                || (word.length() > 1 && word.matches("[A-Z]+"))
                || (word.length() > 1 && word.matches("[a-zA-Z]+") && word.equals(word.toUpperCase()));
    }

    public static boolean isValidAssessmentName(String test) {
        if (test == null) {
            return false;
        }

        String normalizedName = normalizeAssessmentName(test);
        return !normalizedName.isEmpty() && normalizedName.length() <= MAX_LENGTH;
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
