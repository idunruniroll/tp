package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.grade.Grade;

public class GradeCard extends UiPart<Region> {

    private static final String FXML = "GradeListCard.fxml";

    public final Grade grade;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label assessmentName;
    @FXML
    private Label courseCode;
    @FXML
    private Label studentId;
    @FXML
    private Label score;

    public GradeCard(Grade grade, int displayedIndex, String maxScoreText) {
        super(FXML);
        this.grade = grade;

        id.setText(displayedIndex + ". ");
        assessmentName.setText(grade.getAssessmentName().toString());
        courseCode.setText(grade.getCourseCode());
        studentId.setText("Student ID: " + grade.getStudentId());
        score.setText("Grade: " + grade.getScore() + " / " + maxScoreText);
    }
}
