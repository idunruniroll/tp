package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSESSMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_ID;

import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.DisplayMode;
import seedu.address.model.Model;
import seedu.address.model.assessment.Assessment;

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
     *
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
        validateCourseExistsIfNeeded(model);
        applyGradeFilter(model);
        prepareGradeDisplay(model);
        return buildResult(model);
    }

    /**
     * Validates that the referenced course exists when the filter type requires a
     * course.
     *
     * @param model the model to query
     * @throws CommandException if the course does not exist
     */
    private void validateCourseExistsIfNeeded(Model model) throws CommandException {
        boolean requiresCourse = filterType == FilterType.COURSE || filterType == FilterType.COURSE_ASSESSMENT;
        if (requiresCourse && !model.hasCourse(filterValue1)) {
            throw new CommandException(String.format(Messages.MESSAGE_COURSE_NOT_FOUND, filterValue1));
        }
    }

    /**
     * Applies the correct saved grade filter based on the command's filter type.
     *
     * @param model the model to update
     * @throws CommandException if the assessment index is invalid
     */
    private void applyGradeFilter(Model model) throws CommandException {
        switch (filterType) {
        case STUDENT:
            model.showGradesForStudent(filterValue1);
            break;
        case COURSE:
            model.showGradesForCourse(filterValue1);
            break;
        case COURSE_ASSESSMENT:
            applyCourseAssessmentFilter(model);
            break;
        default:
            throw new IllegalStateException("Invalid filter type");
        }
    }

    /**
     * Applies the grade filter for a specific assessment within a course.
     *
     * @param model the model to update
     * @throws CommandException if the assessment index is invalid
     */
    private void applyCourseAssessmentFilter(Model model) throws CommandException {
        Assessment selectedAssessment = getSelectedAssessment(model);
        model.showGradesForCourseAssessment(
                selectedAssessment.getCourseCode(),
                selectedAssessment.getAssessmentName().toString());
    }

    /**
     * Returns the selected assessment for the course-assessment filter.
     *
     * @param model the model to query
     * @return the selected assessment
     * @throws CommandException if the assessment index is invalid
     */
    private Assessment getSelectedAssessment(Model model) throws CommandException {
        ObservableList<Assessment> courseAssessments = model.getAssessmentsForCourseInDisplayOrder(filterValue1);

        if (isInvalidAssessmentIndex(courseAssessments)) {
            throw new CommandException(Messages.MESSAGE_INVALID_ASSESSMENT_INDEX);
        }

        return courseAssessments.get(assessmentIndex.getZeroBased());
    }

    /**
     * Returns true if the assessment index does not point to a valid assessment.
     *
     * @param courseAssessments the assessments in display order for the course
     * @return true if the index is invalid
     */
    private boolean isInvalidAssessmentIndex(ObservableList<Assessment> courseAssessments) {
        return courseAssessments.isEmpty()
                || assessmentIndex == null
                || assessmentIndex.getZeroBased() >= courseAssessments.size();
    }

    /**
     * Prepares the UI to show the grade list.
     *
     * @param model the model to update
     */
    private void prepareGradeDisplay(Model model) {
        model.setCurrentCourseForDisplay(Optional.empty());
        model.setDisplayMode(DisplayMode.GRADES);
    }

    /**
     * Builds the command result based on whether any grades are currently shown.
     *
     * @param model the model to query
     * @return the command result
     */
    private CommandResult buildResult(Model model) {
        if (model.getFilteredGradeList().isEmpty()) {
            return new CommandResult(Messages.MESSAGE_NO_GRADES_FOUND);
        }
        return new CommandResult(Messages.MESSAGE_LIST_GRADES_SUCCESS);
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
