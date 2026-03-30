package seedu.address.ui;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.assessment.Assessment;

/**
 * Panel containing grouped assessments to be displayed in the UI.
 */
public class AssessmentListPanel extends UiPart<Region> {

    private static final String FXML = "AssessmentListPanel.fxml";

    private final ObservableList<Assessment> sourceAssessmentList;
    private final ObservableList<GroupedAssessment> groupedAssessmentList = FXCollections.observableArrayList();

    @FXML
    private ListView<GroupedAssessment> assessmentListView;

    /**
     * Creates an {@code AssessmentListPanel} that displays grouped assessments.
     */
    public AssessmentListPanel(ObservableList<Assessment> assessmentList) {
        super(FXML);
        this.sourceAssessmentList = assessmentList;

        rebuildGroupedAssessments();
        sourceAssessmentList.addListener((ListChangeListener<Assessment>) change -> rebuildGroupedAssessments());

        assessmentListView.setItems(groupedAssessmentList);
        assessmentListView.setCellFactory(listView -> new AssessmentListViewCell());
    }

    private void rebuildGroupedAssessments() {
        Map<String, List<Assessment>> groupedMap = new LinkedHashMap<>();

        for (Assessment assessment : sourceAssessmentList) {
            groupedMap.computeIfAbsent(assessment.getCourseCode(), key -> new ArrayList<>()).add(assessment);
        }

        groupedAssessmentList.setAll(groupedMap.entrySet().stream()
                .map(entry -> new GroupedAssessment(entry.getKey(), entry.getValue()))
                .toList());
    }

    /**
     * Simple grouped assessment view model.
     */
    public static class GroupedAssessment {
        private final String courseCode;
        private final List<Assessment> assessments;

        public GroupedAssessment(String courseCode, List<Assessment> assessments) {
            this.courseCode = courseCode;
            this.assessments = assessments;
        }

        public String getCourseCode() {
            return courseCode;
        }

        public List<Assessment> getAssessments() {
            return assessments;
        }
    }

    class AssessmentListViewCell extends ListCell<GroupedAssessment> {
        @Override
        protected void updateItem(GroupedAssessment groupedAssessment, boolean empty) {
            super.updateItem(groupedAssessment, empty);

            if (empty || groupedAssessment == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new AssessmentGroupCard(groupedAssessment).getRoot());
            }
        }
    }
}
