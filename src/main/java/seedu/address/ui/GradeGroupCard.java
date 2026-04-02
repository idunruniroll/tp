package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * A UI component that displays grades grouped by course and assessment.
 */
public class GradeGroupCard extends UiPart<Region> {

    private static final String FXML = "GradeGroupCard.fxml";

    @FXML
    private Label courseTag;

    @FXML
    private Label titleLabel;

    @FXML
    private VBox gradeRows;

    /**
     * Creates a {@code GradeGroupCard}.
     */
    public GradeGroupCard(GradeListPanel.GroupedGrade groupedGrade) {
        super(FXML);

        courseTag.setText(groupedGrade.getCourseCode());
        titleLabel.setText(groupedGrade.getAssessmentName() + " (Max: " + groupedGrade.getMaxScoreText() + ")");

        int index = 1;
        for (GradeListPanel.GradeRow row : groupedGrade.getRows()) {
            gradeRows.getChildren().add(createGradeRow(index, row, groupedGrade.getMaxScoreText()));
            index++;
        }
    }

    private HBox createGradeRow(int index, GradeListPanel.GradeRow row, String maxScoreText) {
        Label studentEntryLabel = new Label(index + ".  " + row.getStudentId());
        studentEntryLabel.getStyleClass().add("grade-row-text");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label scoreLabel = new Label(row.getScore() + "/" + maxScoreText);
        scoreLabel.getStyleClass().add("grade-score-text");

        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(studentEntryLabel, spacer, scoreLabel);
        return hBox;
    }
}
