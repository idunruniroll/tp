package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSESSMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_ID;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.DisplayMode;
import seedu.address.model.Model;
import seedu.address.model.assessment.Assessment;
import seedu.address.model.grade.Grade;
import seedu.address.model.student.StudentId;

/**
 * Removes a grade for a student from an assessment.
 */
public class RemoveGradeCommand extends Command {

    public static final String COMMAND_WORD = "removegrade";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Removes a grade for a student for an assessment.\n"
        + "Parameters: "
        + PREFIX_COURSE_CODE + "COURSE_CODE "
        + PREFIX_STUDENT_ID + "STUDENT_ID "
        + PREFIX_ASSESSMENT + "ASSESSMENT_INDEX\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_COURSE_CODE + "CS2103T "
        + PREFIX_STUDENT_ID + "A0123456X "
        + PREFIX_ASSESSMENT + "2";

    private final String courseCode;
    private final String studentId;
    private final Index assessmentIndex;

    /**
     * Constructs a RemoveGradeCommand with the specified parameters.
     * @param courseCode      the course code
     * @param studentId       the student ID
     * @param assessmentIndex the assessment index
     */
    public RemoveGradeCommand(String courseCode, String studentId, Index assessmentIndex) {
        requireNonNull(courseCode);
        requireNonNull(studentId);
        requireNonNull(assessmentIndex);

        this.courseCode = courseCode.trim().toUpperCase();
        this.studentId = studentId.trim().toUpperCase();
        this.assessmentIndex = assessmentIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Assessment assessment = GradeCommandValidator.validateAndGetAssessment(
                model, courseCode, studentId, assessmentIndex);

        Grade toRemove = new Grade(new StudentId(studentId), assessment.getAssessmentName(), courseCode);
        if (!model.hasGrade(toRemove)) {
            throw new CommandException(Messages.MESSAGE_GRADE_NOT_FOUND);
        }

        model.removeGrade(toRemove);
        model.showGradesForCourseAssessment(
                courseCode,
                assessment.getAssessmentName().toString());
        model.setDisplayMode(DisplayMode.GRADES);
        return new CommandResult(String.format(Messages.MESSAGE_REMOVE_GRADE_SUCCESS,
                studentId,
                assessment.getAssessmentName(),
                courseCode));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof RemoveGradeCommand
                && courseCode.equalsIgnoreCase(((RemoveGradeCommand) other).courseCode)
                && studentId.equalsIgnoreCase(((RemoveGradeCommand) other).studentId)
                && assessmentIndex.equals(((RemoveGradeCommand) other).assessmentIndex));
    }
}
