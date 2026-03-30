package seedu.address.ui;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.assessment.Assessment;
import seedu.address.model.course.Course;
import seedu.address.model.grade.Grade;
import seedu.address.model.student.Student;

/**
 * Panel containing grouped grades to be displayed in the UI.
 */
public class GradeListPanel extends UiPart<Region> {

    private static final String FXML = "GradeListPanel.fxml";

    private final ObservableList<Grade> sourceGradeList;
    private final ObservableList<Assessment> assessmentList;
    private final ObservableList<Course> courseList;
    private final ObservableList<GroupedGrade> groupedGradeList = FXCollections.observableArrayList();

    @FXML
    private ListView<GroupedGrade> gradeListView;

    /**
     * Creates a {@code GradeListPanel} that displays grouped grades.
     */
    public GradeListPanel(ObservableList<Grade> gradeList,
            ObservableList<Assessment> assessmentList,
            ObservableList<Course> courseList) {
        super(FXML);
        this.sourceGradeList = gradeList;
        this.assessmentList = assessmentList;
        this.courseList = courseList;

        rebuildGroupedGrades();
        sourceGradeList.addListener((ListChangeListener<Grade>) change -> rebuildGroupedGrades());

        gradeListView.setItems(groupedGradeList);
        gradeListView.setCellFactory(listView -> new GradeListViewCell());
    }

    private void rebuildGroupedGrades() {
        Map<String, List<Grade>> groupedMap = new LinkedHashMap<>();

        for (Assessment assessment : assessmentList) {
            List<Grade> gradesForAssessment = sourceGradeList.stream()
                    .filter(grade -> grade.getCourseCode().equalsIgnoreCase(assessment.getCourseCode())
                            && grade.getAssessmentName().equals(assessment.getAssessmentName()))
                    .toList();

            if (!gradesForAssessment.isEmpty()) {
                String key = assessment.getCourseCode() + "||" + assessment.getAssessmentName();
                groupedMap.put(key, new ArrayList<>(gradesForAssessment));
            }
        }

        List<GroupedGrade> groupedGrades = new ArrayList<>();

        for (List<Grade> grades : groupedMap.values()) {
            Grade firstGrade = grades.get(0);
            String courseCode = firstGrade.getCourseCode();
            String assessmentName = firstGrade.getAssessmentName().toString();
            String maxScoreText = findMaxScoreText(courseCode, assessmentName);

            List<GradeRow> rows = new ArrayList<>();
            for (Grade grade : grades) {
                String studentName = findStudentName(courseCode, grade.getStudentId().toString());
                rows.add(new GradeRow(studentName, grade.getScore().toString(), grade.getStudentId().toString()));
            }

            groupedGrades.add(new GroupedGrade(courseCode, assessmentName, maxScoreText, rows));
        }

        groupedGradeList.setAll(groupedGrades);
    }

    private String findMaxScoreText(String courseCode, String assessmentName) {
        Optional<Assessment> matchingAssessment = assessmentList.stream()
                .filter(assessment -> assessment.getCourseCode().equalsIgnoreCase(courseCode)
                        && assessment.getAssessmentName().toString().equals(assessmentName))
                .findFirst();

        return matchingAssessment.map(assessment -> assessment.getMaxScore().toString()).orElse("?");
    }

    private String findStudentName(String courseCode, String studentId) {
        return courseList.stream()
                .filter(course -> course.getCourseCode().equalsIgnoreCase(courseCode))
                .flatMap(course -> course.getStudents().stream())
                .filter(student -> student.getStudentId().equalsIgnoreCase(studentId))
                .map(Student::getStudentName)
                .findFirst()
                .orElse(studentId);
    }

    /**
     * Simple grouped grade view model.
     */
    public static class GroupedGrade {
        private final String courseCode;
        private final String assessmentName;
        private final String maxScoreText;
        private final List<GradeRow> rows;

        public GroupedGrade(String courseCode, String assessmentName, String maxScoreText, List<GradeRow> rows) {
            this.courseCode = courseCode;
            this.assessmentName = assessmentName;
            this.maxScoreText = maxScoreText;
            this.rows = rows;
        }

        public String getCourseCode() {
            return courseCode;
        }

        public String getAssessmentName() {
            return assessmentName;
        }

        public String getMaxScoreText() {
            return maxScoreText;
        }

        public List<GradeRow> getRows() {
            return rows;
        }
    }

    /**
     * Row for one student's grade.
     */
    public static class GradeRow {
        private final String studentName;
        private final String score;
        private final String studentId;

        public GradeRow(String studentName, String score, String studentId) {
            this.studentName = studentName;
            this.score = score;
            this.studentId = studentId;
        }

        public String getStudentName() {
            return studentName;
        }

        public String getScore() {
            return score;
        }

        public String getStudentId() {
            return studentId;
        }
    }

    class GradeListViewCell extends ListCell<GroupedGrade> {
        @Override
        protected void updateItem(GroupedGrade groupedGrade, boolean empty) {
            super.updateItem(groupedGrade, empty);

            if (empty || groupedGrade == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new GradeGroupCard(groupedGrade).getRoot());
            }
        }
    }
}
