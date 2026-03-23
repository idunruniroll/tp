package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assessment.Assessment;
import seedu.address.model.grade.Grade;
import seedu.address.model.person.Person;
import seedu.address.model.student.StudentId;

/**
 * Removes a grade for a student from an assessment.
 */
public class RemoveGradeCommand extends Command {

    public static final String COMMAND_WORD = "removegrade";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes a grade for a student for an assessment.\n"
            + "Parameters: c/COURSE_CODE s/STUDENT_INDEX as/ASSESSMENT_INDEX\n"
            + "Example: " + COMMAND_WORD + " c/CS2103T s/1 as/2";

    public static final String MESSAGE_SUCCESS = "Removed grade: Student id: %1$s, Assessment name: %2$s in Course: %3$s";
    public static final String MESSAGE_INVALID_STUDENT_INDEX = "The student index provided is invalid.";
    public static final String MESSAGE_INVALID_ASSESSMENT_INDEX = "The assessment index provided is invalid.";
    public static final String MESSAGE_INVALID_COURSE_CODE = "Invalid course code.";
    public static final String MESSAGE_GRADE_NOT_FOUND = "Grade not found.";

    private final String courseCode;
    private final Index studentIndex;
    private final Index assessmentIndex;

    /**
     * Constructs a RemoveGradeCommand with the specified parameters.
     *
     * @param courseCode the course code
     * @param studentIndex the student index
     * @param assessmentIndex the assessment index
     */
    public RemoveGradeCommand(String courseCode, Index studentIndex, Index assessmentIndex) {
        requireNonNull(courseCode);
        requireNonNull(studentIndex);
        requireNonNull(assessmentIndex);

        this.courseCode = courseCode.trim().toUpperCase();
        this.studentIndex = studentIndex;
        this.assessmentIndex = assessmentIndex;
    }

    /**
     * Executes the remove grade command.
     *
     * @param model the model
     * @return command result with success message
     * @throws CommandException if the student, assessment, or grade is not found
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        ObservableList<Person> studentList = model.getFilteredPersonList();

        if (studentIndex.getZeroBased() >= studentList.size()) {
            throw new CommandException(MESSAGE_INVALID_STUDENT_INDEX);
        }

        Person student = studentList.get(studentIndex.getZeroBased());

        List<Assessment> courseAssessments = model.getAssessmentList().stream()
                .filter(assessment -> assessment.getCourseCode().equalsIgnoreCase(courseCode))
                .collect(Collectors.toList());

        if (courseAssessments.isEmpty()) {
            throw new CommandException(MESSAGE_INVALID_COURSE_CODE);
        }

        if (assessmentIndex.getZeroBased() >= courseAssessments.size()) {
            throw new CommandException(MESSAGE_INVALID_ASSESSMENT_INDEX);
        }

        Assessment assessment = courseAssessments.get(assessmentIndex.getZeroBased());

        StudentId studentId = new StudentId(student.getEmail().value);
        Grade toRemove = new Grade(studentId, assessment.getAssessmentName(), courseCode);

        if (!model.hasGrade(toRemove)) {
            throw new CommandException(MESSAGE_GRADE_NOT_FOUND);
        }

        model.removeGrade(toRemove);
        return new CommandResult(String.format(MESSAGE_SUCCESS,
                studentId,
                assessment.getAssessmentName(),
                courseCode));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof RemoveGradeCommand
                        && courseCode.equalsIgnoreCase(((RemoveGradeCommand) other).courseCode)
                        && studentIndex.equals(((RemoveGradeCommand) other).studentIndex)
                        && assessmentIndex.equals(((RemoveGradeCommand) other).assessmentIndex));
    }
}
