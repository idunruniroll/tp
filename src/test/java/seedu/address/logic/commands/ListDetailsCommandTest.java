package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import seedu.address.model.assessment.AssessmentName;
import seedu.address.model.assessment.MaxScore;
import seedu.address.model.course.Course;
import seedu.address.model.grade.Grade;
import seedu.address.model.person.Person;
import seedu.address.model.student.Student;

public class ListDetailsCommandTest {

    @Test
    public void execute_singleCourse_success() throws Exception {
        Course course = new Course("CS2103T");
        course.addStudent(new Student("A1234567X", "Alice"));

        ModelStub modelStub = new ModelStub();
        modelStub.courses.put("CS2103T", course);

        ListDetailsCommand command = new ListDetailsCommand(List.of("CS2103T"));
        CommandResult result = command.execute(modelStub);

        String expectedOutput = "";

        assertEquals(expectedOutput, result.getFeedbackToUser());
    }

    @Test
    public void execute_courseNotFound_throwsCommandException() {
        ModelStub modelStub = new ModelStub();

        ListDetailsCommand command = new ListDetailsCommand(List.of("CS9999"));

        assertThrows(CommandException.class, () -> command.execute(modelStub));
    }

    private static class ModelStub implements Model {
        private final Map<String, Course> courses = new HashMap<>();

        @Override
        public Optional<Course> getCourse(String courseCode) {
            return Optional.ofNullable(courses.get(courseCode.toUpperCase()));
        }

        @Override
        public boolean hasCourse(String courseCode) {
            return courses.containsKey(courseCode.toUpperCase());
        }

        @Override
        public ObservableList<Course> getCourseList() {
            return FXCollections.observableArrayList(courses.values());
        }

        @Override
        public void removeCourse(Course course) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addCourse(Course course) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Assessment> getAssessmentList() {
            return FXCollections.observableArrayList(
                    new Assessment("CS2103T", new AssessmentName("Quiz 1"), new MaxScore("10")));
        }

        // Remaining methods not needed for these tests
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
        public ObservableList<Student> getFilteredStudentList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCurrentCourseForDisplay(Optional<String> courseCode) {
            // no-op for GUI state updates
        }

        @Override
        public Optional<String> getCurrentCourseForDisplay() {
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
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Grade> getGradesByCourse(String courseCode) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Grade> getGradesByCourseAndAssessment(String courseCode, String assessmentName) {
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
            // no-op for GUI state updates
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
