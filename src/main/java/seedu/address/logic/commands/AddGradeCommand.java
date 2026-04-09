package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assessment.Assessment;
import seedu.address.model.grade.Grade;
import seedu.address.model.grade.Score;
import seedu.address.model.student.StudentId;

/**
 * Adds a grade for a student on an assessment.
 */
public class AddGradeCommand extends Command {

    public static final String COMMAND_WORD = "addgrade";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a grade for a student for an assessment.\n"
            + "Parameters: c/COURSE_CODE id/STUDENT_ID as/ASSESSMENT_INDEX g/SCORE\n"
            + "Example: " + COMMAND_WORD + " c/CS2103T id/A0123456X as/1 g/85";

    private final String courseCode;
    private final String studentId;
    private final Index assessmentIndex;
    private final Score score;

    /**
     * Constructs an AddGradeCommand with the specified parameters.
     * @param courseCode      the course code
     * @param studentId       the student ID
     * @param assessmentIndex the assessment index
     * @param score           the score
     */
    public AddGradeCommand(String courseCode, String studentId, Index assessmentIndex, Score score) {
        requireNonNull(courseCode);
        requireNonNull(studentId);
        requireNonNull(assessmentIndex);
        requireNonNull(score);

        this.courseCode = courseCode.trim().toUpperCase();
        this.studentId = studentId.trim().toUpperCase();
        this.assessmentIndex = assessmentIndex;
        this.score = score;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Assessment assessment = GradeCommandValidator.validateAndGetAssessment(
                model, courseCode, studentId, assessmentIndex);

        if (score.toDouble() > assessment.getMaxScore().toDouble()) {
            throw new CommandException(Messages.MESSAGE_SCORE_EXCEEDS_MAX);
        }

        Grade toAdd = new Grade(courseCode, new StudentId(studentId), assessment.getAssessmentName(), score);
        if (model.hasGrade(toAdd)) {
            throw new CommandException(Messages.MESSAGE_DUPLICATE_GRADE);
        }

        model.addGrade(toAdd);
        return new CommandResult(String.format(Messages.MESSAGE_ADD_GRADE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof AddGradeCommand
                && courseCode.equalsIgnoreCase(((AddGradeCommand) other).courseCode)
                && studentId.equalsIgnoreCase(((AddGradeCommand) other).studentId)
                && assessmentIndex.equals(((AddGradeCommand) other).assessmentIndex)
                && score.equals(((AddGradeCommand) other).score));
    }
}
