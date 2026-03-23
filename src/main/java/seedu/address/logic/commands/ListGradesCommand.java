package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSESSMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_CODE;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assessment.Assessment;
import seedu.address.model.assessment.MaxScore;
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
            + "  sid/STUDENT_ID\n"
            + "Examples:\n"
            + "  " + COMMAND_WORD + " " + PREFIX_COURSE_CODE + "CS2103T\n"
            + "  " + COMMAND_WORD + " " + PREFIX_COURSE_CODE + "CS2103T " + PREFIX_ASSESSMENT + "1\n"
            + "  " + COMMAND_WORD + " sid/A0123456X";

    public static final String MESSAGE_COURSE_REQUIRED =
            "Please specify a course code. Example: listgrades c/CS2103T";
    public static final String MESSAGE_COURSE_NOT_FOUND = "Course %1$s not found.";
    public static final String MESSAGE_INVALID_ASSESSMENT_INDEX =
            "The assessment index provided is invalid.";

    private final String filterType;
    private final String filterValue1;
    private final Index assessmentIndex; // Only used for course assessment filter

    /**
     * Retrieves the filtered grades based on the filter type.
     *
     */
    public ListGradesCommand(String filterType, String filterValue1, Index assessmentIndex) {
        this.filterType = filterType;

        if (filterType.equalsIgnoreCase("course") || filterType.equalsIgnoreCase("courseassessment")) {
            this.filterValue1 = filterValue1.trim().toUpperCase();
        } else {
            this.filterValue1 = filterValue1;
        }

        this.assessmentIndex = assessmentIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if ((filterType.equalsIgnoreCase("course")
                || filterType.equalsIgnoreCase("courseassessment"))
                && !model.hasCourse(filterValue1)) {
            throw new CommandException(String.format(MESSAGE_COURSE_NOT_FOUND, filterValue1));
        }

        ObservableList<Grade> grades = getFilteredGrades(model);

        if (grades.isEmpty()) {
            return new CommandResult("No grades found.");
        }

        Map<String, Map<String, List<Grade>>> groupedGrades = groupGradesByCourseAndAssessment(grades);

        StringBuilder sb = new StringBuilder("Grades:\n");
        Set<String> printedCourses = new HashSet<>();

        for (Assessment assessment : model.getAssessmentList()) {
            String courseCode = assessment.getCourseCode().toUpperCase();

            if (groupedGrades.containsKey(courseCode)) {
                sb.append(buildGradesByCourseOutput(courseCode, groupedGrades.get(courseCode),
                        model.getAssessmentList(), printedCourses));
            }
        }

        return new CommandResult(sb.toString().trim());
    }


    private ObservableList<Grade> getFilteredGrades(Model model) throws CommandException {
        switch (filterType.toLowerCase()) {
            case "student":
                return model.getGradesByStudentId(filterValue1);

            case "course":
                return FXCollections.observableArrayList(model.getGradesByCourse(filterValue1));

            case "courseassessment":
                List<Assessment> courseAssessments = model.getAssessmentList().stream()
                        .filter(assessment -> assessment.getCourseCode().equalsIgnoreCase(filterValue1))
                        .collect(Collectors.toList());

                if (courseAssessments.isEmpty()
                        || assessmentIndex == null
                        || assessmentIndex.getZeroBased() >= courseAssessments.size()) {
                    throw new CommandException(MESSAGE_INVALID_ASSESSMENT_INDEX);
                }

                Assessment selectedAssessment = courseAssessments.get(assessmentIndex.getZeroBased());
                String storedCourseCode = selectedAssessment.getCourseCode();
                String assessmentName = selectedAssessment.getAssessmentName().toString();

                return FXCollections.observableArrayList(
                        model.getGradesByCourseAndAssessment(storedCourseCode, assessmentName));

            default:
                throw new IllegalArgumentException(
                        "Invalid filter type. Use 'student', 'course', or 'courseassessment'.");
        }
    }

    /**
     * Groups the grades by course and then by assessment.
     */
    private Map<String, Map<String, List<Grade>>> groupGradesByCourseAndAssessment(ObservableList<Grade> grades) {
        return grades.stream()
                .collect(Collectors.groupingBy(grade -> grade.getCourseCode().toUpperCase(),
                        Collectors.groupingBy(grade -> grade.getAssessmentName().toString())));
    }
    /**
     * Builds the output for each course, iterating over the assessments
     */
    private String buildGradesByCourseOutput(String courseCode, Map<String, List<Grade>> assessmentsForCourse,
            ObservableList<Assessment> assessments, Set<String> printedCourses) {
        StringBuilder sb = new StringBuilder();

        // Skip if this course has already been printed
        if (printedCourses.contains(courseCode.toUpperCase())) {
            return "";
        }

        printedCourses.add(courseCode.toUpperCase()); // Mark this course as processed

        sb.append("\nCourse: ").append(courseCode).append("\n");

        int assessmentIndex = 1;

        // Iterate over each assessment in the course, in the same order as
        // listassessments
        for (Assessment currentAssessment : assessments) {
            if (!currentAssessment.getCourseCode().equalsIgnoreCase(courseCode)) {
                continue;
            }

            String assessmentName = currentAssessment.getAssessmentName().toString();
            List<Grade> courseGrades = assessmentsForCourse.get(assessmentName);

            if (courseGrades != null && !courseGrades.isEmpty()) {
                sb.append(generateAssessmentOutput(assessmentName, assessmentIndex, courseGrades, currentAssessment));
            }

            assessmentIndex++;
        }

        return sb.toString();
    }

    /**
     * Generates the output for a specific assessment.
     */
    private String generateAssessmentOutput(String assessmentName, int assessmentIndex,
            List<Grade> courseGrades, Assessment assessment) {
        StringBuilder sb = new StringBuilder();

        MaxScore maxScore = assessment.getMaxScore();

        sb.append("  Assessment: ").append(assessmentName)
                .append(" (Max Score: ").append(maxScore).append(")")
                .append(" (Index: ").append(assessmentIndex).append(")\n");

        int studentIndex = 1;
        for (Grade grade : courseGrades) {
            sb.append(studentIndex).append(".    Student ID: ").append(grade.getStudentId())
                    .append(", Grade: ").append(grade.getScore()).append("\n");
            studentIndex++;
        }

        sb.append("\n");
        return sb.toString();
    }
}
