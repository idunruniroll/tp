package seedu.address.model;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.assessment.Assessment;
import seedu.address.model.course.Course;
import seedu.address.model.grade.Grade;
import seedu.address.model.person.Person;
import seedu.address.model.student.Student;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

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

    /**
     * Returns true if a person with the same identity as {@code person} exists in
     * the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another
     * existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given
     * {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    boolean hasAssessment(Assessment assessment);

    void addAssessment(Assessment assessment);

    void removeAssessment(Assessment assessment);

    ObservableList<Assessment> getAssessmentList();

    boolean hasGrade(Grade grade);

    void addGrade(Grade grade);

    void removeGrade(Grade grade);

    ObservableList<Grade> getGradeList();

    // =========== Course / Student operations
    // ==================================================

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

    /** Sets the course code whose student list should be displayed in the GUI. */
    void setCurrentCourseForDisplay(Optional<String> courseCode);

    /** Returns the course code currently being displayed, or empty if showing persons. */
    Optional<String> getCurrentCourseForDisplay();

    // =========== Assessment / Grade GUI display state
    // ========================================

    /** Returns an unmodifiable view of the filtered assessment list. */
    ObservableList<Assessment> getFilteredAssessmentList();

    /** Returns an unmodifiable view of the filtered grade list. */
    ObservableList<Grade> getFilteredGradeList();

    /**
     * Updates the filter of the grade list shown in the GUI.
     * Only grades that satisfy the given predicate will remain visible
     * in the filtered grade list.
     * @param predicate the predicate used to filter the displayed grades
     */
    void updateFilteredGradeList(Predicate<Grade> predicate);

    /** Sets which main list should be shown in the GUI. */
    void setDisplayMode(DisplayMode displayMode);

    /** Returns which main list should currently be shown in the GUI. */
    DisplayMode getDisplayMode();
}
