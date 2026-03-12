package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assessment.Assessment;
import seedu.address.model.grade.Grade;
import seedu.address.model.grade.Score;
import seedu.address.model.person.Person;
import seedu.address.model.student.StudentId;

public class RemoveGradeCommand extends Command {

    public static final String COMMAND_WORD = "removegrade";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes a student's grade for an assessment.\n"
            + "Parameters: s/STUDENT_INDEX as/ASSESSMENT_INDEX\n"
            + "Example: " + COMMAND_WORD + " s/1 as/1";

    public static final String MESSAGE_SUCCESS = "Removed grade: %1$s";
    public static final String MESSAGE_GRADE_NOT_FOUND = "No grade exists for this student and assessment.";
    public static final String MESSAGE_INVALID_STUDENT_INDEX = "The student index provided is invalid.";
    public static final String MESSAGE_INVALID_ASSESSMENT_INDEX = "The assessment index provided is invalid.";

    private final Index studentIndex;
    private final Index assessmentIndex;

    public RemoveGradeCommand(Index studentIndex, Index assessmentIndex) {
        requireNonNull(studentIndex);
        requireNonNull(assessmentIndex);

        this.studentIndex = studentIndex;
        this.assessmentIndex = assessmentIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        ObservableList<Person> studentList = model.getFilteredPersonList();
        ObservableList<Assessment> assessmentList = model.getAssessmentList();

        if (studentIndex.getZeroBased() >= studentList.size()) {
            throw new CommandException(MESSAGE_INVALID_STUDENT_INDEX);
        }

        if (assessmentIndex.getZeroBased() >= assessmentList.size()) {
            throw new CommandException(MESSAGE_INVALID_ASSESSMENT_INDEX);
        }

        Person student = studentList.get(studentIndex.getZeroBased());
        Assessment assessment = assessmentList.get(assessmentIndex.getZeroBased());

        StudentId studentId = new StudentId(student.getEmail().value);

        /*
         * Score is not part of grade identity.
         * We only need the student + assessment pair to identify the grade.
         * If your Grade class does not allow this, add an overloaded constructor
         * or a helper method in the model to find/remove by identity.
         */
        Grade gradeToRemove = new Grade(studentId, assessment.getAssessmentName(), new Score("0"));

        if (!model.hasGrade(gradeToRemove)) {
            throw new CommandException(MESSAGE_GRADE_NOT_FOUND);
        }

        model.removeGrade(gradeToRemove);
        return new CommandResult(String.format(MESSAGE_SUCCESS, gradeToRemove));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof RemoveGradeCommand
                        && studentIndex.equals(((RemoveGradeCommand) other).studentIndex)
                        && assessmentIndex.equals(((RemoveGradeCommand) other).assessmentIndex));
    }
}
