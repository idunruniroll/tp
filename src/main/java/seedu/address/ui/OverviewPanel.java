package seedu.address.ui;

import java.util.LinkedHashMap;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.logic.Logic;
import seedu.address.model.assessment.Assessment;
import seedu.address.model.grade.Grade;
import seedu.address.model.student.Student;

/**
 * Panel that displays an overview summary of courses, students, assessments, and grades.
 */
public class OverviewPanel extends UiPart<Region> {

    private static final String FXML = "OverviewPanel.fxml";

    @FXML
    private Label courseCountLabel;

    @FXML
    private Label studentCountLabel;

    @FXML
    private Label assessmentCountLabel;

    @FXML
    private Label gradeCountLabel;

    @FXML
    private Label gradesPerAssessmentLabel;

    /**
     * Creates an overview panel using the current application data.
     */
    public OverviewPanel(Logic logic) {
        super(FXML);
        fillOverview(logic);
    }

    /**
     * Fills the panel with the latest overview data.
     */
    private void fillOverview(Logic logic) {
        int courseCount = logic.getAddressBook().getCourseList().size();
        int assessmentCount = logic.getAddressBook().getAssessmentList().size();
        int gradeCount = logic.getAddressBook().getGradeList().size();

        int studentCount = (int) logic.getAddressBook().getCourseList().stream()
                .flatMap(course -> course.getStudents().stream())
                .map(Student::getStudentId)
                .distinct()
                .count();

        Map<String, Integer> gradesPerAssessment = new LinkedHashMap<>();

        for (Assessment assessment : logic.getAddressBook().getAssessmentList()) {
            String key = formatAssessmentKey(
                    assessment.getCourseCode().toString(),
                    assessment.getAssessmentName().toString());
            gradesPerAssessment.put(key, 0);
        }

        for (Grade grade : logic.getAddressBook().getGradeList()) {
            String key = formatAssessmentKey(
                    grade.getCourseCode().toString(),
                    grade.getAssessmentName().toString());
            gradesPerAssessment.put(key, gradesPerAssessment.getOrDefault(key, 0) + 1);
        }

        courseCountLabel.setText("Courses: " + courseCount);
        studentCountLabel.setText("Students: " + studentCount);
        assessmentCountLabel.setText("Assessments: " + assessmentCount);
        gradeCountLabel.setText("Grades: " + gradeCount);

        if (gradesPerAssessment.isEmpty()) {
            gradesPerAssessmentLabel.setText("Grades per assessment:\n- None");
        } else {
            StringBuilder sb = new StringBuilder("Grades per assessment:");
            for (Map.Entry<String, Integer> entry : gradesPerAssessment.entrySet()) {
                sb.append("\n- ")
                        .append(entry.getKey())
                        .append(": ")
                        .append(entry.getValue());
            }
            gradesPerAssessmentLabel.setText(sb.toString());
        }
    }

    /**
     * Returns a display key in the format CourseCode / AssessmentName.
     */
    private String formatAssessmentKey(String courseCode, String assessmentName) {
        return courseCode + " / " + assessmentName;
    }
}
