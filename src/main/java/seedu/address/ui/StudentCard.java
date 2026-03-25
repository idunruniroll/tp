package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.student.Student;

/**
 * A UI component that displays information of a {@code Student}.
 */
public class StudentCard extends UiPart<Region> {

    private static final String FXML = "StudentListCard.fxml";

    public final Student student;

    @FXML
    private HBox cardPane;
    @FXML
    private Label index;
    @FXML
    private Label studentName;
    @FXML
    private Label studentId;

    /**
     * Creates a {@code StudentCard} with the given {@code Student} and display index.
     */
    public StudentCard(Student student, int displayedIndex) {
        super(FXML);
        this.student = student;
        index.setText(displayedIndex + ". ");
        studentName.setText(student.getStudentName());
        studentId.setText(student.getStudentId());
    }
}
