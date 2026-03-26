package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.DisplayMode;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.assessment.Assessment;
import seedu.address.model.course.Course;
import seedu.address.model.grade.Grade;
import seedu.address.model.person.Person;

public class AddCourseCommandTest {

    @Test
    public void constructor_nullCourseCodes_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCourseCommand(null));
    }

    @Test
    public void execute_singleCourseCodeAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingCourseAdded modelStub = new ModelStubAcceptingCourseAdded();
        String validCourseCode = "CS2103T";

        CommandResult commandResult = new AddCourseCommand(Arrays.asList(validCourseCode)).execute(modelStub);

        assertEquals(String.format(AddCourseCommand.MESSAGE_SUCCESS, validCourseCode),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validCourseCode), modelStub.coursesAdded);
    }

    @Test
    public void execute_multipleCourseCodes_addSuccessful() throws Exception {
        ModelStubAcceptingCourseAdded modelStub = new ModelStubAcceptingCourseAdded();
        String courseCodeA = "CS2103T";
        String courseCodeB = "CS2101";

        CommandResult commandResult = new AddCourseCommand(Arrays.asList(courseCodeA, courseCodeB))
                .execute(modelStub);

        assertEquals(String.format(AddCourseCommand.MESSAGE_SUCCESS, courseCodeA + ", " + courseCodeB),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(courseCodeA, courseCodeB), modelStub.coursesAdded);
    }

    @Test
    public void execute_duplicateCourseCode_throwsCommandException() {
        String validCourseCode = "CS2103T";
        AddCourseCommand addCourseCommand = new AddCourseCommand(Arrays.asList(validCourseCode));
        ModelStub modelStub = new ModelStubWithCourse(validCourseCode);

        assertThrows(CommandException.class,
            String.format(AddCourseCommand.MESSAGE_DUPLICATE_COURSE, validCourseCode), () ->
                                                    addCourseCommand.execute(modelStub));
    }

    @Test
    public void execute_duplicateCourseCodeInList_throwsCommandException() {
        String courseCodeA = "CS2103T";
        String courseCodeB = "CS2101";
        AddCourseCommand addCourseCommand = new AddCourseCommand(Arrays.asList(courseCodeA, courseCodeB));
        ModelStub modelStub = new ModelStubWithCourse(courseCodeB);

        assertThrows(CommandException.class,
            String.format(AddCourseCommand.MESSAGE_DUPLICATE_COURSE, courseCodeB), () ->
                                                    addCourseCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        String courseCodeA = "CS2103T";
        String courseCodeB = "CS2101";
        AddCourseCommand addCourseCommandA = new AddCourseCommand(Arrays.asList(courseCodeA));
        AddCourseCommand addCourseCommandB = new AddCourseCommand(Arrays.asList(courseCodeB));

        // same object -> returns true
        assertTrue(addCourseCommandA.equals(addCourseCommandA));

        // same values -> returns true
        AddCourseCommand addCourseCommandACopy = new AddCourseCommand(Arrays.asList(courseCodeA));
        assertTrue(addCourseCommandA.equals(addCourseCommandACopy));

        // different types -> returns false
        assertFalse(addCourseCommandA.equals(1));

        // null -> returns false
        assertFalse(addCourseCommandA.equals(null));

        // different course code -> returns false
        assertFalse(addCourseCommandA.equals(addCourseCommandB));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        private ObservableList<Grade> gradeList = FXCollections.observableArrayList();

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
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
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
        public ObservableList<Grade> getGradeList() {
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
        public ObservableList<Grade> getGradesByStudentId(String studentId) {
            return FXCollections.observableArrayList(
                    gradeList.stream()
                            .filter(grade -> grade.getStudentId().equals(studentId))
                            .collect(Collectors.toList()));
        }

        @Override
        public ObservableList<Grade> getGradesByCourse(String courseCode) {
            return FXCollections.observableArrayList(
                    gradeList.stream()
                            .filter(grade -> grade.getCourseCode().equals(courseCode))
                            .collect(Collectors.toList()));
        }

        @Override
        public ObservableList<Grade> getGradesByCourseAndAssessment(String courseCode, String assessmentName) {
            return FXCollections.observableArrayList(
                    gradeList.stream()
                            .filter(grade -> grade.getCourseCode().equals(courseCode)
                                    && grade.getAssessmentName().equals(assessmentName))
                            .collect(Collectors.toList()));
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
        public void removeGrade(Grade grade) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasCourse(String courseCode) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeCourse(Course course) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Course> getCourseList() {
            return FXCollections.observableArrayList();
        }

        public ObservableList<Assessment> getAssessmentList() {
            return FXCollections.observableArrayList();
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
        public void addStudentToCourse(String courseCode, seedu.address.model.student.Student student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeStudentFromCourse(String courseCode, String studentId) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<seedu.address.model.student.Student> getFilteredStudentList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCurrentCourseForDisplay(java.util.Optional<String> courseCode) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public java.util.Optional<String> getCurrentCourseForDisplay() {
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

    /**
     * A Model stub that contains a single course.
     */
    private class ModelStubWithCourse extends ModelStub {
        private final String courseCode;

        ModelStubWithCourse(String courseCode) {
            requireNonNull(courseCode);
            this.courseCode = courseCode;
        }

        @Override
        public boolean hasCourse(String courseCode) {
            requireNonNull(courseCode);
            return this.courseCode.equals(courseCode);
        }
    }

    /**
     * A Model stub that always accept the course being added.
     */
    private class ModelStubAcceptingCourseAdded extends ModelStub {
        final ArrayList<String> coursesAdded = new ArrayList<>();

        @Override
        public boolean hasCourse(String courseCode) {
            requireNonNull(courseCode);
            return coursesAdded.contains(courseCode);
        }

        @Override
        public void addCourse(Course course) {
            requireNonNull(course);
            coursesAdded.add(course.getCourseCode());
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
