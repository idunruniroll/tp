package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
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
import seedu.address.model.person.Person;
import seedu.address.model.student.Student;

public class ListAssessmentsCommandTest {

    public static final String MESSAGE_SUCCESS = Messages.MESSAGE_LIST_ASSESSMENTS_SUCCESS;

    @Test
    public void execute_noAssessments_returnsNoAssessmentsMessage() throws Exception {
        ModelStub modelStub = new ModelStub(FXCollections.observableArrayList());

        CommandResult result = new ListAssessmentsCommand().execute(modelStub);

        assertEquals(Messages.MESSAGE_NO_ASSESSMENTS, result.getFeedbackToUser());
    }

    @Test
    public void execute_singleCourseMultipleAssessments_listsAssessmentsWithIndexes() throws Exception {
        ObservableList<Assessment> assessments = FXCollections.observableArrayList(
                new Assessment("CS2103T", new AssessmentName("Quiz 1"), new MaxScore("10")),
                new Assessment("CS2103T", new AssessmentName("Finals"), new MaxScore("100")));
        ModelStub modelStub = new ModelStub(assessments);

        CommandResult result = new ListAssessmentsCommand().execute(modelStub);

        assertEquals(Messages.MESSAGE_LIST_ASSESSMENTS_SUCCESS, result.getFeedbackToUser());
        assertEquals(DisplayMode.ASSESSMENTS, modelStub.getDisplayMode());
        assertEquals(assessments.size(), modelStub.getFilteredAssessmentList().size());
        assertTrue(modelStub.getFilteredAssessmentList().containsAll(assessments));
    }

    @Test
    public void execute_multipleCourses_outputContainsAllCourseSections() throws Exception {
        ObservableList<Assessment> assessments = FXCollections.observableArrayList(
                new Assessment("CS2103T", new AssessmentName("Quiz 1"), new MaxScore("10")),
                new Assessment("CS2101", new AssessmentName("OPM"), new MaxScore("20")));
        ModelStub modelStub = new ModelStub(assessments);

        CommandResult result = new ListAssessmentsCommand().execute(modelStub);

        assertEquals(Messages.MESSAGE_LIST_ASSESSMENTS_SUCCESS, result.getFeedbackToUser());
        assertEquals(DisplayMode.ASSESSMENTS, modelStub.getDisplayMode());
        assertEquals(assessments.size(), modelStub.getFilteredAssessmentList().size());
        assertTrue(modelStub.getFilteredAssessmentList().containsAll(assessments));
    }

    @Test
    public void execute_filteredCourseNotFound_throwsCommandException() {
        ObservableList<Assessment> assessments = FXCollections.observableArrayList(
                new Assessment("CS2103T", new AssessmentName("Quiz 1"), new MaxScore("10")));
        ModelStub modelStub = new ModelStub(assessments);

        ListAssessmentsCommand command = new ListAssessmentsCommand("CS9999");
        assertThrows(CommandException.class,
                String.format(Messages.MESSAGE_COURSE_NOT_FOUND, "CS9999"), () -> command.execute(modelStub));
    }

    /**
     * A default model stub that only supports getAssessmentList().
     */
    private static class ModelStub implements Model {
        private final ObservableList<Assessment> assessments;
        private final FilteredList<Assessment> filteredAssessments;
        private DisplayMode displayMode;

        private java.util.Optional<String> currentCourseForDisplay = java.util.Optional.empty();

        private ModelStub(ObservableList<Assessment> assessments) {
            this.assessments = assessments;
            this.filteredAssessments = new FilteredList<>(assessments);
        }

        @Override
        public ObservableList<Assessment> getAssessmentList() {
            return assessments;
        }

        @Override
        public ObservableList<Assessment> getFilteredAssessmentList() {
            return filteredAssessments;
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
        public void setCurrentCourseForDisplay(java.util.Optional<String> courseCode) {
            this.currentCourseForDisplay = courseCode;
        }

        @Override
        public java.util.Optional<String> getCurrentCourseForDisplay() {
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
        public ObservableList<Grade> getGradeList() {
            return FXCollections.observableArrayList();
        }

        @Override
        public ObservableList<Course> getCourseList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasCourse(String courseCode) {
            return assessments.stream().anyMatch(assessment -> assessment.getCourseCode().equalsIgnoreCase(courseCode));
        }

        @Override
        public java.util.Optional<Course> getCourse(String courseCode) {
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
        public ObservableList<Grade> getGradesByStudentId(String studentId) {
            return FXCollections.observableArrayList();
        }

        @Override
        public ObservableList<Grade> getGradesByCourse(String courseCode) {
            return FXCollections.observableArrayList();
        }

        @Override
        public ObservableList<Grade> getGradesByCourseAndAssessment(String courseCode, String assessmentName) {
            return FXCollections.observableArrayList();
        }

        @Override
        public ObservableList<Student> getFilteredStudentList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Grade> getFilteredGradeList() {
            return FXCollections.observableArrayList();
        }

        @Override
        public void updateFilteredGradeList(Predicate<Grade> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredAssessmentList(Predicate<Assessment> predicate) {
            filteredAssessments.setPredicate(predicate);
        }

        @Override
        public boolean isStudentEnrolled(String courseCode, String studentId) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public java.util.Optional<Assessment> getAssessmentForCourseByIndex(
                String courseCode, seedu.address.commons.core.index.Index assessmentIndex) {
            throw new AssertionError("This method should not be called.");
        }
    }
}
