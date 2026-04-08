package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.address.model.assessment.Assessment;
import seedu.address.model.assessment.UniqueAssessmentList;
import seedu.address.model.course.Course;
import seedu.address.model.course.UniqueCourseList;
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
    private final UniqueCourseList courses;

    {
        persons = new UniquePersonList();
        assessments = new UniqueAssessmentList();
        grades = new UniqueGradeList();
        courses = new UniqueCourseList();
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
        bindCoursesToAssessmentList();
    }

    public void setGrades(List<Grade> grades) {
        this.grades.setGrades(grades);
    }

    public void setCourses(List<Course> courseList) {
        requireNonNull(courseList);
        this.courses.setCourses(courseList);
        bindCoursesToAssessmentList();
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

    /**
     * Returns true if an equivalent assessment exists in the address book.
     */
    public boolean hasAssessment(Assessment assessment) {
        requireNonNull(assessment);
        return assessments.contains(assessment);
    }

    /**
     * Adds an assessment to the address book.
     */
    public void addAssessment(Assessment assessment) {
        assessments.add(assessment);
    }

    /**
     * Removes an assessment from the address book and all associated grades.
     */
    public void removeAssessment(Assessment assessment) {
        requireNonNull(assessment);
        assessments.remove(assessment);
        grades.removeIf(grade -> grade.getCourseCode().equalsIgnoreCase(assessment.getCourseCode())
                && grade.getAssessmentName().equals(assessment.getAssessmentName()));
    }

    /**
     * Returns true if an equivalent grade exists in the address book.
     */
    public boolean hasGrade(Grade grade) {
        requireNonNull(grade);
        return grades.contains(grade);
    }

    public void addGrade(Grade grade) {
        grades.add(grade);
    }

    /**
     * Removes a grade from the address book.
     */
    public void removeGrade(Grade grade) {
        requireNonNull(grade);
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

    //////////////////////////////// course-level operations

    /**
     * Checks if course exists
     * @param courseCode
     * @return boolean flag
     */
    public boolean hasCourse(Course courseCode) {
        requireNonNull(courseCode);
        return courses.contains(courseCode);
    }

    /**
     * Adds a course to the address book.
     */
    public void addCourse(Course course) {
        requireNonNull(course);
        course.setAssessmentSource(assessments.asUnmodifiableObservableList());
        courses.add(course);
    }

    /**
     * Removes a course from the address book.
     * Also removes all associated assessments, grades, and unenrolls all students.
     */
    public void removeCourse(Course course) {
        requireNonNull(course);
        String courseCode = course.getCourseCode();

        // Find the course in the system
        Optional<Course> courseToRemove = getCourse(courseCode);

        if (courseToRemove.isPresent()) {
            Course foundCourse = courseToRemove.get();

            // Unenroll all students from this course (student records remain general)
            ArrayList<Student> studentsToRemove = new ArrayList<>(foundCourse.getStudents());
            for (Student student : studentsToRemove) {
                foundCourse.removeStudent(student.getStudentId());
            }

            // Remove all grades for this course
            grades.removeIf(grade -> grade.getCourseCode().equalsIgnoreCase(courseCode));

            // Remove all assessments for this course
            ArrayList<Assessment> assessmentsToRemove = new ArrayList<>(foundCourse.getAssessments());
            for (Assessment assessment : assessmentsToRemove) {
                assessments.remove(assessment);
            }
        }

        // Remove the course object from course list
        courses.remove(course);
    }

    /** Gets a list of courses with partial matches to the given course code */
    public Optional<Course> getCourse(String courseCode) {
        requireNonNull(courseCode);
        return courses.asUnmodifiableObservableList().stream()
                .filter(course -> course.getCourseCode().equalsIgnoreCase(courseCode.trim()))
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
        grades.removeIf(g -> g.getStudentId().value.equalsIgnoreCase(studentId)
                && g.getCourseCode().equalsIgnoreCase(courseCode));
    }

    @Override
    public ObservableList<Course> getCourseList() {
        return courses.asUnmodifiableObservableList();
    }

    private void bindCoursesToAssessmentList() {
        ObservableList<Assessment> globalAssessments = assessments.asUnmodifiableObservableList();
        courses.asUnmodifiableObservableList().forEach(course -> course.setAssessmentSource(globalAssessments));
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
                && grades.equals(otherAddressBook.grades)
                && courses.equals(otherAddressBook.courses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(persons, assessments, grades, courses);
    }
}
