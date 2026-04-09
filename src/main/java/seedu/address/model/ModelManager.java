package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.model.assessment.Assessment;
import seedu.address.model.course.Course;
import seedu.address.model.grade.Grade;
import seedu.address.model.student.Student;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Course> filteredCourses;
    private final FilteredList<Assessment> filteredAssessments;
    private final FilteredList<Grade> filteredGrades;
    private DisplayMode displayMode = DisplayMode.COURSES;
    private Optional<String> currentCourseForDisplay = Optional.empty();
    private final ObservableList<Student> filteredStudents = FXCollections.observableArrayList();
    private final ObservableList<Course> detailedCoursesForDisplay = FXCollections.observableArrayList();

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredCourses = new FilteredList<>(this.addressBook.getCourseList());
        filteredAssessments = new FilteredList<>(this.addressBook.getAssessmentList());
        filteredGrades = new FilteredList<>(this.addressBook.getGradeList());
    }

    /**
     * Creates a ModelManager with empty data.
     */
    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    // =========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    // =========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasAssessment(Assessment assessment) {
        requireNonNull(assessment);
        return addressBook.hasAssessment(assessment);
    }

    @Override
    public void addAssessment(Assessment assessment) {
        addressBook.addAssessment(assessment);
    }

    @Override
    public void removeAssessment(Assessment assessment) {
        addressBook.removeAssessment(assessment);
    }

    @Override
    public ObservableList<Assessment> getAssessmentList() {
        return addressBook.getAssessmentList();
    }

    @Override
    public void updateFilteredAssessmentList(Predicate<Assessment> predicate) {
        requireNonNull(predicate);
        filteredAssessments.setPredicate(predicate);
    }

    @Override
    public boolean hasGrade(Grade grade) {
        requireNonNull(grade);
        return addressBook.hasGrade(grade);
    }

    @Override
    public void addGrade(Grade grade) {
        addressBook.addGrade(grade);
    }

    @Override
    public void removeGrade(Grade grade) {
        addressBook.removeGrade(grade);
    }

    @Override
    public ObservableList<Grade> getGradeList() {
        return addressBook.getGradeList();
    }

    // =========== Grade filtering methods =============================================================

    @Override
    public ObservableList<Grade> getGradesByStudentId(String studentId) {
        requireNonNull(studentId);
        String normalizedStudentId = studentId.trim().toUpperCase();
        return FXCollections.observableArrayList(
                addressBook.getGradeList().stream()
                        .filter(grade -> grade.getStudentId().toString().equalsIgnoreCase(normalizedStudentId))
                        .collect(Collectors.toList()));
    }

    @Override
    public ObservableList<Grade> getGradesByCourse(String courseCode) {
        requireNonNull(courseCode);
        return FXCollections.observableArrayList(
                addressBook.getGradeList().stream()
                        .filter(grade -> grade.getCourseCode().equalsIgnoreCase(courseCode))
                        .collect(Collectors.toList()));
    }

    @Override
    public ObservableList<Grade> getGradesByCourseAndAssessment(String courseCode, String assessmentName) {
        requireNonNull(courseCode);
        requireNonNull(assessmentName);
        String trimmedAssessmentName = assessmentName.trim();
        return FXCollections.observableArrayList(
                addressBook.getGradeList().stream()
                        .filter(grade -> grade.getCourseCode().equalsIgnoreCase(courseCode)
                                && grade.getAssessmentName().toString().equals(trimmedAssessmentName))
                        .collect(Collectors.toList()));
    }

    @Override
    public void updateFilteredGradeList(Predicate<Grade> predicate) {
        requireNonNull(predicate);
        filteredGrades.setPredicate(predicate);
    }

    // =========== Course / Student operations =============================================================

    @Override
    public ObservableList<Course> getCourseList() {
        return addressBook.getCourseList();
    }

    @Override
    public boolean hasCourse(String courseCode) {
        requireNonNull(courseCode);
        return addressBook.hasCourse(new Course(courseCode));
    }

    @Override
    public Optional<Course> getCourse(String courseCode) {
        requireNonNull(courseCode);
        return addressBook.getCourse(courseCode);
    }

    @Override
    public void addCourse(Course course) {
        requireNonNull(course);
        addressBook.addCourse(course);
    }

    @Override
    public void removeCourse(Course course) {
        requireNonNull(course);
        addressBook.removeCourse(course);
    }

    @Override
    public void addStudentToCourse(String courseCode, Student student) {
        requireAllNonNull(courseCode, student);
        addressBook.addStudentToCourse(courseCode, student);
        if (currentCourseForDisplay.isPresent()
                && currentCourseForDisplay.get().equalsIgnoreCase(courseCode)) {
            refreshFilteredStudents();
        }
    }

    @Override
    public void removeStudentFromCourse(String courseCode, String studentId) {
        requireAllNonNull(courseCode, studentId);
        addressBook.removeStudentFromCourse(courseCode, studentId);
        if (currentCourseForDisplay.isPresent()
                && currentCourseForDisplay.get().equalsIgnoreCase(courseCode)) {
            refreshFilteredStudents();
        }
    }

    @Override
    public boolean isStudentEnrolled(String courseCode, String studentId) {
        requireAllNonNull(courseCode, studentId);
        return getCourse(courseCode)
                .map(course -> course.hasStudent(studentId))
                .orElse(false);
    }

    @Override
    public Optional<Assessment> getAssessmentForCourseByIndex(String courseCode, Index assessmentIndex) {
        requireAllNonNull(courseCode, assessmentIndex);
        ObservableList<Assessment> courseAssessments = getAssessmentsForCourseInDisplayOrder(courseCode);
        int zeroBased = assessmentIndex.getZeroBased();
        if (zeroBased >= courseAssessments.size()) {
            return Optional.empty();
        }
        return Optional.of(courseAssessments.get(zeroBased));
    }

    // =========== Student GUI display state =============================================================

    @Override
    public ObservableList<Student> getFilteredStudentList() {
        return FXCollections.unmodifiableObservableList(filteredStudents);
    }

    @Override
    public ObservableList<Course> getFilteredCourseList() {
        return filteredCourses;
    }

    @Override
    public ObservableList<Course> getDetailedCourseList() {
        return FXCollections.unmodifiableObservableList(detailedCoursesForDisplay);
    }

    @Override
    public void setDetailedCoursesForDisplay(List<Course> courses) {
        requireNonNull(courses);
        detailedCoursesForDisplay.setAll(courses);
    }

    @Override
    public void setCurrentCourseForDisplay(Optional<String> courseCode) {
        requireNonNull(courseCode);
        currentCourseForDisplay = courseCode.map(String::trim);
        refreshFilteredStudents();
    }

    @Override
    public Optional<String> getCurrentCourseForDisplay() {
        return currentCourseForDisplay;
    }

    @Override
    public ObservableList<Assessment> getFilteredAssessmentList() {
        return filteredAssessments;
    }

    @Override
    public ObservableList<Grade> getFilteredGradeList() {
        return filteredGrades;
    }

    @Override
    public void setDisplayMode(DisplayMode displayMode) {
        requireNonNull(displayMode);
        this.displayMode = displayMode;
    }

    @Override
    public DisplayMode getDisplayMode() {
        return displayMode;
    }

    private void refreshFilteredStudents() {
        if (currentCourseForDisplay.isEmpty()) {
            filteredStudents.clear();
            return;
        }

        Optional<Course> course = addressBook.getCourse(currentCourseForDisplay.get());
        if (course.isEmpty()) {
            filteredStudents.clear();
            return;
        }

        filteredStudents.setAll(new ArrayList<>(course.get().getStudents()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredAssessments.equals(otherModelManager.filteredAssessments)
                && filteredGrades.equals(otherModelManager.filteredGrades)
                && displayMode.equals(otherModelManager.displayMode)
                && currentCourseForDisplay.equals(otherModelManager.currentCourseForDisplay);
    }
}
