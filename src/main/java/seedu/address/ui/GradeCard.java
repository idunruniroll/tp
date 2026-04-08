package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.grade.Grade;

/**
 * A UI component that displays information for a single {@code Grade}.
 */
public class GradeCard extends UiPart<Region> {

    private static final String FXML = "GradeListCard.fxml";

    public final Grade grade;

    @FXML
    private HBox cardPane;
    @FXML
    private Label assessmentName;
    @FXML
    private Label courseCode;
    @FXML
    private Label studentEntry;
    @FXML
    private Label score;

    /**
     * Creates a {@code GradeCard} for the given {@code Grade} and displays it
     * using the given displayed index.
     *
     * @param grade          The grade to display.
     * @param displayedIndex The index shown for this grade in the list.
     * @param maxScoreText   The maximum score of the assessment.
     */
    public GradeCard(Grade grade, int displayedIndex, String maxScoreText) {
        super(FXML);
        this.grade = grade;

        assessmentName.setText(grade.getAssessmentName() + " (Max: " + maxScoreText + ")");
        courseCode.setText(grade.getCourseCode());
        studentEntry.setText(displayedIndex + ".  " + grade.getStudentId());
        score.setText(grade.getScore() + "/" + maxScoreText);
    }
}
