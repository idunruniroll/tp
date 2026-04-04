package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.DisplayMode;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.assessment.Assessment;
import seedu.address.model.assessment.AssessmentName;
import seedu.address.model.assessment.MaxScore;
import seedu.address.model.course.Course;
import seedu.address.model.grade.Grade;
import seedu.address.model.grade.Score;
import seedu.address.model.person.Person;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentId;

public class ListGradesCommandTest {

    @Test
    public void executeStudentFilter_success() throws Exception {
        ModelStub modelStub = new ModelStub();
        modelStub.assessments = FXCollections.observableArrayList(
                new Assessment("CS2103T", new AssessmentName("Quiz 1"), new MaxScore("10")),
                new Assessment("CS2103T", new AssessmentName("Finals"), new MaxScore("100")),
                new Assessment("CS2101", new AssessmentName("Presentation"), new MaxScore("20")));

        ObservableList<Grade> expectedGrades = FXCollections.observableArrayList(
                new Grade("CS2103T", new StudentId("A1234567X"), new AssessmentName("Quiz 1"), new Score("9")),
                new Grade("CS2101", new StudentId("A1234567X"), new AssessmentName("Presentation"), new Score("18")));
        modelStub.gradesByStudentId = expectedGrades;
        modelStub.allGrades = FXCollections.observableArrayList(expectedGrades);

        ListGradesCommand command = new ListGradesCommand("student", "A1234567X", null);
        CommandResult result = command.execute(modelStub);

        assertEquals(Messages.MESSAGE_LIST_GRADES_SUCCESS, result.getFeedbackToUser());
        assertEquals(DisplayMode.GRADES, modelStub.getDisplayMode());
        assertEquals(expectedGrades, modelStub.getFilteredGradeList());
    }

    @Test
    public void executeCourseAssessment_success() throws Exception {
        ModelStub modelStub = new ModelStub();
        modelStub.hasCourse = true;
        modelStub.assessments = FXCollections.observableArrayList(
                new Assessment("CS2103T", new AssessmentName("Quiz 1"), new MaxScore("10")),
                new Assessment("CS2103T", new AssessmentName("Finals"), new MaxScore("100")));

        ObservableList<Grade> expectedGrades = FXCollections.observableArrayList(
                new Grade("CS2103T", new StudentId("A1234567X"), new AssessmentName("Finals"), new Score("87")),
                new Grade("CS2103T", new StudentId("A7654321B"), new AssessmentName("Finals"), new Score("92")));
        modelStub.gradesByCourseAndAssessment = expectedGrades;
        modelStub.allGrades = FXCollections.observableArrayList(expectedGrades);

        ListGradesCommand command = new ListGradesCommand("courseassessment",
                "CS2103T", Index.fromOneBased(2));
        CommandResult result = command.execute(modelStub);

        assertEquals(Messages.MESSAGE_LIST_GRADES_SUCCESS, result.getFeedbackToUser());
        assertEquals(DisplayMode.GRADES, modelStub.getDisplayMode());
        assertEquals(expectedGrades, modelStub.getFilteredGradeList());
    }

    @Test
    public void executeCourseFilter_courseNotFound() {
        ModelStub modelStub = new ModelStub();
        modelStub.hasCourse = false;

        ListGradesCommand command = new ListGradesCommand("course", "CS2103T", null);

        assertThrows(CommandException.class,
                String.format(Messages.MESSAGE_COURSE_NOT_FOUND, "CS2103T"), (
                    ) -> command.execute(modelStub));
    }

    @Test
    public void executeCourseFilter_noGrades() throws Exception {
        ModelStub modelStub = new ModelStub();
        modelStub.hasCourse = true;
        modelStub.gradesByCourse = FXCollections.observableArrayList();

        ListGradesCommand command = new ListGradesCommand("course", "CS2103T", null);
        CommandResult result = command.execute(modelStub);

        assertEquals("No grades found.", result.getFeedbackToUser());
    }

    @Test
    public void executeCourseAssessment_invalidIndex() {
        ModelStub modelStub = new ModelStub();
        modelStub.hasCourse = true;
        modelStub.assessments = FXCollections.observableArrayList(
                new Assessment("CS2103T", new AssessmentName("Quiz 1"), new MaxScore("10")));

        ListGradesCommand command = new ListGradesCommand("courseassessment",
                "CS2103T", Index.fromOneBased(2));

        assertThrows(CommandException.class,
                String.format(Messages.MESSAGE_INVALID_ASSESSMENT_INDEX, 2), (
                    ) -> command.execute(modelStub));
    }

