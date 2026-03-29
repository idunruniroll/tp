package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.DisplayMode;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.assessment.Assessment;
import seedu.address.model.course.Course;
import seedu.address.model.grade.Grade;
import seedu.address.model.person.Person;
import seedu.address.model.student.Student;

public class AddAssessmentCommandTest {

    @Test
    public void constructor_nullCourseCode_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddAssessmentCommand(null, "Midterm", "100"));
    }

    @Test
    public void constructor_nullAssessmentName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddAssessmentCommand("CS2103T", null, "100"));
    }

    @Test
    public void constructor_nullMaxScore_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddAssessmentCommand("CS2103T", "Midterm", null));
    }

    @Test
    public void execute_courseExistsAndAssessmentAccepted_addSuccessful() throws Exception {
        ModelStubAcceptingAssessmentAdded modelStub = new ModelStubAcceptingAssessmentAdded();
        AddAssessmentCommand command = new AddAssessmentCommand("CS2103T", "Midterm", "100");

        CommandResult commandResult = command.execute(modelStub);

        Assessment expectedAssessment = new Assessment("CS2103T",
                new seedu.address.model.assessment.AssessmentName("Midterm"),
                new seedu.address.model.assessment.MaxScore("100"));

        assertEquals(String.format(AddAssessmentCommand.MESSAGE_SUCCESS, expectedAssessment),
                commandResult.getFeedbackToUser());
        assertEquals(1, modelStub.assessmentsAdded.size());
        assertEquals(expectedAssessment, modelStub.assessmentsAdded.get(0));
    }

    @Test
    public void execute_courseDoesNotExist_throwsCommandException() {
        AddAssessmentCommand command = new AddAssessmentCommand("CS2103T", "Midterm", "100");
        ModelStub modelStub = new ModelStubWithoutCourse();

        assertThrows(CommandException.class,
                String.format(AddAssessmentCommand.MESSAGE_COURSE_NOT_FOUND, "CS2103T"), (
                ) -> command.execute(modelStub));
    }

    @Test
    public void execute_duplicateAssessment_throwsCommandException() {
        Assessment assessment = new Assessment("CS2103T",
                new seedu.address.model.assessment.AssessmentName("Midterm"),
                new seedu.address.model.assessment.MaxScore("100"));
        AddAssessmentCommand command = new AddAssessmentCommand("CS2103T", "Midterm", "100");
        ModelStub modelStub = new ModelStubWithAssessment(assessment);

        assertThrows(CommandException.class,
                AddAssessmentCommand.MESSAGE_DUPLICATE_ASSESSMENT, (
                ) -> command.execute(modelStub));
    }

    @Test
    public void equals() {
        AddAssessmentCommand addMidtermCommand = new AddAssessmentCommand("CS2103T", "Midterm", "100");
        AddAssessmentCommand addFinalCommand = new AddAssessmentCommand("CS2103T", "Final", "100");
        AddAssessmentCommand addMidtermDifferentCourseCommand = new AddAssessmentCommand("CS2101", "Midterm",
                "100");
        AddAssessmentCommand addMidtermDifferentScoreCommand = new AddAssessmentCommand("CS2103T", "Midterm",
                "50");

        assertTrue(addMidtermCommand.equals(addMidtermCommand));
        assertTrue(addMidtermCommand.equals(new AddAssessmentCommand("CS2103T", "Midterm", "100")));
        assertFalse(addMidtermCommand.equals(1));
        assertFalse(addMidtermCommand.equals(null));
        assertFalse(addMidtermCommand.equals(addFinalCommand));
        assertFalse(addMidtermCommand.equals(addMidtermDifferentCourseCommand));
        assertFalse(addMidtermCommand.equals(addMidtermDifferentScoreCommand));
    }

    /**
     * A default model stub that has all methods failing.
     */
    private class ModelStub implements Model {
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
        public ObservableList<Assessment> getAssessmentList() {
            return FXCollections.observableArrayList();
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

        @Override
        public void updateFilteredAssessmentList(Predicate<Assessment> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A model stub with no matching course.
     */
    private class ModelStubWithoutCourse extends ModelStub {
        @Override
        public Optional<Course> getCourse(String courseCode) {
            requireNonNull(courseCode);
            return Optional.empty();
        }
    }

    /**
     * A model stub that already contains the assessment.
     */
    private class ModelStubWithAssessment extends ModelStub {
        private final Assessment assessment;

        ModelStubWithAssessment(Assessment assessment) {
            this.assessment = assessment;
        }

        @Override
        public Optional<Course> getCourse(String courseCode) {
            requireNonNull(courseCode);
            return Optional.of(new Course(courseCode));
        }

        @Override
        public boolean hasAssessment(Assessment assessment) {
            requireNonNull(assessment);
            return this.assessment.isSameAssessment(assessment);
        }
    }

    /**
     * A model stub that always accepts the assessment being added.
     */
    private class ModelStubAcceptingAssessmentAdded extends ModelStub {
        final ArrayList<Assessment> assessmentsAdded = new ArrayList<>();

        @Override
        public Optional<Course> getCourse(String courseCode) {
            requireNonNull(courseCode);
            return Optional.of(new Course(courseCode));
        }

        @Override
        public boolean hasAssessment(Assessment assessment) {
            requireNonNull(assessment);
            return assessmentsAdded.stream().anyMatch(assessment::isSameAssessment);
        }

        @Override
        public void addAssessment(Assessment assessment) {
            requireNonNull(assessment);
            assessmentsAdded.add(assessment);
        }
    }
}
