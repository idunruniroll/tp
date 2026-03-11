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

import java.util.ArrayList;
import java.util.stream.Collectors;

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
    public CourseList(ArrayList<Course> courses) {
        this.courseList = courses;
    }

    /**
     * Adds a course to the end of the course list.
     *
     * @param course course to add
     */
    public void addCourse(Course course) {
        courseList.add(course);
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
     * Case-insensitive search. Returns empty list if no matches.
     *
     * @param keyword search keyword (trimmed, case-insensitive)
     * @return ArrayList of matching courses.
     */
    public ArrayList<Course> find(String keyword) {
        return (ArrayList<Course>) courseList.stream()
            .filter(t -> t.getCourseCode().toLowerCase().contains(keyword))
            .collect(Collectors.toList());
    }
}
