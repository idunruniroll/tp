package seedu.address.model.course;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.course.exceptions.CourseNotFoundException;
import seedu.address.model.course.exceptions.DuplicateCourseException;

/**
 * A list of courses that enforces no duplicates between its elements.
 */
public class UniqueCourseList implements Iterable<Course> {

    private final ObservableList<Course> internalList = FXCollections.observableArrayList();
    private final ObservableList<Course> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    public static final String MESSAGE_NO_COURSES = "No courses added";

    /**
     * Returns true if the list contains an equivalent course.
     */
    public boolean contains(Course toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameCourse);
    }

    /**
     * Adds a course to the list.
     *
     * @throws DuplicateCourseException if the course already exists
     */
    public void add(Course toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateCourseException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the contents of this list with {@code courses}.
     */
    public void setCourses(List<Course> courses) {
        requireAllNonNull(courses);
        if (!coursesAreUnique(courses)) {
            throw new DuplicateCourseException();
        }

        internalList.setAll(courses);
    }

    /**
     * Removes the equivalent course from the list.
     *
     * @throws CourseNotFoundException if no such course could be found
     */
    public void remove(Course toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new CourseNotFoundException();
        }
    }

    public ObservableList<Course> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Course> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof UniqueCourseList
                && internalList.equals(((UniqueCourseList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    private boolean coursesAreUnique(List<Course> courses) {
        for (int i = 0; i < courses.size() - 1; i++) {
            for (int j = i + 1; j < courses.size(); j++) {
                if (courses.get(i).isSameCourse(courses.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
