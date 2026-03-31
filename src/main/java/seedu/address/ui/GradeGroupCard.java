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
        Label indexLabel = new Label(index + ".");
        indexLabel.getStyleClass().add("cell_small_label");

        Label studentLabel = new Label(row.getStudentId());
        studentLabel.getStyleClass().add("cell_big_label");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label scoreLabel = new Label(row.getScore() + "/" + maxScoreText);
        scoreLabel.getStyleClass().add("cell_small_label");

        HBox hBox = new HBox(12);
        hBox.getChildren().addAll(indexLabel, studentLabel, spacer, scoreLabel);
        return hBox;
    }
}
