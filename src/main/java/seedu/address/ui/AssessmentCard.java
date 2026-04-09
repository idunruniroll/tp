package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.assessment.Assessment;

/**
 * A UI component that displays information for a single {@code Assessment}.
 */
public class AssessmentCard extends UiPart<Region> {

    private static final String FXML = "AssessmentListCard.fxml";

    public final Assessment assessment;

    @FXML
    private HBox cardPane;
    @FXML
    private Label assessmentName;
    @FXML
    private Label courseCode;
    @FXML
    private Label maxScore;

    /**
     * Creates an {@code AssessmentCard} for the given {@code Assessment} and
     * displays it
     * using the given displayed index.
     * @param assessment     The assessment to display.
     * @param displayedIndex The index shown for this assessment in the list.
     */
    public AssessmentCard(Assessment assessment, int displayedIndex) {
        super(FXML);
        this.assessment = assessment;

        assessmentName.setText(displayedIndex + ". "
                + assessment.getAssessmentName() + " (Max: " + assessment.getMaxScore() + ")");
    }
}