    @Test
    public void equals() {
        ListGradesCommand firstCommand = new ListGradesCommand("course", "CS2103T", null);
        ListGradesCommand firstCommandCopy = new ListGradesCommand("course", "CS2103T", null);
        ListGradesCommand secondCommand = new ListGradesCommand("student", "A1234567X", null);
        ListGradesCommand thirdCommand = new ListGradesCommand("courseassessment", "CS2103T", Index.fromOneBased(1));

        assertTrue(firstCommand.equals(firstCommand));
        assertTrue(firstCommand.equals(firstCommandCopy));
        assertFalse(firstCommand.equals(null));
        assertFalse(firstCommand.equals(1));
        assertFalse(firstCommand.equals(secondCommand));
        assertFalse(firstCommand.equals(thirdCommand));
    }

    /**
     * A default model stub that only supports ListGradesCommand-related methods.
     */
    private static class ModelStub implements Model {
        private boolean hasCourse;
        private ObservableList<Assessment> assessments = FXCollections.observableArrayList();
        private ObservableList<Grade> gradesByStudentId = FXCollections.observableArrayList();
        private ObservableList<Grade> gradesByCourse = FXCollections.observableArrayList();
        private ObservableList<Grade> gradesByCourseAndAssessment = FXCollections.observableArrayList();
        private ObservableList<Grade> allGrades = FXCollections.observableArrayList();
        private ObservableList<Grade> filteredGrades = FXCollections.observableArrayList();
        private DisplayMode displayMode;
        private Optional<String> currentCourseForDisplay = Optional.empty();

        @Override
        public boolean hasCourse(String courseCode) {
            requireNonNull(courseCode);
            return hasCourse;
        }

        @Override
        public ObservableList<Assessment> getAssessmentList() {
            return assessments;
        }

        @Override
        public ObservableList<Grade> getGradesByStudentId(String studentId) {
            requireNonNull(studentId);
            filteredGrades.setAll(gradesByStudentId);
            return gradesByStudentId;
        }

        @Override
        public ObservableList<Grade> getGradesByCourse(String courseCode) {
            requireNonNull(courseCode);
            filteredGrades.setAll(gradesByCourse);
            return gradesByCourse;
        }

        @Override
        public ObservableList<Grade> getGradesByCourseAndAssessment(String courseCode, String assessmentName) {
            requireNonNull(courseCode);
            requireNonNull(assessmentName);
            filteredGrades.setAll(gradesByCourseAndAssessment);
            return gradesByCourseAndAssessment;
        }

        @Override
        public ObservableList<Grade> getGradeList() {
            return allGrades;
        }

        @Override
        public ObservableList<Grade> getFilteredGradeList() {
            return filteredGrades;
        }

        @Override
        public void updateFilteredGradeList(Predicate<Grade> predicate) {
            filteredGrades.setAll(allGrades.stream().filter(predicate).toList());
        }

        @Override
        public void setDisplayMode(DisplayMode displayMode) {
            this.displayMode = displayMode;
        }

        @Override
        public DisplayMode getDisplayMode() {
            return displayMode;
        }

        @Override
        public void setCurrentCourseForDisplay(Optional<String> courseCode) {
            currentCourseForDisplay = courseCode;
        }

        @Override
        public Optional<String> getCurrentCourseForDisplay() {
            return currentCourseForDisplay;
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook addressBook) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasAssessment(Assessment assessment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addAssessment(Assessment assessment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeAssessment(Assessment assessment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasGrade(Grade grade) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addGrade(Grade grade) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeGrade(Grade grade) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Course> getCourseList() {
            return FXCollections.observableArrayList();
        }

        @Override
        public Optional<Course> getCourse(String courseCode) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addCourse(Course course) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeCourse(Course course) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addStudentToCourse(String courseCode, Student student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeStudentFromCourse(String courseCode, String studentId) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Student> getFilteredStudentList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Assessment> getFilteredAssessmentList() {
            return FXCollections.observableArrayList();
        }

        @Override
        public void updateFilteredAssessmentList(Predicate<Assessment> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }
}
