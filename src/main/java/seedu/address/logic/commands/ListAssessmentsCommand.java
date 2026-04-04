package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.DisplayMode;
import seedu.address.model.Model;

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

    private final String courseCode;

    public ListAssessmentsCommand() {
        this.courseCode = null;
    }

    public ListAssessmentsCommand(String courseCode) {
        this.courseCode = courseCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (courseCode == null) {
            model.updateFilteredAssessmentList(assessment -> true);
            model.setDisplayMode(DisplayMode.ASSESSMENTS);

            if (model.getFilteredAssessmentList().isEmpty()) {
                return new CommandResult(Messages.MESSAGE_NO_ASSESSMENTS);
            }
            return new CommandResult(Messages.MESSAGE_LIST_ASSESSMENTS_SUCCESS);
        }

        if (!model.hasCourse(courseCode)) {
            throw new CommandException(String.format(Messages.MESSAGE_COURSE_NOT_FOUND, courseCode));
        }

        model.updateFilteredAssessmentList(
                assessment -> assessment.getCourseCode().equalsIgnoreCase(courseCode));
        model.setDisplayMode(DisplayMode.ASSESSMENTS);

        if (model.getFilteredAssessmentList().isEmpty()) {
            return new CommandResult(String.format(Messages.MESSAGE_NO_ASSESSMENTS_FOR_COURSE, courseCode));
        }

        return new CommandResult(String.format(Messages.MESSAGE_LIST_ASSESSMENTS_SUCCESS_FILTERED, courseCode));
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
