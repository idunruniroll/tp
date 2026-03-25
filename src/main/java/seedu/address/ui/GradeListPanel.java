package seedu.address.ui;

import java.util.Optional;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.assessment.Assessment;
import seedu.address.model.grade.Grade;

public class GradeListPanel extends UiPart<Region> {

    private static final String FXML = "GradeListPanel.fxml";

    private final ObservableList<Assessment> assessmentList;

    @FXML
    private ListView<Grade> gradeListView;

    public GradeListPanel(ObservableList<Grade> gradeList, ObservableList<Assessment> assessmentList) {
        super(FXML);
        this.assessmentList = assessmentList;
        gradeListView.setItems(gradeList);
        gradeListView.setCellFactory(listView -> new GradeListViewCell());
    }

    private String findMaxScoreText(Grade grade) {
        Optional<Assessment> matchingAssessment = assessmentList.stream()
                .filter(assessment -> assessment.getCourseCode().equalsIgnoreCase(grade.getCourseCode())
                        && assessment.getAssessmentName().equals(grade.getAssessmentName()))
                .findFirst();

        return matchingAssessment
                .map(assessment -> assessment.getMaxScore().toString())
                .orElse("?");
    }

    class GradeListViewCell extends ListCell<Grade> {
        @Override
        protected void updateItem(Grade grade, boolean empty) {
            super.updateItem(grade, empty);

            if (empty || grade == null) {
                setGraphic(null);
                setText(null);
            } else {
                String maxScoreText = findMaxScoreText(grade);
                setGraphic(new GradeCard(grade, getIndex() + 1, maxScoreText).getRoot());
            }
        }
    }
}
