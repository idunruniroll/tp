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
import seedu.address.model.student.Student;

/**
 * Wraps all data at the address-book level.
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueAssessmentList assessments;
    private final UniqueGradeList grades;
    private final UniqueCourseList courses;

    {
        assessments = new UniqueAssessmentList();
        grades = new UniqueGradeList();
        courses = new UniqueCourseList();
    }

    public AddressBook() {
    }

    /**
     * Creates an AddressBook using the data in the {@code toBeCopied}.
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        setAssessments(newData.getAssessmentList());
        setGrades(newData.getGradeList());
        setCourses(newData.getCourseList());
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

    /**
     * Adds a grade to the address book.
     */
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

    //// course-level operations

    /**
     * Returns true if an equivalent course exists in the address book.
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

        Optional<Course> courseToRemove = getCourse(courseCode);

        if (courseToRemove.isPresent()) {
            Course foundCourse = courseToRemove.get();

            List<Student> studentsToRemove = new java.util.ArrayList<>(foundCourse.getStudents());
            for (Student student : studentsToRemove) {
                foundCourse.removeStudent(student.getStudentId());
            }

            grades.removeIf(grade -> grade.getCourseCode().equalsIgnoreCase(courseCode));

            ArrayList<Assessment> assessmentsToRemove = new ArrayList<>(foundCourse.getAssessments());
            for (Assessment assessment : assessmentsToRemove) {
                assessments.remove(assessment);
            }
        }

        courses.remove(course);
    }

    /**
     * Gets the course with the given course code, if it exists.
     */
    public Optional<Course> getCourse(String courseCode) {
        requireNonNull(courseCode);
        return courses.asUnmodifiableObservableList().stream()
                .filter(course -> course.getCourseCode().equalsIgnoreCase(courseCode.trim()))
                .findFirst();
    }

    /**
     * Adds a student to the specified course.
     */
    public void addStudentToCourse(String courseCode, Student student) {
        requireNonNull(courseCode);
        requireNonNull(student);
        getCourse(courseCode).ifPresent(c -> c.addStudent(student));
    }

    /**
     * Removes a student from the specified course and deletes all their grade records for that course.
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
        return AddressBook.class.getCanonicalName()
                + "{assessments=" + getAssessmentList()
                + ", grades=" + getGradeList()
                + ", courses=" + getCourseList()
                + "}";
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
        return assessments.equals(otherAddressBook.assessments)
                && grades.equals(otherAddressBook.grades)
                && courses.equals(otherAddressBook.courses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assessments, grades, courses);
    }
}
