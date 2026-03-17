package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.assessment.Assessment;
import seedu.address.model.course.Course;
import seedu.address.model.course.CourseList;
import seedu.address.model.grade.Grade;
import seedu.address.model.person.Person;
import seedu.address.model.student.Student;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    
    private ObservableList<Course> courses;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        this.courses = this.addressBook.getCourseList();
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    // =========== UserPrefs
    // ==================================================================================

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

    // =========== AddressBook
    // ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
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

    // =========== Course / Student operations
    // =============================================================

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
    }

    @Override
    public void removeStudentFromCourse(String courseCode, String studentId) {
        requireAllNonNull(courseCode, studentId);
        addressBook.removeStudentFromCourse(courseCode, studentId);
    }

    // =========== Filtered Person List Accessors
    // =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the
     * internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons);
    }

}
