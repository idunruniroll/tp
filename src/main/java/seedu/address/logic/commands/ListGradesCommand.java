package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.model.Model;
import seedu.address.model.assessment.Assessment;
import seedu.address.model.assessment.MaxScore;
import seedu.address.model.grade.Grade;

/**
 * Lists grades filtered by type and values (student, course, or assessment).
 */
public class ListGradesCommand extends Command {

    public static final String COMMAND_WORD = "listgrades";

    private final String filterType;
    private final String filterValue1;
    private final String filterValue2;

    /**
     * Retrieves the filtered grades based on the filter type.
     *
     */
    public ListGradesCommand(String filterType, String filterValue1, String filterValue2) {
        this.filterType = filterType;
        this.filterValue1 = filterValue1;
        this.filterValue2 = filterValue2;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        ObservableList<Grade> grades = getFilteredGrades(model);

        if (grades.isEmpty()) {
            return new CommandResult("No grades found.");
        }

        // Group grades by course, and then by assessment, while keeping the order of
        // assessments
        Map<String, Map<String, List<Grade>>> groupedGrades = groupGradesByCourseAndAssessment(grades);

        StringBuilder sb = new StringBuilder("Grades:\n");

        Set<String> printedCourses = new HashSet<>(); // Track printed courses

        // Generate output for each course
        for (Assessment assessment : model.getAssessmentList()) {
            String courseCode = assessment.getCourseCode();

            // Only generate output for courses that have grades
            if (groupedGrades.containsKey(courseCode)) {
                sb.append(buildGradesByCourseOutput(courseCode, groupedGrades.get(courseCode),
                        model.getAssessmentList(), printedCourses));
            }
        }

        return new CommandResult(sb.toString().trim());
    }


    private ObservableList<Grade> getFilteredGrades(Model model) {
        ObservableList<Grade> grades = null;

        switch (filterType.toLowerCase()) {
        case "student":
            grades = model.getGradesByStudentId(filterValue1);
            break;
        case "course":
            grades = model.getGradesByCourse(filterValue1);
            break;
        case "courseassessment":
            grades = model.getGradesByCourseAndAssessment(filterValue1, filterValue2);
            break;
        default:
            throw new IllegalArgumentException(
                    "Invalid filter type. Use 'student', 'course', or 'courseassessment'.");
        }

        return grades;
    }

    /**
     * Groups the grades by course and then by assessment
     */
    private Map<String, Map<String, List<Grade>>> groupGradesByCourseAndAssessment(ObservableList<Grade> grades) {
        return grades.stream()
                .collect(Collectors.groupingBy(Grade::getCourseCode,
                        Collectors.groupingBy(grade -> grade.getAssessmentName().toString())));
    }

    /**
     * Builds the output for each course, iterating over the assessments
     */
    private String buildGradesByCourseOutput(String courseCode, Map<String, List<Grade>> assessmentsForCourse,
            ObservableList<Assessment> assessments, Set<String> printedCourses) {
        StringBuilder sb = new StringBuilder();

        // Skip if this course has already been printed
        if (printedCourses.contains(courseCode)) {
            return "";
        }

        printedCourses.add(courseCode); // Mark this course as processed

        sb.append("\nCourse: ").append(courseCode).append("\n");

        int assessmentIndex = 1;

        // Iterate over each assessment in the course, in the same order as
        // listassessments
        for (Assessment currentAssessment : assessments) {
            if (!currentAssessment.getCourseCode().equals(courseCode)) {
                continue; // Skip assessments that don't belong to the current course
            }

            String assessmentName = currentAssessment.getAssessmentName().toString();
            List<Grade> courseGrades = assessmentsForCourse.get(assessmentName);

            // Ensure courseGrades is never null, initialize as empty if necessary
            if (courseGrades == null || courseGrades.isEmpty()) {
                continue; // Skip if there are no grades for this assessment
            }

            sb.append(generateAssessmentOutput(assessmentName, assessmentIndex, courseGrades, currentAssessment));

            assessmentIndex++;
        }

        return sb.toString();
    }

    /**
     * Generates the output for a specific assessment
     */
    private String generateAssessmentOutput(String assessmentName, int assessmentIndex,
            List<Grade> courseGrades, Assessment assessment) {
        StringBuilder sb = new StringBuilder();

        // Get the max score from the assessment (assuming it's part of the Assessment
        // class)
        MaxScore maxScore = assessment.getMaxScore(); // You need to ensure this method exists in Assessment class.

        sb.append("  Assessment: ").append(assessmentName)
                .append(" (Max Score: ").append(maxScore).append(")")
                .append(" (Index: ").append(assessmentIndex).append(")\n");

        // List students and their grades, with student index
        int studentIndex = 1;
        for (Grade grade : courseGrades) {
            sb.append(studentIndex + ".    Student ID: ").append(grade.getStudentId())
                    .append(", Grade: ").append(grade.getScore()).append("\n");
            studentIndex++;
        }

        // Add a blank line after each assessment
        sb.append("\n");

        return sb.toString();
    }
}
