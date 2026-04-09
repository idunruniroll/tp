package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "\u274C Invalid command format!\n%1$s \n";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_COURSE_NOT_FOUND = "Course %1$s not found.";
    public static final String MESSAGE_DUPLICATE_ASSESSMENT = "This assessment already exists.";
    public static final String MESSAGE_SIMILAR_ASSESSMENT = "A similar assessment already exists: %1$s";
    public static final String MESSAGE_INVALID_ASSESSMENT_NAME = "Invalid assessment name.";
    public static final String MESSAGE_INVALID_MAX_SCORE = "Invalid max score.";
    public static final String MESSAGE_ADD_ASSESSMENT_SUCCESS = "New assessment added: %1$s";
    public static final String MESSAGE_INVALID_STUDENT_ID = "The student ID provided is not enrolled in this course.";
    public static final String MESSAGE_INVALID_STUDENT_ID_FORMAT = "\u274C Invalid student ID. Example: id/A0123456X";
    public static final String MESSAGE_INVALID_STUDENT_NAME_FORMAT = "\u274C Invalid name. Example: n/John Tan";
    public static final String MESSAGE_INVALID_ASSESSMENT_INDEX = "The assessment index provided is invalid.";

    public static final String MESSAGE_ADD_GRADE_SUCCESS = "New grade added: %1$s";
    public static final String MESSAGE_DUPLICATE_GRADE = "This grade already exists for the student and assessment.";
    public static final String MESSAGE_SCORE_EXCEEDS_MAX = "Score cannot be more than the assessment max score.";

    public static final String MESSAGE_REMOVE_GRADE_SUCCESS =
        "Removed grade: Student ID: %1$s, Assessment name: %2$s in Course: %3$s";
    public static final String MESSAGE_GRADE_NOT_FOUND = "Grade not found.";

    public static final String MESSAGE_REMOVE_ASSESSMENT_SUCCESS = "Removed assessment: %1$s";

    public static final String MESSAGE_LIST_GRADES_SUCCESS = "Displayed grades.";
    public static final String MESSAGE_NO_GRADES_FOUND = "No grades found.";

    public static final String MESSAGE_LIST_ASSESSMENTS_SUCCESS = "Listed all assessments";
    public static final String MESSAGE_LIST_ASSESSMENTS_SUCCESS_FILTERED = "Listed all assessments for course: %1$s";
    public static final String MESSAGE_NO_ASSESSMENTS = "No assessments found.";
    public static final String MESSAGE_NO_ASSESSMENTS_FOR_COURSE = "No assessments found for course: %1$s";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

}
