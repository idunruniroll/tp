package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Messages;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.assessment.AssessmentName;
import seedu.address.model.assessment.MaxScore;
import seedu.address.model.course.Course;
import seedu.address.model.grade.Score;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    private static final String SPECIAL_CHARACTERS = "+_.-";
    private static final String ALPHANUMERIC_NO_UNDERSCORE = "[^\\W_]+";
    private static final String LOCAL_PART_REGEX = "^" + ALPHANUMERIC_NO_UNDERSCORE
            + "([" + SPECIAL_CHARACTERS + "]" + ALPHANUMERIC_NO_UNDERSCORE + ")*";
    private static final String DOMAIN_PART_REGEX = ALPHANUMERIC_NO_UNDERSCORE
            + "(-" + ALPHANUMERIC_NO_UNDERSCORE + ")*";
    private static final String DOMAIN_LAST_PART_REGEX = "(" + DOMAIN_PART_REGEX + "){2,}$";
    private static final String DOMAIN_REGEX = "(" + DOMAIN_PART_REGEX + "\\.)*" + DOMAIN_LAST_PART_REGEX;
    private static final String EMAIL_VALIDATION_REGEX = LOCAL_PART_REGEX + "@" + DOMAIN_REGEX;

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Returns true if all the given prefixes are present in the argument multimap.
     */
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses a {@code String email} into a validated email string.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is not a valid email address.
     */
    public static String parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!trimmedEmail.matches(EMAIL_VALIDATION_REGEX)) {
            throw new ParseException("Invalid email address.");
        }
        return trimmedEmail;
    }

    /**
     * Parses a {@code String name} into an {@code AssessmentName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid
     */
    public static AssessmentName parseAssessmentName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        try {
            return new AssessmentName(trimmedName);
        } catch (IllegalArgumentException e) {
            throw new ParseException(AssessmentName.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses a {@code String value} into a {@code MaxScore}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code value} is invalid
     */
    public static MaxScore parseMaxScore(String value) throws ParseException {
        requireNonNull(value);
        try {
            return new MaxScore(value.trim());
        } catch (IllegalArgumentException e) {
            throw new ParseException(MaxScore.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses a {@code String value} into a {@code Score}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code value} is invalid
     */
    public static Score parseScore(String value) throws ParseException {
        requireNonNull(value);
        try {
            return new Score(value.trim());
        } catch (IllegalArgumentException e) {
            throw new ParseException(Score.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses a {@code String courseCode} into a valid course code string.
     * The course code will be converted to uppercase.
     *
     * @throws ParseException if the given {@code courseCode} is invalid
     */
    public static String parseCourseCode(String courseCode) throws ParseException {
        requireNonNull(courseCode);
        String trimmedCourseCode = courseCode.trim().toUpperCase();
        if (!Course.isValidCourseCode(trimmedCourseCode)) {
            throw new ParseException(Course.MESSAGE_CONSTRAINTS);
        }
        return trimmedCourseCode;
    }

    /**
     * Parses a comma-separated list of course codes.
     *
     * @throws ParseException if no values are provided or any course code is invalid
     */
    public static List<String> parseCourseCodes(String rawCourseCodes) throws ParseException {
        requireNonNull(rawCourseCodes);

        List<String> splitCourseCodes = Arrays.stream(rawCourseCodes.split(","))
                .map(String::trim)
                .filter(code -> !code.isEmpty())
                .toList();

        if (splitCourseCodes.isEmpty()) {
            throw new ParseException(Course.MESSAGE_CONSTRAINTS);
        }

        List<String> parsedCourseCodes = new ArrayList<>();
        for (String splitCourseCode : splitCourseCodes) {
            parsedCourseCodes.add(parseCourseCode(splitCourseCode));
        }

        return parsedCourseCodes;
    }

    /**
     * Parses a student ID string.
     * Leading/trailing whitespace is trimmed and the value is uppercased.
     *
     * @throws ParseException if blank.
     */
    public static String parseStudentId(String value) throws ParseException {
        requireNonNull(value);
        String trimmed = value.trim().toUpperCase();
        if (!trimmed.matches("[A-Z0-9]{6,12}")) {
            throw new ParseException(Messages.MESSAGE_INVALID_STUDENT_ID_FORMAT);
        }
        return trimmed;
    }

    /**
     * Parses a student name string.
     * Leading/trailing spaces are trimmed; multiple internal whitespace is collapsed to single spaces.
     *
     * @throws ParseException if invalid
     */
    public static String parseStudentName(String value) throws ParseException {
        requireNonNull(value);
        String collapsed = value.trim().replaceAll("\\s+", " ");
        if (collapsed.isEmpty()
                || collapsed.length() > 60
                || !collapsed.matches("[\\p{L} .,'-]+")) {
            throw new ParseException(Messages.MESSAGE_INVALID_STUDENT_NAME_FORMAT);
        }
        return collapsed;
    }
}
