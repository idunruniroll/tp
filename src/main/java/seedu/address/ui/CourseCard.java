package seedu.address.ui;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.assessment.Assessment;
import seedu.address.model.course.Course;

/**
 * A UI component that displays summary information for a single {@code Course}.
 */
public class CourseCard extends UiPart<Region> {

    private static final String FXML = "CourseListCard.fxml";

    public final Course course;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label courseCode;
    @FXML
    private Label assessmentCount;
    @FXML
    private Label studentCount;

    /**
     * Creates a {@code CourseCard} for the given {@code Course}.
     */
    public CourseCard(Course course, int displayedIndex, ObservableList<Assessment> assessmentList) {
        super(FXML);
        this.course = course;

        id.setText(displayedIndex + ". ");
        courseCode.setText(course.getCourseCode());
        assessmentCount.textProperty().bind(Bindings.size(course.getAssessments()).asString("Assessments: %d"));
        studentCount.textProperty().bind(Bindings.size(course.getStudents()).asString("Students: %d"));
    }
}
