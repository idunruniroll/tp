package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.assessment.AssessmentName;
import seedu.address.model.assessment.MaxScore;
import seedu.address.model.course.Course;
import seedu.address.model.grade.Score;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser
 * classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading
     * and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero
     *                        unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String name} into an {@code AssessmentName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param name the name string to parse
     * @return an AssessmentName object
     * @throws ParseException if the given {@code name} is invalid
     */
    public static AssessmentName parseAssessmentName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (trimmedName.isEmpty()) {
            throw new ParseException(AssessmentName.MESSAGE_CONSTRAINTS);
        }
        return new AssessmentName(trimmedName);
    }

    /**
     * Parses a {@code String value} into a {@code MaxScore}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param value the max score string to parse
     * @return a MaxScore object
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
     * @param value the score string to parse
     * @return a Score object
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
     * Parses a {@code String courseCode} into a valid course code.
     * The course code will be converted to uppercase.
     *
     * @param courseCode the course code string to parse
     * @return a valid course code string
     * @throws ParseException if the given {@code courseCode} is invalid
     */
    public static String parseCourseCode(String courseCode) throws ParseException {
        requireNonNull(courseCode);
        String trimmed = courseCode.trim().toUpperCase();

        if (!trimmed.matches("[A-Z0-9]{2,10}")) {
            throw new ParseException(Course.MESSAGE_CONSTRAINTS);
        }

        return trimmed;
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
        if (!trimmed.matches("[A-Za-z0-9]{6,12}")) {
            throw new ParseException("\u274C Invalid student ID. Example: id/A0123456X");
        }
        return trimmed;
    }

    /**
     * Parses a student name string.
     * Leading/trailing spaces are trimmed; multiple internal whitespace is collapsed to single spaces.
     *
     * Acceptable values: 1-60 chars, letters/spaces and common punctuation.
     */
    public static String parseStudentName(String value) throws ParseException {
        requireNonNull(value);
        String collapsed = value.trim().replaceAll("\\s+", " ");
        if (collapsed.isEmpty()
                || collapsed.length() > 60
                || !collapsed.matches("[\\p{L} .,'-]+")) {
            throw new ParseException("\u274C Invalid name. Example: n/John Tan");
        }
        return collapsed;
    }
}
