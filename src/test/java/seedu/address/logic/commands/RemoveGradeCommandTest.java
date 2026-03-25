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
import seedu.address.model.student.StudentId;
import seedu.address.testutil.PersonBuilder;

public class RemoveGradeCommandTest {

    @Test
    public void constructor_nullCourseCode_throwsNullPointerException() {
        assertThrows(NullPointerException.class, (
            ) -> new RemoveGradeCommand(null, Index.fromOneBased(1), Index.fromOneBased(1)));
    }

    @Test
    public void constructor_nullStudentIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, (
            ) -> new RemoveGradeCommand("CS2103T", null, Index.fromOneBased(1)));
    }

    @Test
    public void constructor_nullAssessmentIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, (
            ) -> new RemoveGradeCommand("CS2103T", Index.fromOneBased(1), null));
    }

    @Test
    public void execute_validInputs_removeSuccessful() throws Exception {
        Person alice = new PersonBuilder()
                .withName("Alice Pauline")
                .withPhone("94351253")
                .withEmail("alice@gmail.com")
                .withAddress("123, Jurong West Ave 6, #08-111")
                .build();

        Assessment quiz = new Assessment("CS2103T",
                new AssessmentName("Quiz 1"),
                new MaxScore("10"));

        Grade existingGrade = new Grade(
                new StudentId("alice@gmail.com"),
                new AssessmentName("Quiz 1"),
                "CS2103T");

        ModelStubWithGrade modelStub = new ModelStubWithGrade(alice, quiz, existingGrade);

        RemoveGradeCommand command = new RemoveGradeCommand("cs2103t",
                Index.fromOneBased(1), Index.fromOneBased(1));

        CommandResult result = command.execute(modelStub);

        assertEquals(String.format(RemoveGradeCommand.MESSAGE_SUCCESS,
                "alice@gmail.com", "Quiz 1", "CS2103T"), result.getFeedbackToUser());
        assertEquals(existingGrade, modelStub.removedGrade);
    }

    @Test
    public void execute_invalidStudentIndex_throwsCommandException() {
        Person alice = new PersonBuilder().build();
        Assessment quiz = new Assessment("CS2103T",
                new AssessmentName("Quiz 1"),
                new MaxScore("10"));
        Grade existingGrade = new Grade(
                new StudentId("amy@gmail.com"),
                new AssessmentName("Quiz 1"),
                "CS2103T");

        ModelStubWithGrade modelStub = new ModelStubWithGrade(alice, quiz, existingGrade);
        RemoveGradeCommand command = new RemoveGradeCommand("CS2103T",
                Index.fromOneBased(2), Index.fromOneBased(1));

        assertThrows(CommandException.class,
                RemoveGradeCommand.MESSAGE_INVALID_STUDENT_INDEX, (
                    ) -> command.execute(modelStub));
    }

    @Test
    public void execute_invalidCourseCode_throwsCommandException() {
        Person alice = new PersonBuilder()
                .withEmail("alice@gmail.com")
                .build();
        Assessment quiz = new Assessment("CS2103T",
                new AssessmentName("Quiz 1"),
                new MaxScore("10"));
        Grade existingGrade = new Grade(
                new StudentId("alice@gmail.com"),
                new AssessmentName("Quiz 1"),
                "CS2103T");

        ModelStubWithGrade modelStub = new ModelStubWithGrade(alice, quiz, existingGrade);
        RemoveGradeCommand command = new RemoveGradeCommand("CS9999",
                Index.fromOneBased(1), Index.fromOneBased(1));

        assertThrows(CommandException.class,
                RemoveGradeCommand.MESSAGE_INVALID_COURSE_CODE, (
                    ) -> command.execute(modelStub));
    }

    @Test
    public void execute_invalidAssessmentIndex_throwsCommandException() {
        Person alice = new PersonBuilder()
                .withEmail("alice@gmail.com")
                .build();
        Assessment quiz = new Assessment("CS2103T",
                new AssessmentName("Quiz 1"),
                new MaxScore("10"));
        Grade existingGrade = new Grade(
                new StudentId("alice@gmail.com"),
                new AssessmentName("Quiz 1"),
                "CS2103T");

        ModelStubWithGrade modelStub = new ModelStubWithGrade(alice, quiz, existingGrade);
        RemoveGradeCommand command = new RemoveGradeCommand("CS2103T",
                Index.fromOneBased(1), Index.fromOneBased(2));

        assertThrows(CommandException.class,
                RemoveGradeCommand.MESSAGE_INVALID_ASSESSMENT_INDEX, (
                    ) -> command.execute(modelStub));
    }

    @Test
    public void execute_gradeNotFound_throwsCommandException() {
        Person alice = new PersonBuilder()
                .withEmail("alice@gmail.com")
                .build();
        Assessment quiz = new Assessment("CS2103T",
                new AssessmentName("Quiz 1"),
                new MaxScore("10"));

        ModelStubWithoutGrade modelStub = new ModelStubWithoutGrade(alice, quiz);
        RemoveGradeCommand command = new RemoveGradeCommand("CS2103T",
                Index.fromOneBased(1), Index.fromOneBased(1));

        assertThrows(CommandException.class,
                RemoveGradeCommand.MESSAGE_GRADE_NOT_FOUND, (
                    ) -> command.execute(modelStub));
    }

    @Test
    public void equals() {
        RemoveGradeCommand firstCommand = new RemoveGradeCommand("CS2103T", Index.fromOneBased(1),
                Index.fromOneBased(1));
        RemoveGradeCommand firstCommandCopy = new RemoveGradeCommand("cs2103t", Index.fromOneBased(1),
                Index.fromOneBased(1));
        RemoveGradeCommand secondCommand = new RemoveGradeCommand("CS2103T", Index.fromOneBased(2),
                Index.fromOneBased(1));
        RemoveGradeCommand differentAssessmentCommand = new RemoveGradeCommand("CS2103T", Index.fromOneBased(1),
                Index.fromOneBased(2));
        RemoveGradeCommand differentCourseCommand = new RemoveGradeCommand("CS2101", Index.fromOneBased(1),
                Index.fromOneBased(1));

        assertTrue(firstCommand.equals(firstCommand));
        assertTrue(firstCommand.equals(firstCommandCopy));
        assertFalse(firstCommand.equals(null));
        assertFalse(firstCommand.equals(1));
        assertFalse(firstCommand.equals(secondCommand));
        assertFalse(firstCommand.equals(differentAssessmentCommand));
        assertFalse(firstCommand.equals(differentCourseCommand));
    }

    /**
     * Default model stub.
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
    }

    private class ModelStubWithGrade extends ModelStub {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Assessment> assessments = FXCollections.observableArrayList();
        private final Grade existingGrade;
        private Grade removedGrade;

        ModelStubWithGrade(Person person, Assessment assessment, Grade existingGrade) {
            persons.add(person);
            assessments.add(assessment);
            this.existingGrade = existingGrade;
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Assessment> getAssessmentList() {
            return assessments;
        }

        @Override
        public boolean hasGrade(Grade grade) {
            requireNonNull(grade);
            return existingGrade.isSameGrade(grade);
        }

        @Override
        public void removeGrade(Grade grade) {
            requireNonNull(grade);
            removedGrade = grade;
        }
    }

    private class ModelStubWithoutGrade extends ModelStub {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Assessment> assessments = FXCollections.observableArrayList();

        ModelStubWithoutGrade(Person person, Assessment assessment) {
            persons.add(person);
            assessments.add(assessment);
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Assessment> getAssessmentList() {
            return assessments;
        }

        @Override
        public boolean hasGrade(Grade grade) {
            requireNonNull(grade);
            return false;
        }
    }
}
