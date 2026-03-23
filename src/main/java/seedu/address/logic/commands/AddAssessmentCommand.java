package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assessment.Assessment;
import seedu.address.model.assessment.AssessmentName;
import seedu.address.model.assessment.MaxScore;

/**
 * Adds an assessment to the address book.
 */
public class AddAssessmentCommand extends Command {

    public static final String COMMAND_WORD = "addassessment";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an assessment.\n"
            + "Parameters: "
            + "c/COURSE_CODE "
            + "an/ASSESSMENT_NAME "
            + "m/MAX_SCORE\n"
            + "Example: " + COMMAND_WORD + " "
            + "c/CS2103T "
            + "an/Midterm "
            + "m/100";

    public static final String MESSAGE_SUCCESS = "New assessment added: %1$s";
    public static final String MESSAGE_DUPLICATE_ASSESSMENT = "This assessment already exists.";
    public static final String MESSAGE_COURSE_NOT_FOUND = "Course %1$s not found.";

    private final String courseCode;
    private final AssessmentName assessmentName;
    private final MaxScore maxScore;

    /**
     * Constructs an AddAssessmentCommand with the specified course code, assessment name, and max score.
     *
     * @param courseCode the course code
     * @param assessmentName the assessment name
     * @param maxScore the maximum score
     */
    public AddAssessmentCommand(String courseCode, String assessmentName, String maxScore) {
        requireNonNull(courseCode);
        requireNonNull(assessmentName);
        requireNonNull(maxScore);

        this.courseCode = courseCode;
        this.assessmentName = new AssessmentName(assessmentName);
        this.maxScore = new MaxScore(maxScore);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.getCourse(courseCode).isEmpty()) {
            throw new CommandException(String.format(MESSAGE_COURSE_NOT_FOUND, courseCode));
        }

        // Create the assessment object using the provided courseCode, assessmentName,
        // and maxScore
        Assessment assessment = new Assessment(courseCode, assessmentName, maxScore);

        if (model.hasAssessment(assessment)) {
            throw new CommandException(MESSAGE_DUPLICATE_ASSESSMENT);
        }

        model.addAssessment(assessment);
        return new CommandResult(String.format(MESSAGE_SUCCESS, assessment));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AddAssessmentCommand
                        && courseCode.equals(((AddAssessmentCommand) other).courseCode)
                        && assessmentName.equals(((AddAssessmentCommand) other).assessmentName)
                        && maxScore.equals(((AddAssessmentCommand) other).maxScore));
    }
}
