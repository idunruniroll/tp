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
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.DisplayMode;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.assessment.Assessment;
import seedu.address.model.assessment.AssessmentName;
import seedu.address.model.assessment.MaxScore;
import seedu.address.model.course.Course;
import seedu.address.model.grade.Grade;
import seedu.address.model.person.Person;
import seedu.address.model.student.Student;

public class RemoveAssessmentCommandTest {

    @Test
    public void constructor_nullCourseCode_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RemoveAssessmentCommand(null, Index.fromOneBased(1)));
    }

    @Test
    public void constructor_nullAssessmentIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RemoveAssessmentCommand("CS2103T", null));
    }

    @Test
    public void execute_validCourseAndIndex_removesAssessmentSuccessfully() throws Exception {
        Assessment quiz = new Assessment("CS2103T", new AssessmentName("Quiz 1"), new MaxScore("10"));
        Assessment finals = new Assessment("CS2103T", new AssessmentName("Finals"), new MaxScore("100"));
        Assessment otherCourseAssessment = new Assessment("CS2101", new AssessmentName("Midterm"), new MaxScore("50"));

        ObservableList<Assessment> assessments = FXCollections.observableArrayList(
                quiz, finals, otherCourseAssessment);

        ModelStub modelStub = new ModelStub(assessments);
        RemoveAssessmentCommand command = new RemoveAssessmentCommand("CS2103T", Index.fromOneBased(2));

        CommandResult commandResult = command.execute(modelStub);

        assertEquals(String.format(RemoveAssessmentCommand.MESSAGE_DELETE_ASSESSMENT_SUCCESS, finals),
                commandResult.getFeedbackToUser());
        assertEquals(finals, modelStub.removedAssessment);
        assertEquals(2, modelStub.assessments.size());
        assertFalse(modelStub.assessments.contains(finals));
    }

    @Test
    public void execute_courseNotFound_throwsCommandException() {
        Assessment quiz = new Assessment("CS2103T", new AssessmentName("Quiz 1"), new MaxScore("10"));
        ObservableList<Assessment> assessments = FXCollections.observableArrayList(quiz);

        ModelStub modelStub = new ModelStub(assessments);
        RemoveAssessmentCommand command = new RemoveAssessmentCommand("CS9999", Index.fromOneBased(1));

        assertThrows(CommandException.class, RemoveAssessmentCommand.MESSAGE_INVALID_COURSE, (
            ) -> command.execute(modelStub));
    }

    @Test
    public void execute_invalidAssessmentIndex_throwsCommandException() {
        Assessment quiz = new Assessment("CS2103T", new AssessmentName("Quiz 1"), new MaxScore("10"));
        ObservableList<Assessment> assessments = FXCollections.observableArrayList(quiz);

        ModelStub modelStub = new ModelStub(assessments);
        RemoveAssessmentCommand command = new RemoveAssessmentCommand("CS2103T", Index.fromOneBased(2));

        assertThrows(CommandException.class, RemoveAssessmentCommand.MESSAGE_INVALID_ASSESSMENT_INDEX, (
            ) -> command.execute(modelStub));
    }

    @Test
    public void equals() {
        RemoveAssessmentCommand firstCommand = new RemoveAssessmentCommand("CS2103T", Index.fromOneBased(1));
        RemoveAssessmentCommand secondCommand = new RemoveAssessmentCommand("CS2103T", Index.fromOneBased(2));
        RemoveAssessmentCommand differentCourseCommand = new RemoveAssessmentCommand("CS2101", Index.fromOneBased(1));
        RemoveAssessmentCommand firstCommandCopy = new RemoveAssessmentCommand("CS2103T", Index.fromOneBased(1));

        assertTrue(firstCommand.equals(firstCommand));
        assertTrue(firstCommand.equals(firstCommandCopy));
        assertFalse(firstCommand.equals(1));
        assertFalse(firstCommand.equals(null));
        assertFalse(firstCommand.equals(secondCommand));
        assertFalse(firstCommand.equals(differentCourseCommand));
    }

    /**
     * A default model stub that only supports assessment operations needed by this
     * test.
     */
    private static class ModelStub implements Model {
        private final ObservableList<Assessment> assessments;
        private Assessment removedAssessment;

        private ModelStub(ObservableList<Assessment> assessments) {
            this.assessments = assessments;
        }

        @Override
        public ObservableList<Assessment> getAssessmentList() {
            return assessments;
        }

        @Override
        public void removeAssessment(Assessment assessment) {
            requireNonNull(assessment);
            removedAssessment = assessment;
            assessments.remove(assessment);
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
            return FXCollections.observableArrayList();
        }

        @Override
        public boolean hasCourse(String courseCode) {
            throw new AssertionError("This method should not be called.");
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
        public void setCurrentCourseForDisplay(Optional<String> courseCode) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<String> getCurrentCourseForDisplay() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Assessment> getFilteredAssessmentList() {
            return FXCollections.observableArrayList();
        }

        @Override
        public ObservableList<Grade> getFilteredGradeList() {
            return FXCollections.observableArrayList();
        }

        @Override
        public void setDisplayMode(DisplayMode displayMode) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public DisplayMode getDisplayMode() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredGradeList(Predicate<Grade> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }
}
