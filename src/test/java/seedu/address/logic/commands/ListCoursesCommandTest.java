package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.AddressBook;
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

public class ListCoursesCommandTest {

    @Test
    public void execute_emptyCourseList_success() {
        ModelStubWithNoCourses modelStub = new ModelStubWithNoCourses();
        CommandResult result = new ListCoursesCommand().execute(modelStub);

        assertEquals("", result.getFeedbackToUser());
    }

    @Test
    public void execute_singleCourse_success() {
        ModelStubWithCourses modelStub = new ModelStubWithCourses(new ArrayList<>(
                java.util.Arrays.asList(new Course("CS2103T"))));
        CommandResult result = new ListCoursesCommand().execute(modelStub);

        String expectedOutput = "";
        assertEquals(expectedOutput, result.getFeedbackToUser());
    }

    @Test
    public void execute_multipleCourses_success() {
        List<Course> courses = new ArrayList<>();
        courses.add(new Course("CS2103T"));
        courses.add(new Course("CS2101"));
        courses.add(new Course("CS2106"));

        ModelStubWithCourses modelStub = new ModelStubWithCourses(courses);
        CommandResult result = new ListCoursesCommand().execute(modelStub);

        String expectedOutput = "";
        assertEquals(expectedOutput, result.getFeedbackToUser());
    }

    @Test
    public void execute_assessmentsAddedAfterwards_courseAssessmentViewUpdates() {
        Course course = new Course("CS2103T");
        ObservableList<Assessment> assessments = FXCollections.observableArrayList(
                new Assessment("CS2103T", new AssessmentName("Midterm"), new MaxScore("100")),
                new Assessment("CS2101", new AssessmentName("Quiz"), new MaxScore("20")));
        ModelStubWithCourses modelStub = new ModelStubWithCourses(List.of(course), assessments);

        new ListCoursesCommand().execute(modelStub);
        assertEquals(1, course.getAssessments().size());

        assessments.add(new Assessment("CS2103T", new AssessmentName("Final"), new MaxScore("100")));
        new ListCoursesCommand().execute(modelStub);
        assertEquals(2, course.getAssessments().size());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        protected ObservableList<Assessment> assessmentList = FXCollections.observableArrayList();
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
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Assessment> getAssessmentList() {
            return assessmentList;
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
    }

    /**
     * A Model stub with no courses.
     */
    private class ModelStubWithNoCourses extends ModelStub {
        @Override
        public ObservableList<Course> getCourseList() {
            return FXCollections.observableArrayList();
        }
    }

    /**
     * A Model stub that contains courses.
     */
    private class ModelStubWithCourses extends ModelStub {
        private final ObservableList<Course> courses;

        ModelStubWithCourses(List<Course> courseList) {
            this(courseList, FXCollections.observableArrayList());
        }

        ModelStubWithCourses(List<Course> courseList, ObservableList<Assessment> assessmentList) {
            requireNonNull(courseList);
            this.courses = FXCollections.observableArrayList(courseList);
            this.assessmentList = assessmentList;
        }

        @Override
        public ObservableList<Course> getCourseList() {
            return courses;
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
