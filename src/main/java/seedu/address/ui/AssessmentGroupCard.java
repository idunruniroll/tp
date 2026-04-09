package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.assessment.Assessment;

/**
 * A UI component that displays a grouped set of assessments under one course.
 */
public class AssessmentGroupCard extends UiPart<Region> {

    private static final String FXML = "AssessmentGroupCard.fxml";

    @FXML
    private Label courseTag;

    @FXML
    private VBox assessmentRows;

    /**
     * Creates an {@code AssessmentGroupCard} for one course and its assessments.
     */
    public AssessmentGroupCard(AssessmentListPanel.GroupedAssessment groupedAssessment) {
        super(FXML);

        courseTag.setText(groupedAssessment.getCourseCode());

        int index = 1;
        for (Assessment assessment : groupedAssessment.getAssessments()) {
            assessmentRows.getChildren().add(createAssessmentRow(index, assessment));
            index++;
        }
    }

    private HBox createAssessmentRow(int index, Assessment assessment) {
        Label indexLabel = new Label(index + ".");
        indexLabel.getStyleClass().add("grade-row-text");

        Label nameLabel = new Label(
                assessment.getAssessmentName().toString() + " (Max: " + assessment.getMaxScore() + ")");
        nameLabel.getStyleClass().add("grade-row-text");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox row = new HBox(12);
        row.getChildren().addAll(indexLabel, nameLabel, spacer);
        return row;
    }
}
