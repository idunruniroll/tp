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

public class AddGradeCommand extends Command {

    public static final String COMMAND_WORD = "addgrade";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a grade for a student for an assessment.\n"
            + "Parameters: s/STUDENT_INDEX as/ASSESSMENT_INDEX g/SCORE\n"
            + "Example: " + COMMAND_WORD + " s/1 as/1 g/85";

    public static final String MESSAGE_SUCCESS = "New grade added: %1$s";
    public static final String MESSAGE_DUPLICATE_GRADE = "This grade already exists for the student and assessment.";
    public static final String MESSAGE_INVALID_STUDENT_INDEX = "The student index provided is invalid.";
    public static final String MESSAGE_INVALID_ASSESSMENT_INDEX = "The assessment index provided is invalid.";
    public static final String MESSAGE_SCORE_EXCEEDS_MAX = "Score cannot be more than the assessment max score.";

    private final Index studentIndex;
    private final Index assessmentIndex;
    private final Score score;

    public AddGradeCommand(Index studentIndex, Index assessmentIndex, Score score) {
        requireNonNull(studentIndex);
        requireNonNull(assessmentIndex);
        requireNonNull(score);

        this.studentIndex = studentIndex;
        this.assessmentIndex = assessmentIndex;
        this.score = score;
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

        if (score.value > assessment.getMaxScore().value) {
            throw new CommandException(MESSAGE_SCORE_EXCEEDS_MAX);
        }

        StudentId studentId = new StudentId(student.getEmail().value);
        Grade toAdd = new Grade(studentId, assessment.getAssessmentName(), score);

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
                        && studentIndex.equals(((AddGradeCommand) other).studentIndex)
                        && assessmentIndex.equals(((AddGradeCommand) other).assessmentIndex)
                        && score.equals(((AddGradeCommand) other).score));
    }
}
