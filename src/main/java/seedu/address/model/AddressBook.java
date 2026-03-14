package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.assessment.Assessment;
import seedu.address.model.assessment.UniqueAssessmentList;
import seedu.address.model.course.Course;
import seedu.address.model.course.CourseList;
import seedu.address.model.grade.Grade;
import seedu.address.model.grade.UniqueGradeList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.student.Student;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueAssessmentList assessments;
    private final UniqueGradeList grades;
    private final CourseList courses;

    {
        persons = new UniquePersonList();
        assessments = new UniqueAssessmentList();
        grades = new UniqueGradeList();
        courses = new CourseList();
    }

    public AddressBook() {
    }

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    public void setAssessments(List<Assessment> assessments) {
        this.assessments.setAssessments(assessments);
    }

    public void setGrades(List<Grade> grades) {
        this.grades.setGrades(grades);
    }

    public void setCourses(List<Course> courseList) {
        requireNonNull(courseList);
        courses.getCourses().clear();
        courses.getCourses().addAll(courseList);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setAssessments(newData.getAssessmentList());
        setGrades(newData.getGradeList());
        setCourses(newData.getCourseList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in
     * the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with
     * {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another
     * existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    public boolean hasAssessment(Assessment assessment) {
        requireNonNull(assessment);
        return assessments.contains(assessment);
    }

    public void addAssessment(Assessment assessment) {
        assessments.add(assessment);
    }

    public void removeAssessment(Assessment assessment) {
        assessments.remove(assessment);
        grades.removeIf(grade -> grade.getAssessmentName().equals(assessment.getAssessmentName()));
    }

    public boolean hasGrade(Grade grade) {
        requireNonNull(grade);
        return grades.contains(grade);
    }

    public void addGrade(Grade grade) {
        grades.add(grade);
    }

    public void removeGrade(Grade grade) {
        grades.remove(grade);
    }

    @Override
    public ObservableList<Assessment> getAssessmentList() {
        return assessments.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Grade> getGradeList() {
        return grades.asUnmodifiableObservableList();
    }

    //// course-level operations

    public boolean hasCourse(String courseCode) {
        requireNonNull(courseCode);
        return courses.getCourses().stream()
                .anyMatch(c -> c.getCourseCode().equalsIgnoreCase(courseCode.trim()));
    }

    public void addCourse(Course course) {
        requireNonNull(course);
        courses.addCourse(course);
    }

    public Optional<Course> getCourse(String courseCode) {
        requireNonNull(courseCode);
        return courses.getCourses().stream()
                .filter(c -> c.getCourseCode().equalsIgnoreCase(courseCode.trim()))
                .findFirst();
    }

    /** Adds a student to the specified course. Course must exist. */
    public void addStudentToCourse(String courseCode, Student student) {
        requireNonNull(courseCode);
        requireNonNull(student);
        getCourse(courseCode).ifPresent(c -> c.addStudent(student));
    }

    /**
     * Removes a student from the specified course and deletes all their grade records.
     * Course and student must exist.
     */
    public void removeStudentFromCourse(String courseCode, String studentId) {
        requireNonNull(courseCode);
        requireNonNull(studentId);
        getCourse(courseCode).ifPresent(c -> c.removeStudent(studentId));
        grades.removeIf(g -> g.getStudentId().value.equalsIgnoreCase(studentId));
    }

    @Override
    public List<Course> getCourseList() {
        return new ArrayList<>(courses.getCourses());
    }

    //// util methods

    @Override
    public String toString() {
        return AddressBook.class.getCanonicalName() + "{persons=" + getPersonList()
                + ", assessments=" + getAssessmentList()
                + ", grades=" + getGradeList()
                + ", courses=" + getCourseList()
                + "}";
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        return persons.equals(otherAddressBook.persons)
                && assessments.equals(otherAddressBook.assessments)
                && grades.equals(otherAddressBook.grades);
    }

    @Override
    public int hashCode() {
        return Objects.hash(persons, assessments, grades);
    }
}
