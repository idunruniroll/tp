package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSESSMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_ID;

import java.util.HashSet;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.DisplayMode;
import seedu.address.model.Model;
import seedu.address.model.assessment.Assessment;
import seedu.address.model.grade.Grade;

/**
 * Lists grades filtered by type and values (student, course, or assessment).
 */
public class ListGradesCommand extends Command {

    public static final String COMMAND_WORD = "listgrades";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists grades for a course, a course assessment, or a student.\n"
            + "Parameters:\n"
            + "  " + PREFIX_COURSE_CODE + "COURSE_CODE\n"
            + "  " + PREFIX_COURSE_CODE + "COURSE_CODE " + PREFIX_ASSESSMENT + "ASSESSMENT_INDEX\n"
            + "  " + PREFIX_STUDENT_ID + "STUDENT_ID\n"
            + "Examples:\n"
            + "  " + COMMAND_WORD + " " + PREFIX_COURSE_CODE + "CS2103T\n"
            + "  " + COMMAND_WORD + " " + PREFIX_COURSE_CODE + "CS2103T " + PREFIX_ASSESSMENT + "1\n"
            + "  " + COMMAND_WORD + " " + PREFIX_STUDENT_ID + "A0123456X";

    /**
     * Supported grade listing filters.
     */
    public enum FilterType {
        STUDENT, COURSE, COURSE_ASSESSMENT
    }

    private final FilterType filterType;
    private final String filterValue1;
    private final Index assessmentIndex;

    /**
     * Constructs a ListGradesCommand with the specified filter type and values.
     * @param filterType      the type of filter
     * @param filterValue1    the value for the filter
     * @param assessmentIndex the index of the assessment
     */
    public ListGradesCommand(FilterType filterType, String filterValue1, Index assessmentIndex) {
        requireNonNull(filterType);
        requireNonNull(filterValue1);
        this.filterType = filterType;

        this.filterValue1 = (this.filterType == FilterType.COURSE || this.filterType == FilterType.COURSE_ASSESSMENT)
                ? filterValue1.trim().toUpperCase()
                : filterValue1;

        this.assessmentIndex = assessmentIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if ((filterType == FilterType.COURSE || filterType == FilterType.COURSE_ASSESSMENT)
                && !model.hasCourse(filterValue1)) {
            throw new CommandException(String.format(Messages.MESSAGE_COURSE_NOT_FOUND, filterValue1));
        }

        ObservableList<Grade> grades = getFilteredGrades(model);

        if (grades.isEmpty()) {
            model.updateFilteredGradeList(grade -> false);
            model.setCurrentCourseForDisplay(java.util.Optional.empty());
            model.setDisplayMode(DisplayMode.GRADES);
            return new CommandResult(Messages.MESSAGE_NO_GRADES_FOUND);
        }

        Set<Grade> selectedGrades = new HashSet<>(grades);
        model.updateFilteredGradeList(selectedGrades::contains);
        model.setCurrentCourseForDisplay(java.util.Optional.empty());
        model.setDisplayMode(DisplayMode.GRADES);

        return new CommandResult(Messages.MESSAGE_LIST_GRADES_SUCCESS);
    }

    private ObservableList<Grade> getFilteredGrades(Model model) throws CommandException {
        switch (filterType) {
        case STUDENT:
            return model.getGradesByStudentId(filterValue1);
        case COURSE:
            return FXCollections.observableArrayList(model.getGradesByCourse(filterValue1));
        case COURSE_ASSESSMENT:
            ObservableList<Assessment> courseAssessments = model
                    .getAssessmentsForCourseInDisplayOrder(filterValue1);

            if (courseAssessments.isEmpty()
                    || assessmentIndex == null
                    || assessmentIndex.getZeroBased() >= courseAssessments.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_ASSESSMENT_INDEX);
            }

            Assessment selectedAssessment = courseAssessments.get(assessmentIndex.getZeroBased());
            String storedCourseCode = selectedAssessment.getCourseCode();
            String assessmentName = selectedAssessment.getAssessmentName().toString();

            return FXCollections.observableArrayList(
                    model.getGradesByCourseAndAssessment(storedCourseCode, assessmentName));
        default:
            throw new IllegalStateException("Invalid filter type");
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ListGradesCommand)) {
            return false;
        }
        ListGradesCommand otherCommand = (ListGradesCommand) other;
        return filterType.equals(otherCommand.filterType)
            && filterValue1.equals(otherCommand.filterValue1)
            && java.util.Objects.equals(assessmentIndex, otherCommand.assessmentIndex);
    }
}
