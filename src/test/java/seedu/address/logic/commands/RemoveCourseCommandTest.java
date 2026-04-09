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

public class RemoveCourseCommandTest {

    @Test
    public void constructor_nullCourseCode_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RemoveCourseCommand(null));
    }

    @Test
    public void execute_courseCodeAcceptedByModel_removeSuccessful() throws Exception {
        ModelStubWithCourse modelStub = new ModelStubWithCourse("CS2103T");
        String validCourseCode = "CS2103T";

        CommandResult commandResult = new RemoveCourseCommand(Arrays.asList(validCourseCode)).execute(modelStub);

        assertEquals(String.format(RemoveCourseCommand.MESSAGE_SUCCESS, validCourseCode),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validCourseCode), modelStub.coursesRemoved);
    }

    @Test
    public void execute_multipleCourseCodesAcceptedByModel_removeSuccessful() throws Exception {
        ModelStubWithCourses modelStub = new ModelStubWithCourses("CS2103T", "CS2101");

        CommandResult commandResult = new RemoveCourseCommand(Arrays.asList("CS2103T", "CS2101")).execute(modelStub);

        assertEquals(String.format(RemoveCourseCommand.MESSAGE_SUCCESS, "CS2103T, CS2101"),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList("CS2103T", "CS2101"), modelStub.coursesRemoved);
    }

    @Test
    public void execute_courseNotFound_throwsCommandException() {
        String nonexistentCourseCode = "CS9999";
        RemoveCourseCommand removeCourseCommand = new RemoveCourseCommand(Arrays.asList(nonexistentCourseCode));
        ModelStub modelStub = new ModelStubWithoutCourse();

        assertThrows(CommandException.class,
            String.format(RemoveCourseCommand.MESSAGE_COURSE_NOT_FOUND, nonexistentCourseCode), ()
                                                -> removeCourseCommand.execute(modelStub));
    }

    @Test
    public void execute_duplicateCourseCodeInputsIgnoringCase_throwsCommandException() {
        RemoveCourseCommand removeCourseCommand =
                new RemoveCourseCommand(Arrays.asList("CS1231S", "cs1231S", "cS1231s"));
        ModelStub modelStub = new ModelStubWithCourses("CS1231S");

        assertThrows(CommandException.class,
                String.format(RemoveCourseCommand.MESSAGE_DUPLICATE_COURSE_INPUT, "CS1231S"), ()
                                -> removeCourseCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        String courseCodeA = "CS2103T";
        String courseCodeB = "CS2101";
        RemoveCourseCommand removeCourseCommandA = new RemoveCourseCommand(Arrays.asList(courseCodeA));
        RemoveCourseCommand removeCourseCommandB = new RemoveCourseCommand(Arrays.asList(courseCodeB));

        // same object -> returns true
        assertTrue(removeCourseCommandA.equals(removeCourseCommandA));

        // same values -> returns true
        RemoveCourseCommand removeCourseCommandACopy = new RemoveCourseCommand(Arrays.asList(courseCodeA));
        assertTrue(removeCourseCommandA.equals(removeCourseCommandACopy));

        // different types -> returns false
        assertFalse(removeCourseCommandA.equals(1));

        // null -> returns false
        assertFalse(removeCourseCommandA.equals(null));

        // different course code -> returns false
        assertFalse(removeCourseCommandA.equals(removeCourseCommandB));
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
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
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
            // no-op for GUI state updates
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

    /**
     * A Model stub that contains a single course.
     */
    private class ModelStubWithCourse extends ModelStub {
        final ArrayList<String> coursesRemoved = new ArrayList<>();
        private final String courseCode;

        ModelStubWithCourse(String courseCode) {
            requireNonNull(courseCode);
            this.courseCode = courseCode;
        }

        @Override
        public boolean hasCourse(String courseCode) {
            requireNonNull(courseCode);
            // Course exists if it hasn't been removed yet
            return this.courseCode.equals(courseCode) && !coursesRemoved.contains(courseCode);
        }

        @Override
        public void removeCourse(Course course) {
            requireNonNull(course);
            coursesRemoved.add(course.getCourseCode());
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that contains multiple courses.
     */
    private class ModelStubWithCourses extends ModelStub {
        final ArrayList<String> coursesRemoved = new ArrayList<>();
        private final ArrayList<String> existingCourses = new ArrayList<>();

        ModelStubWithCourses(String... courseCodes) {
            existingCourses.addAll(Arrays.asList(courseCodes));
        }

        @Override
        public boolean hasCourse(String courseCode) {
            requireNonNull(courseCode);
            return existingCourses.contains(courseCode) && !coursesRemoved.contains(courseCode);
        }

        @Override
        public void removeCourse(Course course) {
            requireNonNull(course);
            coursesRemoved.add(course.getCourseCode());
        }
    }

    /**
     * A Model stub that does not contain any course.
     */
    private class ModelStubWithoutCourse extends ModelStub {
        @Override
        public boolean hasCourse(String courseCode) {
            return false;
        }
    }
}
