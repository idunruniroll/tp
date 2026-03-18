/**
 * Manages a collection of Course.
 *
 * Encapsulates all course list operations: add, delete, mark/unmark, find, and list.
 * Uses 1-based indexing for user-facing operations (delete 1 = remove first task).
 *
 * @author zow1e
 * @see Course
 */

package seedu.address.model.course;

import static java.util.Objects.nonNull;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Manages a collection of Course objects.
 */
public class CourseList {

    private ArrayList<Course> courseList;

    /**
     * Creates an empty CourseList.
     *
     * Initializes with empty ArrayList for new task collections.
     */
    public CourseList() {
        courseList = new ArrayList<Course>();
    }

    /**
     * Creates a CourseList initialized with existing courses.
     *
     * Used when loading tasks from storage.
     *
     * @param courses existing ArrayList of courses to manage
     */
    public void setCourseList(List<Course> courses) {
        this.courseList = new ArrayList<>(courses);
    }

    /**
     * Adds a course to the end of the course list if it doesn't already exist.
     *
     * @param course course to add
     */
    public void addCourse(Course course) {
        if (!this.courseExists(course)) {
            courseList.add(course);
        }
    }

    /**
     * Remove a course from the course list if it exists.
     *
     * @param course course to remove
     */
    public void removeCourse(Course course) {
        if (this.courseExists(course)) {
            courseList.remove(course);
        }
    }

    /**
     * Deletes the course at the specified 1-based index.
     *
     * Index 1 = first course, index 2 = second course, etc.
     *
     * @param index 1-based index of course to delete
     * @return the deleted Course object
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public Course delete(int index) {
        return courseList.remove(index - 1);
    }

    /**
     * Returns the complete list of courses.
     *
     * @return ArrayList containing all courses.
     */
    public ArrayList<Course> getCourses() {
        return courseList;
    }

    /**
     * Returns the total number of courses in the list.
     *
     * @return number of courses (0 if empty)
     */
    public int size() {
        return courseList.size();
    }

    /**
     * Finds all courses whose description contains the given keyword.
     *
     * Case-insensitive search. Returns the matching course if found, null
     * otherwise.
     * Assumes there is at most one matching course.
     *
     * @param keyword search keyword (trimmed, case-insensitive)
     * @return the matching Course or null if not found.
     */
    public Course findCourseCode(String keyword) {
        String normalizedKeyword = keyword.trim().toUpperCase();
        return courseList.stream()
                .filter(course -> course.getCourseCode().equalsIgnoreCase(normalizedKeyword))
                .findFirst()
                .orElse(null);
    }

    /**
     * Finds if a course exists using the given keyword.
     *
     * Case-insensitive search. Returns true if found, false otherwise.
     * Assumes there is at most one matching course.
     *
     * @param keyword search keyword (trimmed, case-insensitive)
     * @return the true or null if not found.
     */
    public boolean courseExists(Course course) {
        return courseList.contains(course);
    }

    /**
     * Returns a string representation of the course list, displaying course codes
     * with 1-based indexing.
     *
     * @return formatted string of course codes
     */
    @Override
    public String toString() {
        if (courseList.isEmpty()) {
            return "No courses in the list.";
        }
        return "Course List:\n" + IntStream.range(0, courseList.size())
                .mapToObj(i -> (i + 1) + ". " + courseList.get(i).getCourseCode())
                .collect(Collectors.joining("\n"));
    }
}
