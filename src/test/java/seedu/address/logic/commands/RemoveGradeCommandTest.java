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
import seedu.address.model.student.StudentId;

public class RemoveGradeCommandTest {

    @Test
    public void constructor_nullCourseCode_throwsNullPointerException() {
        assertThrows(NullPointerException.class, (
            ) -> new RemoveGradeCommand(null, "A1234567X", Index.fromOneBased(1)));
    }

    @Test
    public void constructor_nullStudentId_throwsNullPointerException() {
        assertThrows(NullPointerException.class, (
            ) -> new RemoveGradeCommand("CS2103T", null, Index.fromOneBased(1)));
    }

    @Test
    public void constructor_nullAssessmentIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, (
            ) -> new RemoveGradeCommand("CS2103T", "A1234567X", null));
    }

    @Test
    public void execute_validInputs_removeSuccessful() throws Exception {
        Course course = new Course("CS2103T");
        course.addStudent(new Student("A1234567X", "Alice"));

        Assessment quiz = new Assessment("CS2103T",
                new AssessmentName("Quiz 1"),
                new MaxScore("10"));

        Grade existingGrade = new Grade(
                new StudentId("A1234567X"),
                new AssessmentName("Quiz 1"),
                "CS2103T");

        ModelStubWithGrade modelStub = new ModelStubWithGrade(course, quiz, existingGrade);

        RemoveGradeCommand command = new RemoveGradeCommand("cs2103t",
                "A1234567X", Index.fromOneBased(1));

        CommandResult result = command.execute(modelStub);

        assertEquals(String.format(Messages.MESSAGE_REMOVE_GRADE_SUCCESS,
                "A1234567X", "Quiz 1", "CS2103T"), result.getFeedbackToUser());
        assertEquals(existingGrade, modelStub.removedGrade);
    }

    @Test
    public void execute_invalidStudentId_throwsCommandException() {
        Course course = new Course("CS2103T");
        course.addStudent(new Student("A1234567X", "Alice"));

        Assessment quiz = new Assessment("CS2103T",
                new AssessmentName("Quiz 1"),
                new MaxScore("10"));

        Grade existingGrade = new Grade(
                new StudentId("A1234567X"),
                new AssessmentName("Quiz 1"),
                "CS2103T");

        ModelStubWithGrade modelStub = new ModelStubWithGrade(course, quiz, existingGrade);
        RemoveGradeCommand command = new RemoveGradeCommand("CS2103T",
                "A2345678Y", Index.fromOneBased(1));

        assertThrows(CommandException.class,
                String.format(Messages.MESSAGE_INVALID_STUDENT_ID, "A2345678Y"), (
                    ) -> command.execute(modelStub));
    }

    @Test
    public void execute_invalidCourseCode_throwsCommandException() {
        Course course = new Course("CS2103T");
        course.addStudent(new Student("A1234567X", "Alice"));

        Assessment quiz = new Assessment("CS2103T",
                new AssessmentName("Quiz 1"),
                new MaxScore("10"));

        Grade existingGrade = new Grade(
                new StudentId("A1234567X"),
                new AssessmentName("Quiz 1"),
                "CS2103T");

        ModelStubWithGrade modelStub = new ModelStubWithGrade(course, quiz, existingGrade);
        RemoveGradeCommand command = new RemoveGradeCommand("CS9999",
                "A1234567X", Index.fromOneBased(1));

        assertThrows(CommandException.class,
                String.format(Messages.MESSAGE_COURSE_NOT_FOUND, "CS9999"), () -> command.execute(modelStub));
    }

    @Test
    public void execute_invalidAssessmentIndex_throwsCommandException() {
        Course course = new Course("CS2103T");
        course.addStudent(new Student("A1234567X", "Alice"));

        Assessment quiz = new Assessment("CS2103T",
                new AssessmentName("Quiz 1"),
                new MaxScore("10"));

        Grade existingGrade = new Grade(
                new StudentId("A1234567X"),
                new AssessmentName("Quiz 1"),
                "CS2103T");

        ModelStubWithGrade modelStub = new ModelStubWithGrade(course, quiz, existingGrade);
        RemoveGradeCommand command = new RemoveGradeCommand("CS2103T",
                "A1234567X", Index.fromOneBased(2));

        assertThrows(CommandException.class,
                String.format(Messages.MESSAGE_INVALID_ASSESSMENT_INDEX, 2), (
                    ) -> command.execute(modelStub));
    }

    @Test
    public void execute_gradeNotFound_throwsCommandException() {
        Course course = new Course("CS2103T");
        course.addStudent(new Student("A1234567X", "Alice"));

        Assessment quiz = new Assessment("CS2103T",
                new AssessmentName("Quiz 1"),
                new MaxScore("10"));

        ModelStubWithoutGrade modelStub = new ModelStubWithoutGrade(course, quiz);
        RemoveGradeCommand command = new RemoveGradeCommand("CS2103T",
                "A1234567X", Index.fromOneBased(1));

        assertThrows(CommandException.class,
                String.format(Messages.MESSAGE_GRADE_NOT_FOUND), (
                    ) -> command.execute(modelStub));
    }

    @Test
    public void equals() {
        RemoveGradeCommand firstCommand = new RemoveGradeCommand("CS2103T", "A1234567X",
                Index.fromOneBased(1));
        RemoveGradeCommand firstCommandCopy = new RemoveGradeCommand("cs2103t", "A1234567X",
                Index.fromOneBased(1));
        RemoveGradeCommand secondCommand = new RemoveGradeCommand("CS2103T", "A2345678Y",
                Index.fromOneBased(1));
        RemoveGradeCommand differentAssessmentCommand = new RemoveGradeCommand("CS2103T", "A1234567X",
                Index.fromOneBased(2));
        RemoveGradeCommand differentCourseCommand = new RemoveGradeCommand("CS2101", "A1234567X",
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

    private class ModelStubWithGrade extends ModelStub {
        private final Course course;
        private final ObservableList<Assessment> assessments = FXCollections.observableArrayList();
        private final Grade existingGrade;
        private Grade removedGrade;

        ModelStubWithGrade(Course course, Assessment assessment, Grade existingGrade) {
            this.course = course;
            this.assessments.add(assessment);
            this.existingGrade = existingGrade;
        }

        @Override
        public boolean hasCourse(String courseCode) {
            return course.getCourseCode().equalsIgnoreCase(courseCode);
        }

        @Override
        public Optional<Course> getCourse(String courseCode) {
            if (course.getCourseCode().equalsIgnoreCase(courseCode)) {
                return Optional.of(course);
            }
            return Optional.empty();
        }

        @Override
        public boolean isStudentEnrolled(String courseCode, String studentId) {
            return course.getCourseCode().equalsIgnoreCase(courseCode)
                    && course.getStudents().stream()
                    .anyMatch(student -> student.getStudentId().equalsIgnoreCase(studentId));
        }

        @Override
        public Optional<Assessment> getAssessmentForCourseByIndex(String courseCode, Index assessmentIndex) {
            if (!course.getCourseCode().equalsIgnoreCase(courseCode)) {
                return Optional.empty();
            }
            if (assessmentIndex.getZeroBased() >= assessments.size()) {
                return Optional.empty();
            }
            return Optional.of(assessments.get(assessmentIndex.getZeroBased()));
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
        private final Course course;
        private final ObservableList<Assessment> assessments = FXCollections.observableArrayList();

        ModelStubWithoutGrade(Course course, Assessment assessment) {
            this.course = course;
            this.assessments.add(assessment);
        }

        @Override
        public boolean hasCourse(String courseCode) {
            return course.getCourseCode().equalsIgnoreCase(courseCode);
        }

        @Override
        public Optional<Course> getCourse(String courseCode) {
            if (course.getCourseCode().equalsIgnoreCase(courseCode)) {
                return Optional.of(course);
            }
            return Optional.empty();
        }

        @Override
        public boolean isStudentEnrolled(String courseCode, String studentId) {
            return course.getCourseCode().equalsIgnoreCase(courseCode)
                    && course.getStudents().stream()
                    .anyMatch(student -> student.getStudentId().equalsIgnoreCase(studentId));
        }

        @Override
        public Optional<Assessment> getAssessmentForCourseByIndex(String courseCode, Index assessmentIndex) {
            if (!course.getCourseCode().equalsIgnoreCase(courseCode)) {
                return Optional.empty();
            }
            if (assessmentIndex.getZeroBased() >= assessments.size()) {
                return Optional.empty();
            }
            return Optional.of(assessments.get(assessmentIndex.getZeroBased()));
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
