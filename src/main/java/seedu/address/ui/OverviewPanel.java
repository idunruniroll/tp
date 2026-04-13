package seedu.address.ui;

import java.util.LinkedHashMap;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.logic.Logic;
import seedu.address.model.assessment.Assessment;
import seedu.address.model.grade.Grade;

/**
 * Panel that displays an overview summary of assessments and grades.
 */
public class OverviewPanel extends UiPart<Region> {

    private static final String FXML = "OverviewPanel.fxml";

    @FXML
    private Label assessmentCountLabel;

    @FXML
    private Label gradeCountLabel;

    @FXML
    private Label gradesPerAssessmentLabel;

    /**
     * Creates an {@code OverviewPanel} and fills it with the current summary data.
     *
     * @param logic Logic component used to access the current data.
     */
    public OverviewPanel(Logic logic) {
        super(FXML);
        fillOverview(logic);
    }

    /**
     * Fills the panel with the latest assessment and grade summary.
     *
     * @param logic Logic component used to access the current data.
     */
    private void fillOverview(Logic logic) {
        int assessmentCount = logic.getAddressBook().getAssessmentList().size();
        int gradeCount = logic.getAddressBook().getGradeList().size();

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
     * Returns a display key for an assessment using course code and assessment name.
     *
     * @param courseCode Course code of the assessment.
     * @param assessmentName Name of the assessment.
     * @return Formatted assessment key.
     */
    private String formatAssessmentKey(String courseCode, String assessmentName) {
        return courseCode + " / " + assessmentName;
    }
}
