package seedu.address.ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import seedu.address.model.student.Student;

/**
 * Panel containing a table of students for a specific course.
 */
public class StudentListPanel extends UiPart<Region> {

    private static final String FXML = "StudentListPanel.fxml";

    @FXML
    private Label courseHeader;

    @FXML
    private TableView<Student> studentTableView;

    @FXML
    private TableColumn<Student, String> studentIdColumn;

    @FXML
    private TableColumn<Student, String> studentNameColumn;

    /**
     * Creates a StudentListPanel.
     *
     * @param studentList the list of students to display
     */
    public StudentListPanel(ObservableList<Student> studentList) {
        super(FXML);

        studentIdColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getStudentId()));
        studentNameColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getStudentName()));

        studentTableView.setItems(studentList);
        setCourseHeader("");
    }

    public void setCourseHeader(String courseCode) {
        if (courseCode == null || courseCode.isBlank()) {
            courseHeader.setText("Students");
        } else {
            courseHeader.setText("Course: " + courseCode);
        }
    }
}

