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
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentId;

public class AddGradeCommandTest {

    @Test
    public void constructor_nullCourseCode_throwsNullPointerException() {
        assertThrows(NullPointerException.class, (
            ) -> new AddGradeCommand(null, "A1234567X", Index.fromOneBased(1), new Score("10")));
    }

    @Test
    public void constructor_nullStudentId_throwsNullPointerException() {
        assertThrows(NullPointerException.class, (
            ) -> new AddGradeCommand("CS2103T", null, Index.fromOneBased(1), new Score("10")));
    }

    @Test
    public void constructor_nullAssessmentIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, (
            ) -> new AddGradeCommand("CS2103T", "A1234567X", null, new Score("10")));
    }

    @Test
    public void constructor_nullScore_throwsNullPointerException() {
        assertThrows(NullPointerException.class, (
            ) -> new AddGradeCommand("CS2103T", "A1234567X", Index.fromOneBased(1), null));
    }

    @Test
    public void execute_allInputsValid_addSuccessful() throws Exception {
        ModelStubAcceptingGradeAdded modelStub = new ModelStubAcceptingGradeAdded();

        Course course = new Course("CS2103T");
        course.addStudent(new Student("A1234567X", "Alice"));
        course.addStudent(new Student("A2345678Y", "Bob"));
        modelStub.course = course;

        modelStub.assessments.add(new Assessment("CS2103T", new AssessmentName("Quiz 1"), new MaxScore("10")));
        modelStub.assessments.add(new Assessment("CS2103T", new AssessmentName("Finals"), new MaxScore("100")));
        modelStub.assessments.add(new Assessment("CS2101", new AssessmentName("Presentation"), new MaxScore("20")));

        AddGradeCommand command = new AddGradeCommand("cs2103t",
                "A1234567X", Index.fromOneBased(1), new Score("9"));

        CommandResult commandResult = command.execute(modelStub);

        Grade expectedGrade = new Grade("CS2103T", new StudentId("A1234567X"),
                new AssessmentName("Quiz 1"), new Score("9"));

        assertEquals(String.format(Messages.MESSAGE_ADD_GRADE_SUCCESS, expectedGrade),
                commandResult.getFeedbackToUser());
        assertEquals(1, modelStub.gradesAdded.size());
        assertEquals(expectedGrade, modelStub.gradesAdded.get(0));
    }

    @Test
    public void execute_courseDoesNotExist_throwsCommandException() {
        ModelStubAcceptingGradeAdded modelStub = new ModelStubAcceptingGradeAdded();
        AddGradeCommand command = new AddGradeCommand("CS9999",
                "A1234567X", Index.fromOneBased(1), new Score("9"));

        assertThrows(CommandException.class,
                String.format(Messages.MESSAGE_COURSE_NOT_FOUND, "CS9999"), () -> command.execute(modelStub));
    }

    @Test
    public void execute_invalidStudentIndex_throwsCommandException() {
        ModelStubAcceptingGradeAdded modelStub = new ModelStubAcceptingGradeAdded();

        Course course = new Course("CS2103T");
        course.addStudent(new Student("A1234567X", "Alice"));
        modelStub.course = course;

        modelStub.assessments.add(new Assessment("CS2103T", new AssessmentName("Quiz 1"), new MaxScore("10")));

        AddGradeCommand command = new AddGradeCommand("CS2103T",
                "A2345678Y", Index.fromOneBased(1), new Score("9"));

        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_STUDENT_ID, (
            ) -> command.execute(modelStub));
    }

    @Test
    public void execute_invalidAssessmentIndex_throwsCommandException() {
        ModelStubAcceptingGradeAdded modelStub = new ModelStubAcceptingGradeAdded();

        Course course = new Course("CS2103T");
        course.addStudent(new Student("A1234567X", "Alice"));
        modelStub.course = course;

        modelStub.assessments.add(new Assessment("CS2103T", new AssessmentName("Quiz 1"), new MaxScore("10")));
        modelStub.assessments.add(new Assessment("CS2101", new AssessmentName("Presentation"), new MaxScore("20")));

        AddGradeCommand command = new AddGradeCommand("CS2103T",
                "A1234567X", Index.fromOneBased(2), new Score("9"));

        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_ASSESSMENT_INDEX, (
            ) -> command.execute(modelStub));
    }

    @Test
    public void execute_scoreExceedsMax_throwsCommandException() {
        ModelStubAcceptingGradeAdded modelStub = new ModelStubAcceptingGradeAdded();

        Course course = new Course("CS2103T");
        course.addStudent(new Student("A1234567X", "Alice"));
        modelStub.course = course;

        modelStub.assessments.add(new Assessment("CS2103T", new AssessmentName("Quiz 1"), new MaxScore("10")));

        AddGradeCommand command = new AddGradeCommand("CS2103T",
                "A1234567X", Index.fromOneBased(1), new Score("11"));

        assertThrows(CommandException.class, Messages.MESSAGE_SCORE_EXCEEDS_MAX, (
            ) -> command.execute(modelStub));
    }

    @Test
    public void execute_duplicateGrade_throwsCommandException() {
        ModelStubAcceptingGradeAdded modelStub = new ModelStubAcceptingGradeAdded();

        Course course = new Course("CS2103T");
        course.addStudent(new Student("A1234567X", "Alice"));
        modelStub.course = course;

        Assessment assessment = new Assessment("CS2103T", new AssessmentName("Quiz 1"), new MaxScore("10"));
        modelStub.assessments.add(assessment);

        Grade existingGrade = new Grade("CS2103T", new StudentId("A1234567X"),
                new AssessmentName("Quiz 1"), new Score("9"));
        modelStub.existingGrade = existingGrade;

        AddGradeCommand command = new AddGradeCommand("CS2103T",
                "A1234567X", Index.fromOneBased(1), new Score("9"));

        assertThrows(CommandException.class, Messages.MESSAGE_DUPLICATE_GRADE, (
            ) -> command.execute(modelStub));
    }

    @Test
    public void equals() {
        AddGradeCommand firstCommand = new AddGradeCommand("CS2103T", "A1234567X", Index.fromOneBased(1),
                new Score("9"));
        AddGradeCommand firstCommandCopy = new AddGradeCommand("cs2103t", "A1234567X", Index.fromOneBased(1),
                new Score("9"));
        AddGradeCommand secondCommand = new AddGradeCommand("CS2103T", "A2345678Y", Index.fromOneBased(1),
                new Score("9"));
        AddGradeCommand differentAssessmentCommand = new AddGradeCommand("CS2103T", "A1234567X", Index.fromOneBased(2),
                new Score("9"));
        AddGradeCommand differentScoreCommand = new AddGradeCommand("CS2103T", "A1234567X", Index.fromOneBased(1),
                new Score("10"));
        AddGradeCommand differentCourseCommand = new AddGradeCommand("CS2101", "A1234567X", Index.fromOneBased(1),
                new Score("9"));

        assertTrue(firstCommand.equals(firstCommand));
        assertTrue(firstCommand.equals(firstCommandCopy));
        assertFalse(firstCommand.equals(null));
        assertFalse(firstCommand.equals(1));
        assertFalse(firstCommand.equals(secondCommand));
        assertFalse(firstCommand.equals(differentAssessmentCommand));
        assertFalse(firstCommand.equals(differentScoreCommand));
        assertFalse(firstCommand.equals(differentCourseCommand));
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
            // Display mode updates are part of successful command execution.
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

        @Override
        public void showAllAssessments() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void showAssessmentsForCourse(String courseCode) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void refreshLastAssessmentListFilter() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void showGradesForStudent(String studentId) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void showGradesForCourse(String courseCode) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void showGradesForCourseAssessment(String courseCode, String assessmentName) {
            // Grade display updates are part of successful command execution.
        }

        @Override
        public void refreshLastGradeListFilter() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A model stub that always accepts the grade being added.
     */
    private class ModelStubAcceptingGradeAdded extends ModelStub {
        private Course course;
        private final ObservableList<Assessment> assessments = FXCollections.observableArrayList();
        private final ArrayList<Grade> gradesAdded = new ArrayList<>();
        private Grade existingGrade;

        @Override
        public Optional<Course> getCourse(String courseCode) {
            requireNonNull(courseCode);
            if (course != null && course.getCourseCode().equalsIgnoreCase(courseCode)) {
                return Optional.of(course);
            }
            return Optional.empty();
        }

        @Override
        public boolean hasCourse(String courseCode) {
            requireNonNull(courseCode);
            return course != null && course.getCourseCode().equalsIgnoreCase(courseCode);
        }

        @Override
        public boolean isStudentEnrolled(String courseCode, String studentId) {
            requireNonNull(courseCode);
            requireNonNull(studentId);
            return course != null
                    && course.getCourseCode().equalsIgnoreCase(courseCode)
                    && course.getStudents().stream()
                    .anyMatch(student -> student.getStudentId().equalsIgnoreCase(studentId));
        }

        @Override
        public Optional<Assessment> getAssessmentForCourseByIndex(String courseCode, Index assessmentIndex) {
            requireNonNull(courseCode);
            requireNonNull(assessmentIndex);
            ObservableList<Assessment> courseAssessments = assessments.filtered(
                    assessment -> assessment.getCourseCode().equalsIgnoreCase(courseCode));
            if (assessmentIndex.getZeroBased() >= courseAssessments.size()) {
                return Optional.empty();
            }
            return Optional.of(courseAssessments.get(assessmentIndex.getZeroBased()));
        }

        @Override
        public ObservableList<Assessment> getAssessmentList() {
            return assessments;
        }

        @Override
        public boolean hasGrade(Grade grade) {
            requireNonNull(grade);
            return existingGrade != null && existingGrade.isSameGrade(grade);
        }

        @Override
        public void addGrade(Grade grade) {
            requireNonNull(grade);
            gradesAdded.add(grade);
        }
    }
}
