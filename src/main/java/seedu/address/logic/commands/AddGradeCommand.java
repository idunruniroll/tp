package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
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

    public static final String MESSAGE_SUCCESS = "New grade added: %1$s";
    public static final String MESSAGE_DUPLICATE_GRADE = "This grade already exists for the student and assessment.";
    public static final String MESSAGE_INVALID_STUDENT_ID = "The student ID provided is not enrolled in this course.";
    public static final String MESSAGE_INVALID_ASSESSMENT_INDEX = "The assessment index provided is invalid.";
    public static final String MESSAGE_SCORE_EXCEEDS_MAX = "Score cannot be more than the assessment max score.";
    public static final String MESSAGE_INVALID_COURSE_CODE = "Invalid course code.";

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

        if (model.getCourse(courseCode).isEmpty()) {
            throw new CommandException(MESSAGE_INVALID_COURSE_CODE);
        }

        boolean studentExistsInCourse = model.getCourse(courseCode).get().getStudents().stream()
                .anyMatch(student -> student.getStudentId().equalsIgnoreCase(studentId));

        if (!studentExistsInCourse) {
            throw new CommandException(MESSAGE_INVALID_STUDENT_ID);
        }

        List<Assessment> courseAssessments = model.getAssessmentList().stream()
                .filter(assessment -> assessment.getCourseCode().equalsIgnoreCase(courseCode))
                .collect(Collectors.toList());

        if (assessmentIndex.getZeroBased() >= courseAssessments.size()) {
            throw new CommandException(MESSAGE_INVALID_ASSESSMENT_INDEX);
        }

        Assessment assessment = courseAssessments.get(assessmentIndex.getZeroBased());

        if (score.toDouble() > assessment.getMaxScore().toDouble()) {
            throw new CommandException("Score cannot be more than the assessment max score.");
        }

        Grade toAdd = new Grade(courseCode, new StudentId(studentId), assessment.getAssessmentName(), score);

        if (model.hasGrade(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_GRADE);
        }

        model.addGrade(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
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
