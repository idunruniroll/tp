package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assessment.Assessment;

/**
 * Removes an assessment from a course.
 */
public class RemoveAssessmentCommand extends Command {

    public static final String COMMAND_WORD = "removeassessment";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes the assessment identified by the "
            + "course and index number used in the displayed assessment list.\n"
            + "Parameters: c/COURSE_CODE as/ASSESSMENT_INDEX\n"
            + "Example: " + COMMAND_WORD + " c/CS2103T as/1";

    private final String courseCode;
    private final Index assessmentIndex;

    /**
     * Constructs a RemoveAssessmentCommand with the specified course code and assessment index.
     *
     * @param courseCode the course code
     * @param assessmentIndex the assessment index
     */
    public RemoveAssessmentCommand(String courseCode, Index assessmentIndex) {
        requireNonNull(courseCode);
        requireNonNull(assessmentIndex);

        this.courseCode = courseCode;
        this.assessmentIndex = assessmentIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Get the list of assessments for the given course code
        ObservableList<Assessment> filteredAssessments =
            model.getAssessmentsForCourseInDisplayOrder(courseCode);

        if (filteredAssessments.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_INVALID_COURSE);
        }

        if (assessmentIndex.getZeroBased() >= filteredAssessments.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ASSESSMENT_INDEX);
        }

        Assessment assessmentToRemove = filteredAssessments.get(assessmentIndex.getZeroBased());
        model.removeAssessment(assessmentToRemove);

        return new CommandResult(String.format(Messages.MESSAGE_REMOVE_ASSESSMENT_SUCCESS, assessmentToRemove));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof RemoveAssessmentCommand
                        && courseCode.equals(((RemoveAssessmentCommand) other).courseCode)
                        && assessmentIndex.equals(((RemoveAssessmentCommand) other).assessmentIndex));
    }
}
