package seedu.address.model;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.model.assessment.Assessment;
import seedu.address.model.course.Course;
import seedu.address.model.grade.Grade;
import seedu.address.model.student.Student;

/**
 * The API of the Model component.
 */
public interface Model {

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    boolean hasAssessment(Assessment assessment);

    void addAssessment(Assessment assessment);

    void removeAssessment(Assessment assessment);

    ObservableList<Assessment> getAssessmentList();

    /**
     * Returns the assessments for the given course in the same order as shown in
     * the assessment list UI.
     */
    default ObservableList<Assessment> getAssessmentsForCourseInDisplayOrder(String courseCode) {
        return FXCollections.observableArrayList(
                getAssessmentList().stream()
                        .filter(assessment -> assessment.getCourseCode().equalsIgnoreCase(courseCode))
                        .toList());
    }

    void updateFilteredAssessmentList(Predicate<Assessment> predicate);

    boolean hasGrade(Grade grade);

    void addGrade(Grade grade);

    void removeGrade(Grade grade);

    ObservableList<Grade> getGradeList();

    // =========== Course / Student operations ==================================================

    /** Returns an unmodifiable view of the unique Course list */
    ObservableList<Course> getCourseList();

    boolean hasCourse(String courseCode);

    Optional<Course> getCourse(String courseCode);

    void addCourse(Course course);

    void removeCourse(Course course);

    void addStudentToCourse(String courseCode, Student student);

    void removeStudentFromCourse(String courseCode, String studentId);

    ObservableList<Grade> getGradesByStudentId(String studentId);

    ObservableList<Grade> getGradesByCourse(String courseCode);

    ObservableList<Grade> getGradesByCourseAndAssessment(String courseCode, String assessmentName);

    // =========== Student GUI display state ====================================================

    /** Returns the live observable list of students for the currently displayed course. */
    ObservableList<Student> getFilteredStudentList();

    /** Returns an unmodifiable view of the course list shown in the GUI. */
    default ObservableList<Course> getFilteredCourseList() {
        return FXCollections.observableArrayList();
    }

    /** Returns the courses whose expanded details should be displayed in the GUI. */
    default ObservableList<Course> getDetailedCourseList() {
        return FXCollections.observableArrayList();
    }

    /** Sets the course details list shown in the GUI. */
    default void setDetailedCoursesForDisplay(List<Course> courses) {
    }

    /** Sets the course code whose student list should be displayed in the GUI. */
    void setCurrentCourseForDisplay(Optional<String> courseCode);

    /** Returns the course code currently being displayed. */
    Optional<String> getCurrentCourseForDisplay();

    // =========== Assessment / Grade GUI display state ========================================

    /** Returns an unmodifiable view of the filtered assessment list. */
    ObservableList<Assessment> getFilteredAssessmentList();

    /** Returns an unmodifiable view of the filtered grade list. */
    ObservableList<Grade> getFilteredGradeList();

    /**
     * Updates the filter of the grade list shown in the GUI.
     */
    void updateFilteredGradeList(Predicate<Grade> predicate);

    /** Sets which main list should be shown in the GUI. */
    void setDisplayMode(DisplayMode displayMode);

    /** Returns which main list should currently be shown in the GUI. */
    DisplayMode getDisplayMode();

    /** Returns true if the student is enrolled in the specified course. */
    boolean isStudentEnrolled(String courseCode, String studentId);

    /** Returns the assessment at the given display index for the specified course, if present. */
    Optional<Assessment> getAssessmentForCourseByIndex(String courseCode, Index assessmentIndex);
}
