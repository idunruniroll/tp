package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.model.DisplayMode;
import seedu.address.model.Model;
import seedu.address.model.assessment.Assessment;

/**
 * Lists all assessments in the system, optionally filtered by course.
 */
public class ListAssessmentsCommand extends Command {

    public static final String COMMAND_WORD = "listassessments";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all assessments, optionally filtered by course.\n"
            + "Parameters: [c/COURSE_CODE]\n"
            + "Example: " + COMMAND_WORD + "\n"
            + "Example: " + COMMAND_WORD + " c/CS2103T";

    public static final String MESSAGE_SUCCESS = "Listed all assessments";
    public static final String MESSAGE_SUCCESS_FILTERED = "Listed all assessments for course: %1$s";
    public static final String MESSAGE_NO_ASSESSMENTS = "No assessments found.";
    public static final String MESSAGE_NO_ASSESSMENTS_FOR_COURSE = "No assessments found for course: %1$s";

    private final String courseCode;

    public ListAssessmentsCommand() {
        this.courseCode = null;
    }

    public ListAssessmentsCommand(String courseCode) {
        this.courseCode = courseCode;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        ObservableList<Assessment> allAssessments = model.getAssessmentList();

        if (allAssessments.isEmpty()) {
            return new CommandResult(MESSAGE_NO_ASSESSMENTS);
        }

        if (courseCode == null) {
            model.updateFilteredAssessmentList(assessment -> true);
            model.setDisplayMode(DisplayMode.ASSESSMENTS);
            return new CommandResult(MESSAGE_SUCCESS);
        }

        model.updateFilteredAssessmentList(
                assessment -> assessment.getCourseCode().equalsIgnoreCase(courseCode));

        if (model.getFilteredAssessmentList().isEmpty()) {
            model.updateFilteredAssessmentList(assessment -> true);
            return new CommandResult(String.format(MESSAGE_NO_ASSESSMENTS_FOR_COURSE, courseCode));
        }

        model.setDisplayMode(DisplayMode.ASSESSMENTS);
        return new CommandResult(String.format(MESSAGE_SUCCESS_FILTERED, courseCode));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ListAssessmentsCommand)) {
            return false;
        }
        ListAssessmentsCommand otherCommand = (ListAssessmentsCommand) other;
        return Objects.equals(courseCode, otherCommand.courseCode);
    }
}
