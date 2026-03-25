package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
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

    @Test
    public void execute_noAssessments_returnsNoAssessmentsMessage() {
        ModelStub modelStub = new ModelStub(FXCollections.observableArrayList());

        CommandResult result = new ListAssessmentsCommand().execute(modelStub);

        assertEquals("No assessments found.", result.getFeedbackToUser());
    }

    @Test
    public void execute_singleCourseMultipleAssessments_listsAssessmentsWithIndexes() {
        ObservableList<Assessment> assessments = FXCollections.observableArrayList(
                new Assessment("CS2103T", new AssessmentName("Quiz 1"), new MaxScore("10")),
                new Assessment("CS2103T", new AssessmentName("Finals"), new MaxScore("100")));
        ModelStub modelStub = new ModelStub(assessments);

        CommandResult result = new ListAssessmentsCommand().execute(modelStub);

        String expected = "Assessments:\n"
                + "\nCourse: CS2103T (Index: 1)\n"
                + "    1. Assessment Name: Quiz 1 (Max Score: 10) in CS2103T\n"
                + "    2. Assessment Name: Finals (Max Score: 100) in CS2103T";
        assertEquals(expected, result.getFeedbackToUser());
    }

    @Test
    public void execute_multipleCourses_outputContainsAllCourseSections() {
        ObservableList<Assessment> assessments = FXCollections.observableArrayList(
                new Assessment("CS2103T", new AssessmentName("Quiz 1"), new MaxScore("10")),
                new Assessment("CS2101", new AssessmentName("Participation"), new MaxScore("20")));
        ModelStub modelStub = new ModelStub(assessments);

        CommandResult result = new ListAssessmentsCommand().execute(modelStub);

        String feedback = result.getFeedbackToUser();
        assertTrue(feedback.startsWith("Assessments:"));
        assertTrue(feedback.contains("Course: CS2103T (Index:"));
        assertTrue(feedback.contains("1. Assessment Name: Quiz 1 (Max Score: 10) in CS2103T"));
        assertTrue(feedback.contains("Course: CS2101 (Index:"));
        assertTrue(feedback.contains("1. Assessment Name: Participation (Max Score: 20) in CS2101"));
    }

    /**
     * A default model stub that only supports getAssessmentList().
     */
    private static class ModelStub implements Model {
        private final ObservableList<Assessment> assessments;

        private ModelStub(ObservableList<Assessment> assessments) {
            this.assessments = assessments;
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
        public ObservableList<Assessment> getAssessmentList() {
            return assessments;
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
            throw new AssertionError("This method should not be called.");
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
        public void setCurrentCourseForDisplay(java.util.Optional<String> courseCode) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public java.util.Optional<String> getCurrentCourseForDisplay() {
            throw new AssertionError("This method should not be called.");
        }
    }
}
