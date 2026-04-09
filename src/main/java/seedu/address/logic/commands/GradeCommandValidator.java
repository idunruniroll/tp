package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assessment.Assessment;

/**
 * Shared grade command validation utilities.
 */
final class GradeCommandValidator {

    private GradeCommandValidator() {
    }

    /**
     * Validates course existence, student enrollment and assessment index, and returns the resolved assessment.
     */
    static Assessment validateAndGetAssessment(Model model, String courseCode, String studentId, Index assessmentIndex)
            throws CommandException {
        requireNonNull(model);
        requireNonNull(courseCode);
        requireNonNull(studentId);
        requireNonNull(assessmentIndex);

        if (!model.hasCourse(courseCode)) {
            throw new CommandException(String.format(Messages.MESSAGE_COURSE_NOT_FOUND, courseCode));
        }

        if (!model.isStudentEnrolled(courseCode, studentId)) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_ID);
        }

        return model.getAssessmentForCourseByIndex(courseCode, assessmentIndex)
                .orElseThrow(() -> new CommandException(Messages.MESSAGE_INVALID_ASSESSMENT_INDEX));
    }
}
